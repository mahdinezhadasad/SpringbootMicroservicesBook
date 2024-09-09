package bookservice.bookorderservice.Web.model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookOrderStatusUpdate extends BaseItem {
    
    @Builder
    public BookOrderStatusUpdate(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                                 UUID orderId, String orderStatus, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.customerRef = customerRef;
    }
    
    @JsonProperty("orderId")
    private UUID orderId;
    
    @JsonProperty("customerRef")
    private String customerRef;
    
    @JsonProperty("orderStatus")
    private String orderStatus;
}