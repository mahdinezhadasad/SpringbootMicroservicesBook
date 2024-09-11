package bookservice.bookservice.services.inventory.model;

import java.util.UUID;

public interface BookInventoryService {
    
    Integer getOnhandInventory(UUID bookId);
}