package bookservice.bookservice.services.inventory.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
//@ConfigurationProperties(prefix = "bookstore", ignoreUnknownFields = true)
@Component
public class BookInventoryServiceRestTemplateImpl implements BookInventoryService {
    
    public static final String INVENTORY_PATH = "/api/v1/book/{bookId}/inventory";
    private final RestTemplate restTemplate;
    
    @Value("${sfg.bookstore.inventory-service-host}")
    private String bookInventoryServiceHost;
    
    public BookInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder,
                                                @Value("${sfg.bookstore.inventory-user}") String inventoryUser,
                                                @Value("${sfg.bookstore.inventory-password}") String inventoryPassword) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication(inventoryUser, inventoryPassword)
                .build();
    }
    
    @Override
    public Integer getOnhandInventory(UUID bookId) {
        
        log.debug("Calling Inventory Service");
        
        ResponseEntity<List<BookInventoryDto>> responseEntity = restTemplate
                .exchange(bookInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BookInventoryDto>> () {}, (Object) bookId);
        
        // Sum from inventory list
        
        return Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BookInventoryDto::getQuantityOnHand)
                .sum();
    }
}