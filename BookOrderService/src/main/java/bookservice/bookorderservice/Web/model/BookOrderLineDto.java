package bookservice.bookorderservice.Web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookOrderLineDto extends BaseItem {
    
    @Builder
    public BookOrderLineDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                            String isbn, String title, String author, Integer orderQuantity, BigDecimal price,Integer quantityAllocated) {
        super(id, version, createdDate, lastModifiedDate);
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.orderQuantity = orderQuantity;
        this.price =  price;
        this.quantityAllocated=quantityAllocated;
    }
    
    private String isbn;
    private String title;
    private String author;
    private Integer orderQuantity = 0;
    private BigDecimal price;
    private Integer quantityAllocated;
    
}