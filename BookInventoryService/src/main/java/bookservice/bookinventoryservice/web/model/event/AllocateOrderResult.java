package bookservice.bookinventoryservice.web.model.event;

import bookservice.bookinventoryservice.web.model.BookOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderResult {
    
    private BookOrderDto bookOrderDto;
    private Boolean allocationError = false;
    private Boolean pendingInventory = false;
}