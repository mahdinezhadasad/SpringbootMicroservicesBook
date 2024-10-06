package bookservice.bookorderservice.Web.model.events;


import bookservice.bookorderservice.Web.model.BookOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderRequest {
    
    private BookOrderDto bookOrderDto;
    private Boolean allocationError = false;
    private Boolean pendingInventory = false;
}