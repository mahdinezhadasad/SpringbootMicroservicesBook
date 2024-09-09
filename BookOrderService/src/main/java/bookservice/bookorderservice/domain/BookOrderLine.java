package bookservice.bookorderservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BookOrderLine extends BaseEntity {
    
    @Builder
    public BookOrderLine(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                         BookOrder bookOrder, UUID bookId, Integer orderQuantity,
                         Integer quantityAllocated) {
        super(id, version, createdDate, lastModifiedDate);
        this.bookOrder = bookOrder;
        this.bookId = bookId;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
    }
    
    @ManyToOne
    private BookOrder bookOrder;
    
    private UUID bookId;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
}