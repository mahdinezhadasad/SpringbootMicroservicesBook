package bookservice.bookservice.Web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookOrderDto extends BaseItem {
    
    @Builder
    public BookOrderDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, UUID customerId, List<BookOrderLineDto> bookOrderLines,
                        BookOrderStatusEnum orderStatus, String orderStatusCallbackUrl, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.bookOrderLines = bookOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
        this.customerRef = customerRef;
    }
    
    private UUID customerId;
    private String customerRef;
    private List<BookOrderLineDto> bookOrderLines;
    private BookOrderStatusEnum orderStatus;
    private String orderStatusCallbackUrl;
}