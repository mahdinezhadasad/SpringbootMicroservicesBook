package bookservice.bookorderservice.sm.actions;


import bookservice.bookorderservice.Web.mappers.BookOrderMapper;
import bookservice.bookorderservice.Web.model.events.ValidateOrderRequest;
import bookservice.bookorderservice.config.JmsConfig;
import bookservice.bookorderservice.domain.BookOrder;
import bookservice.bookorderservice.domain.BookOrderEventEnum;
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
public class ValidateOrderAction implements Action<OrderStatusEnum, BookOrderEventEnum> {
    private final BookOrderRepository bookOrderRepository;
    
    
    private final BookOrderMapper orderMapper;
    

    private final JmsTemplate jmsTemplate;
    
    @Override
    public void execute(StateContext<OrderStatusEnum, BookOrderEventEnum> stateContext)
    {
        
        BookOrder bookOrder = bookOrderRepository.findOneById ((UUID) stateContext.getMessage ().getHeaders ().get (BookOrderManagerImpl.header));
        
        jmsTemplate.convertAndSend (JmsConfig.VALIDATE_BOOK_ORDER_QUEUE, ValidateOrderRequest.builder ().bookOrderDto (orderMapper.bookOrderToDto (bookOrder)).build ());
        
        log.debug ("Sent Validation request to queue");
    }
}