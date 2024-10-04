package bookservice.bookservice.services.order;

import bookservice.bookservice.config.JmsConfig;
import bookservice.bookservice.event.ValidateOrderRequest;
import bookservice.bookservice.event.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookOrderValidationListener {
    
    private final BookOrderValidator bookOrderValidator;
    private final JmsTemplate jmsTemplate;
    private final JmsConfig jmsConfig;
    
    @JmsListener (destination = JmsConfig.VALIDATE_BOOK_ORDER_QUEUE)
    public void listen(ValidateOrderRequest validateOrderRequest){
        
        Boolean isValid = bookOrderValidator.validateOrder (validateOrderRequest.getBookOrderDto ());
        
        jmsTemplate.convertAndSend (jmsConfig.VALIDATE_BOOK_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder ()
                        .isValid (isValid)
                        .orderId (validateOrderRequest.getBookOrderDto ().getId ())
                        .build ());
    
    
    
    }
}