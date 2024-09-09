package bookservice.bookorderservice.service;

import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.Web.model.BookOrderLineDto;
import bookservice.bookorderservice.bootstrap.BookOrderBootStrap;
import bookservice.bookorderservice.domain.Customer;
import bookservice.bookorderservice.repositories.BookOrderRepository;
import bookservice.bookorderservice.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class ReadingRoomService {
    
    private final CustomerRepository customerRepository;
    private final BookOrderService bookOrderService;
 
    private final List<String> bookIsbns = new ArrayList<>(3);
    
    public ReadingRoomService(CustomerRepository customerRepository, BookOrderService bookOrderService
                              ) {
        this.customerRepository = customerRepository;
        this.bookOrderService = bookOrderService;
      
        
        bookIsbns.add(BookOrderBootStrap.BOOK_1_ISBN);
        bookIsbns.add(BookOrderBootStrap.BOOK_2_ISBN);
        bookIsbns.add(BookOrderBootStrap.BOOK_3_ISBN);
    }
    
    @Transactional
    @Scheduled(fixedRate = 2000) //run every 2 seconds
    public void placeReadingRoomOrder() {
        
        List<Customer> customerList = customerRepository.findAllByCustomerNameLike(BookOrderBootStrap.BOOK_1_ISBN);
        
        if (customerList.size() == 1) { //should be just one
            doPlaceOrder(customerList.get(0));
        } else {
            log.error("Too many or too few reading room customers found");
        }
    }
    
    private void doPlaceOrder(Customer customer) {
        String bookToOrder = getRandomBookIsbn();
        
        BookOrderLineDto bookOrderLine = BookOrderLineDto.builder()
                .isbn(bookToOrder)
                .orderQuantity(new Random().nextInt(6)) // Set a random quantity
                .build();
        
        List<BookOrderLineDto> bookOrderLineSet = new ArrayList<>();
        bookOrderLineSet.add(bookOrderLine);
        
        BookOrderDto bookOrder = BookOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .bookOrderLines(bookOrderLineSet)
                .build();
        
        BookOrderDto savedOrder = bookOrderService.placeOrder(customer.getId(), bookOrder);
    }
    
    private String getRandomBookIsbn() {
        return bookIsbns.get(new Random().nextInt(bookIsbns.size()));
    }
}