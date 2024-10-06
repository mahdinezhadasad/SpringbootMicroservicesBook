package bookservice.bookorderservice.service.listeners;


import bookservice.bookorderservice.Web.model.events.ValidateOrderResult;
import bookservice.bookorderservice.config.JmsConfig;
import bookservice.bookorderservice.service.BookOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {
    
    private final BookOrderManager bookOrderManager;
    
    @JmsListener (destination = JmsConfig.VALIDATE_BOOK_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult result){
    
      final UUID bookOrderId = result.getOrderId ();
      
      
    log.debug ("Book order id is {}", bookOrderId);
    
    bookOrderManager.processValidateionResult(bookOrderId,result.getIsValid ());
    
    }
}