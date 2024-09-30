package bookservice.bookinventoryservice.bootsrap;

import bookservice.bookinventoryservice.domain.BookInventory;
import bookservice.bookinventoryservice.repository.BookInventoryRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class BookInventoryBootstrap implements CommandLineRunner {
    public static final String BOOK_1_ISBN = "978-3-16-148410-0";
    public static final String BOOK_2_ISBN = "978-1-4028-9462-6";
    public static final String BOOK_3_ISBN = "978-0-596-52068-7";
    public static final UUID BOOK_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BOOK_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BOOK_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");
    
    private final BookInventoryRepository bookInventoryRepository;
    
    public BookInventoryBootstrap(BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        if(bookInventoryRepository.count() == 0){
            loadInitialInventory();
        }
    }
    
    private void loadInitialInventory() {
        bookInventoryRepository.save(BookInventory
                .builder()
                .bookId(BOOK_1_UUID)
                .isbn(BOOK_1_ISBN)
                .quantityOnHand(50)
                .build());
        
        bookInventoryRepository.save(BookInventory
                .builder()
                .bookId(BOOK_2_UUID)
                .isbn(BOOK_2_ISBN)
                .quantityOnHand(50)
                .build());
        
        bookInventoryRepository.saveAndFlush(BookInventory
                .builder()
                .bookId(BOOK_3_UUID)
                .isbn(BOOK_3_ISBN)
                .quantityOnHand(50)
                .build());
        
       
    }
}