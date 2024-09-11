package bookservice.bookservice.services.inventory.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BookInventoryServiceFeign implements BookInventoryService {
    
    private final InventoryServiceFeignClient inventoryServiceFeignClient;
    
    @Override
    public Integer getOnhandInventory(UUID bookId) {
        log.debug("Calling Inventory Service - BookId: " + bookId);
        
        ResponseEntity<List<BookInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnhandInventory(bookId);
        
        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BookInventoryDto::getQuantityOnHand)
                .sum();
        
        log.debug("BookId: " + bookId + " On hand is: " + onHand);
        
        return onHand;
    }
    

}