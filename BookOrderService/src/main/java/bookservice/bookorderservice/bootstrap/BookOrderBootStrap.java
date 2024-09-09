package bookservice.bookorderservice.bootstrap;

import bookservice.bookorderservice.domain.Customer;
import bookservice.bookorderservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BookOrderBootStrap implements CommandLineRunner {
    public static final String LIBRARY_NAME = "Main Library";
    public static final String BOOK_1_ISBN = "978-3-16-148410-0";
    public static final String BOOK_2_ISBN = "978-1-4028-9462-6";
    public static final String BOOK_3_ISBN = "978-0-545-01022-1";
    
    private final CustomerRepository customerRepository;
    
    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
    }
    
    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            customerRepository.save(Customer.builder()
                    .customerName(LIBRARY_NAME)
                    .apiKey(UUID.randomUUID())
                    .build());
        }
    }
}