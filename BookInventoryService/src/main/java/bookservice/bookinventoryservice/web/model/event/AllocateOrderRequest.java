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
public class AllocateOrderRequest {
    
    private BookOrderDto bookOrderDto;
}