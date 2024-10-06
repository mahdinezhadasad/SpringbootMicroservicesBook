package bookservice.bookorderservice.service;

import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.domain.BookOrder;
import bookservice.bookorderservice.domain.BookOrderEventEnum;
import bookservice.bookorderservice.domain.OrderStatusEnum;
import bookservice.bookorderservice.repositories.BookOrderRepository;
import bookservice.bookorderservice.sm.BookOrderStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookOrderManagerImpl implements BookOrderManager {
    
    public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";
    
    private final StateMachineFactory<OrderStatusEnum, BookOrderEventEnum> stateMachineFactory;
    private final BookOrderRepository bookOrderRepository;
    private final BookOrderStateChangeInterceptor bookOrderStateChangeInterceptor;
    
    @Transactional
    @Override
    public BookOrder newBookOrder(BookOrder bookOrder) {
        bookOrder.setId(null);
        bookOrder.setOrderStatus(OrderStatusEnum.NEW);
        
        BookOrder savedBookOrder = bookOrderRepository.saveAndFlush(bookOrder);
        sendBookOrderEvent(savedBookOrder, BookOrderEventEnum.VALIDATE_ORDER);
        return savedBookOrder;
    }
    
    @Transactional
    @Override
    public void processValidationResult(UUID bookOrderId, Boolean isValid) {
        log.debug("Process Validation Result for bookOrderId: " + bookOrderId + " Valid? " + isValid);
        
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(bookOrderId);
        
        bookOrderOptional.ifPresentOrElse(bookOrder -> {
            if (isValid) {
                sendBookOrderEvent(bookOrder, BookOrderEventEnum.VALIDATION_PASSED);
                
                // Wait for status change
                awaitForStatus(bookOrderId, OrderStatusEnum.VALIDATED);
                
                BookOrder validatedOrder = bookOrderRepository.findById(bookOrderId).get();
                
                sendBookOrderEvent(validatedOrder, BookOrderEventEnum.ALLOCATED);
                
            } else {
                sendBookOrderEvent(bookOrder, BookOrderEventEnum.VALIDATION_FAILED);
            }
        }, () -> log.error("Order Not Found. Id: " + bookOrderId));
    }
    
    @Override
    public void bookOrderAllocationPassed(BookOrderDto bookOrderDto) {
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(bookOrderDto.getId());
        
        bookOrderOptional.ifPresentOrElse(bookOrder -> {
            sendBookOrderEvent(bookOrder, BookOrderEventEnum.ALLOCATION_SUCCESS);
            awaitForStatus(bookOrder.getId(), OrderStatusEnum.ALLOCATED);
            updateAllocatedQty(bookOrderDto);
        }, () -> log.error("Order Id Not Found: " + bookOrderDto.getId()));
    }
    
    @Override
    public void bookOrderAllocationPendingInventory(BookOrderDto bookOrderDto) {
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(bookOrderDto.getId());
        
        bookOrderOptional.ifPresentOrElse(bookOrder -> {
            sendBookOrderEvent(bookOrder, BookOrderEventEnum.ALLOCATION_NO_INVENTORY);
            awaitForStatus(bookOrder.getId(), OrderStatusEnum.PENDING_INVENTORY);
            updateAllocatedQty(bookOrderDto);
        }, () -> log.error("Order Id Not Found: " + bookOrderDto.getId()));
    }
    
    private void updateAllocatedQty(BookOrderDto bookOrderDto) {
        Optional<BookOrder> allocatedOrderOptional = bookOrderRepository.findById(bookOrderDto.getId());
        
        allocatedOrderOptional.ifPresentOrElse(allocatedOrder -> {
            allocatedOrder.getBookOrderLines().forEach(bookOrderLine -> {
                bookOrderDto.getBookOrderLines().forEach(bookOrderLineDto -> {
                    if (bookOrderLine.getId().equals(bookOrderLineDto.getId())) {
                        bookOrderLine.setQuantityAllocated(bookOrderLineDto.getOrderQuantity ());
                    }
                });
            });
            
            bookOrderRepository.saveAndFlush(allocatedOrder);
        }, () -> log.error("Order Not Found. Id: " + bookOrderDto.getId()));
    }
    
    @Override
    public void bookOrderAllocationFailed(BookOrderDto bookOrderDto) {
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(bookOrderDto.getId());
        
        bookOrderOptional.ifPresentOrElse(bookOrder -> {
            sendBookOrderEvent(bookOrder, BookOrderEventEnum.ALLOCATION_FAILED);
        }, () -> log.error("Order Not Found. Id: " + bookOrderDto.getId()));
    }
    
    @Override
    public void bookOrderPickedUp(UUID id) {
        Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(id);
        
        bookOrderOptional.ifPresentOrElse(bookOrder -> {
            // Do process
            sendBookOrderEvent(bookOrder, BookOrderEventEnum.BOOKORDER_PICKED_UP);
        }, () -> log.error("Order Not Found. Id: " + id));
    }
    
    @Override
    public void cancelOrder(UUID id) {
        bookOrderRepository.findById(id).ifPresentOrElse(bookOrder -> {
            sendBookOrderEvent(bookOrder, BookOrderEventEnum.ALLOCATION_FAILED);
        }, () -> log.error("Order Not Found. Id: " + id));
    }
    
    private void sendBookOrderEvent(BookOrder bookOrder, BookOrderEventEnum eventEnum) {
        StateMachine<OrderStatusEnum, BookOrderEventEnum> sm = build(bookOrder);
        
        Message msg = MessageBuilder.withPayload(eventEnum)
                .setHeader(ORDER_ID_HEADER, bookOrder.getId().toString())
                .build();
        
        sm.sendEvent(msg);
    }
    
    private void awaitForStatus(UUID bookOrderId, OrderStatusEnum statusEnum) {
        
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger loopCount = new AtomicInteger(0);
        
        while (!found.get()) {
            if (loopCount.incrementAndGet() > 10) {
                found.set(true);
                log.debug("Loop Retries exceeded");
            }
            
            bookOrderRepository.findById(bookOrderId).ifPresentOrElse(bookOrder -> {
                if (bookOrder.getOrderStatus().equals(statusEnum)) {
                    found.set(true);
                    log.debug("Order Found");
                } else {
                    log.debug("Order Status Not Equal. Expected: " + statusEnum.name() + " Found: " + bookOrder.getOrderStatus().name());
                }
            }, () -> {
                log.debug("Order Id Not Found");
            });
            
            if (!found.get()) {
                try {
                    log.debug("Sleeping for retry");
                    Thread.sleep(100);
                } catch (Exception e) {
                    // Do nothing
                }
            }
        }
    }
    
    private StateMachine<OrderStatusEnum, BookOrderEventEnum> build(BookOrder bookOrder) {
        StateMachine<OrderStatusEnum, BookOrderEventEnum> sm = stateMachineFactory.getStateMachine(bookOrder.getId());
        
        sm.stop();
        
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(bookOrderStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(bookOrder.getOrderStatus(), null, null, null));
                });
        
        sm.start();
        
        return sm;
    }
}