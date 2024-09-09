package bookservice.bookservice.mappers;

import bookservice.bookservice.domain.Book;
import bookservice.bookservice.domain.BookDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-08T15:09:47+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class BookMapperImpl_ implements BookMapper {

    @Override
    public BookDto bookToBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        return bookDto;
    }

    @Override
    public BookDto bookToBookDtoWithInventory(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        return bookDto;
    }

    @Override
    public Book bookDtoToBook(BookDto dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        return book;
    }
}
