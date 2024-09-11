package bookservice.bookservice.services.inventoryCheck;

import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.Web.mappers.BookMapper;
import bookservice.bookservice.domain.Book;
import bookservice.bookservice.services.inventory.model.BookInventoryService;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryCheckService {
    private final BookRepository bookRepository;
    private final BookInventoryService bookInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BookMapper bookMapper;
    
    @Scheduled(fixedRate = 5000) //every 5 seconds
    public void checkForLowInventory() {
        List<Book> books = bookRepository.findAll();
        
        books.forEach(book -> {
            Integer invQOH = bookInventoryService.getOnhandInventory(book.getId());
            log.debug("Checking Inventory for: " + book.getTitle() + " / " + book.getId());
            
            log.debug("Inventory is: " + invQOH);
            
           
        });
    }
}