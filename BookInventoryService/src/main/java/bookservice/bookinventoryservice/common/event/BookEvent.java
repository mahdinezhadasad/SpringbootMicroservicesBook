package bookservice.bookinventoryservice.common.event;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class BookEvent implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -1282151781757526592L;
    
    private final BookDto bookDto;
}