package bookservice.bookservice.event;

import bookservice.bookservice.domain.BookDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BookEvent {
    public NewInventoryEvent(BookDto bookDto) {
        super (bookDto);
    }
}