package bookservice.bookservice.services.inventory.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

public interface InventoryServiceFeignClient {
    
    @RequestMapping(method = RequestMethod.GET, value = BookInventoryServiceRestTemplateImpl.INVENTORY_PATH)
    ResponseEntity<List<BookInventoryDto>> getOnhandInventory(@PathVariable UUID bookId);
}