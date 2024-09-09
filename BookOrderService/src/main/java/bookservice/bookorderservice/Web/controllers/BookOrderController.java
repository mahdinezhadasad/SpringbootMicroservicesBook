package bookservice.bookorderservice.Web.controllers;

import java.util.UUID;

import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.Web.model.BookOrderPagedList;
import bookservice.bookorderservice.service.BookOrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/customers/{customerId}/")
@RestController
public class BookOrderController {
    
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    
    private final BookOrderService bookOrderService;
    
    public BookOrderController(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }
    
    @GetMapping("orders")
    public BookOrderPagedList listOrders(@PathVariable("customerId") UUID customerId,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        
        return bookOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
    }
    
    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    public BookOrderDto placeOrder(@PathVariable("customerId") UUID customerId, @RequestBody BookOrderDto bookOrderDto) {
        return bookOrderService.placeOrder(customerId, bookOrderDto);
    }
    
    @GetMapping("orders/{orderId}")
    public BookOrderDto getOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        return bookOrderService.getOrderById(customerId, orderId);
    }
    
    @PutMapping("/orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        bookOrderService.pickupOrder(customerId, orderId);
    }
}