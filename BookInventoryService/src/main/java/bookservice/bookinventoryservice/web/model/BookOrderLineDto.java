package bookservice.bookinventoryservice.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookOrderLineDto extends BaseItem {
    
    @Builder
    public BookOrderLineDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                            String isbn, String bookTitle, UUID bookId, Integer orderQuantity) {
        super(id, version, createdDate, lastModifiedDate);
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.orderQuantity = orderQuantity;
    }
    
    private String isbn;
    private String bookTitle;
    private UUID bookId;
    private Integer orderQuantity = 0;
}