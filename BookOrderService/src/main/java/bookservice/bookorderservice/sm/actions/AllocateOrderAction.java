package bookservice.bookorderservice.sm.actions;

import bookservice.bookorderservice.Web.mappers.BookOrderMapper;
import bookservice.bookorderservice.config.JmsConfig;
import bookservice.bookorderservice.domain.BookOrder;
import bookservice.bookorderservice.domain.OrderStatusEnum;
import bookservice.bookorderservice.repositories.BookOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<OrderStatusEnum, OrderStatusEnum> {
    private final BookOrderRepository bookOrderRepository;
    
    private final JmsTemplate jmsTemplate;
    
    private final BookOrderMapper bookOrderMapper;
    
    
    @Override
    public void execute(StateContext<OrderStatusEnum, OrderStatusEnum> stateContext) {
        
        String bookOrder =
                (String) stateContext.getMessage ().getHeaders ().get(BookOrderManagerImpl.header);
        
        BookOrder bookOrder1 = bookOrderRepository.findOneById (UUID.fromString(bookOrder) );
        
        jmsTemplate.convertAndSend (JmsConfig.ALLOCATE_ORDER_QUEUE,bookOrderMapper.bookOrderToDto (bookOrder1));
        
        log.debug ("Sent Allocation Request for order id " + bookOrder);
    
    }
}