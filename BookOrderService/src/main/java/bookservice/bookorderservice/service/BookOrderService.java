package bookservice.bookorderservice.service;

import java.util.UUID;

import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.Web.model.BookOrderPagedList;
import org.springframework.data.domain.Pageable;

public interface BookOrderService {
    BookOrderPagedList listOrders(UUID customerId, Pageable pageable);
    
    BookOrderDto placeOrder(UUID customerId, BookOrderDto bookOrderDto);
    
    BookOrderDto getOrderById(UUID customerId, UUID orderId);
    
    void pickupOrder(UUID customerId, UUID orderId);
}