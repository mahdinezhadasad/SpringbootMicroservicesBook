package bookservice.bookorderservice.service;

import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.domain.BookOrder;

import java.util.UUID;

public interface BookOrderManager {
    BookOrder newBookOrder(BookOrder bookOrder);
    
    void processValidationResult(UUID bookOrderId, Boolean isValid);
    
    void bookOrderAllocationPassed(BookOrderDto bookOrder);
    
    void bookOrderAllocationPendingInventory(BookOrderDto beerOrder);
    
    void bookOrderAllocationFailed(BookOrderDto beerOrder);
    
    void bookOrderPickedUp(UUID id);
    
    void cancelOrder(UUID id);
}