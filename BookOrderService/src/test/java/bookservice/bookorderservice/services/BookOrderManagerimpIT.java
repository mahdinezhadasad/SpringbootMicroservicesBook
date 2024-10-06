package bookservice.bookorderservice.services;


import bookservice.bookorderservice.domain.Customer;
import bookservice.bookorderservice.repositories.BookOrderRepository;
import bookservice.bookorderservice.repositories.CustomerRepository;
import bookservice.bookorderservice.service.BookOrderManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class BookOrderManagerimpIT {
    
    @Autowired
    BookOrderManager bookOrderManager;
    
    @Autowired
    BookOrderRepository bookOrderRepository;
    
    @Autowired
    CustomerRepository customerRepository;
    
    
    Customer customer;
    
    
    UUID  bookId = UUID.randomUUID();
    
    
    @BeforeEach
    void setUp() {
        
        customer = customerRepository.save(Customer.builder ())
                .cutomerName("ss")
                .build();
    }
    
}