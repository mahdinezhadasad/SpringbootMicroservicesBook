package bookservice.bookservice.bootstrap;
import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class BookLoader implements CommandLineRunner {
    
    public static final String BOOK_1_UPC = "1234567890123";
    public static final String BOOK_2_UPC = "9876543210987";
    public static final String BOOK_3_UPC = "1111111111111";
    
    private final BookRepository bookRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            loadBookObjects();
        }
    }
    
    private void loadBookObjects() {
        Book b1 = Book.builder()
                .title("Spring Boot in Action")
                .author("Craig Walls")
                .isbn("9781617292545")
                .price(new BigDecimal("29.99"))
                .upc(BOOK_1_UPC)
                .stockAvailable(100)
                .build();
        
        Book b2 = Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("9780134685991")
                .price(new BigDecimal("45.00"))
                .upc(BOOK_2_UPC)
                .stockAvailable(50)
                .build();
        
        Book b3 = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(new BigDecimal("40.00"))
                .upc(BOOK_3_UPC)
                .stockAvailable(30)
                .build();
        
        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);
    }
}