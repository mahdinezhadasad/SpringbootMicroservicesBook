package bookservice.bookservice.services.order;

import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.Web.model.BookOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookOrderValidator {
    
    private final BookRepository bookRepository;
    
  
    public Boolean validateOrder(BookOrderDto bookOrder) {
        
        AtomicInteger booksNotFound = new AtomicInteger();
        
        bookOrder.getBookOrderLines().forEach(orderline -> {
            if (bookRepository.findByIsbn (orderline.getIsbn ()) !=null) {
                booksNotFound.incrementAndGet();
            }
        });
        
        return booksNotFound.get() == 0;
    }
    
}