package bookservice.bookservice.services;


import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.config.JmsConfig;
import bookservice.bookservice.domain.Book;
import bookservice.bookservice.domain.BookDto;
import bookservice.bookservice.event.BookEvent;
import bookservice.bookservice.event.NewInventoryEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestStockBookService {
    
    private final JmsTemplate jmsTemplate;
    private BookRepository bookRepository;
    
    @Transactional
    @JmsListener (
            destination = JmsConfig.BOOK_RESTOCK_REQUEST_QUEUE
    )
    public void listen(BookEvent bookEvent) {
        BookDto bookDto = bookEvent.getBookDto ();
        
        Book  book  = bookRepository.getOne (bookDto.getId ());
        
        bookDto.setStockAvailable (book.getStockAvailable ());
        
        NewInventoryEvent newInventoryEvent = new NewInventoryEvent (bookDto);
        
        jmsTemplate.convertAndSend (JmsConfig.NEW_BOOK_INVENTORY_QUEUE,newInventoryEvent);
        
    }
}