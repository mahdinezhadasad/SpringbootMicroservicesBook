package bookservice.bookinventoryservice.services;

import bookservice.bookinventoryservice.common.event.NewInventoryEvent;
import bookservice.bookinventoryservice.config.JmsConfig;
import bookservice.bookinventoryservice.domain.BookInventory;
import bookservice.bookinventoryservice.repository.BookInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {
    
    private final BookInventoryRepository  bookInventoryRepository;
    @JmsListener (destination = JmsConfig.NEW_BOOK_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event){
        
        log.debug ("Got  Inventory : " + event.toString ());
       
        bookInventoryRepository.save (BookInventory.builder()
                .bookId (event.getBookDto ().getId ())
                .isbn (event.getBookDto ().getIsbn ()).quantityOnHand (event.getBookDto ().getStockAvailable ()).build());
        
    }
}