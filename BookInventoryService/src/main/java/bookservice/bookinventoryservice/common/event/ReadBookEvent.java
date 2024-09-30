package bookservice.bookinventoryservice.common.event;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ReadBookEvent extends BookEvent{
    public ReadBookEvent(BookDto bookDto) {
        super (bookDto);
    }
}