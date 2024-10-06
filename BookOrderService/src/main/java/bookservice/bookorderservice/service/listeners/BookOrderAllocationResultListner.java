package bookservice.bookorderservice.service.listeners;

import bookservice.bookorderservice.config.JmsConfig;
import bookservice.bookorderservice.service.BookOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j

@RequiredArgsConstructor

@Component

public class BookOrderAllocationResultListener {

    private final BookOrderManager beerOrderManager;


    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)

    public void listen(Alloca result){
 
        if(!result.getAllocationError() && !result.getPendingInventory()){
       
                    //allocated normally
                 
                            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrderDto());
          
        } else if(!result.getAllocationError() && result.getPendingInventory()) {
         
                    //pending inventory
                
                            beerOrderManager.beerOrderAllocationPendingInventory(result.getBeerOrderDto());
          
        } else if(result.getAllocationError()){
          
                    //allocation error
                 
                            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrderDto());
           
        }
      
    }

    
}