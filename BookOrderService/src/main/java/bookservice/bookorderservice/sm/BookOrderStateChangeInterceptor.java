package bookservice.bookorderservice.sm;

import bookservice.bookorderservice.domain.BookOrder;
import bookservice.bookorderservice.domain.BookOrderEventEnum;
import bookservice.bookorderservice.domain.OrderStatusEnum;
import bookservice.bookorderservice.repositories.BookOrderRepository;
import bookservice.bookorderservice.service.BookOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderStatusEnum, BookOrderEventEnum> {
    
    private final BookOrderRepository bookOrderRepository;
    
    @Transactional
    @Override
    public void preStateChange(State<OrderStatusEnum, BookOrderEventEnum> state, Message<BookOrderEventEnum> message, Transition<OrderStatusEnum, BookOrderEventEnum> transition, StateMachine<OrderStatusEnum, BookOrderEventEnum> stateMachine, StateMachine<OrderStatusEnum, BookOrderEventEnum> rootStateMachine) {
        log.debug("Pre-State Change");
        
        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(BookOrderManagerImpl.ORDER_ID_HEADER, " ")))
                .ifPresent(orderId -> {
                    log.debug("Saving state for order id: " + orderId + " Status: " + state.getId());
                    
                    BookOrder bookOrder = bookOrderRepository.getOne(UUID.fromString(orderId));
                    bookOrder.setOrderStatus(state.getId());
                    bookOrderRepository.saveAndFlush(bookOrder);
                });
    }
    


}