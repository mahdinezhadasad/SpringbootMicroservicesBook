package bookservice.bookservice.Web.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookOrder extends BaseItem {
    
    @Builder
    public BookOrder(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, List<BookOrderLineDto> bookOrderLines,
                        BookOrderStatusEnum orderStatus, String orderStatusCallbackUrl, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
      
        this.bookOrderLines = bookOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
        this.customerRef = customerRef;
    }
    @ManyToOne
    private Customer customer;
    
    @OneToMany(mappedBy = "bookOrder", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<BookOrderLineDto> beerOrderLines;
    private String customerRef;
    private List<BookOrderLineDto> bookOrderLines;
    private BookOrderStatusEnum orderStatus;
    private String orderStatusCallbackUrl;
}