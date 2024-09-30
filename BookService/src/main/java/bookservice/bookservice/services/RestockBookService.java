package bookservice.bookservice.services;


import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.Web.mappers.BookMapper;
import bookservice.bookservice.config.JmsConfig;
import bookservice.bookservice.domain.Book;
import bookservice.bookservice.event.ReadBookEvent;
import bookservice.bookservice.services.inventory.model.BookInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestockBookService {
    
    private final BookRepository bookRepository;
    private final BookInventoryService bookInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BookMapper bookMapper;
    
    
    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory(){
        
        List<Book>   books = bookRepository.findAll();
        
        books.forEach (book -> {
            
            Integer  invQOH = bookInventoryService.getOnhandInventory (book.getId ());
            log.debug ("Min Onhand is : " + book.getStockAvailable ());
            log.debug ("Inventory  is :" + invQOH);
            
            if(book.getStockAvailable () >= invQOH){
                
                jmsTemplate.convertAndSend (JmsConfig.BOOK_RESTOCK_REQUEST_QUEUE, new ReadBookEvent(bookMapper.bookToBookDto(book)));
            }
        });
    }
}