package bookservice.bookinventoryservice.common.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BookEvent {
    public NewInventoryEvent(BookDto bookDto) {
        super (bookDto);
    }
}