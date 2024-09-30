package bookservice.bookservice.event;

import bookservice.bookservice.domain.BookDto;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ReadBookEvent extends BookEvent{
    public ReadBookEvent(BookDto bookDto) {
        super (bookDto);
    }
}