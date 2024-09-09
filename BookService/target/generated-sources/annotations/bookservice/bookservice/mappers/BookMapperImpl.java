package bookservice.bookservice.mappers;

import bookservice.bookservice.domain.Book;
import bookservice.bookservice.domain.BookDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-08T15:09:47+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
@Primary
public class BookMapperImpl extends BookMapperDecorator {

    @Autowired
    @Qualifier("delegate")
    private BookMapper delegate;

    @Override
    public BookDto bookToBookDto(Book book)  {
        return delegate.bookToBookDto( book );
    }

    @Override
    public BookDto bookToBookDtoWithInventory(Book book)  {
        return delegate.bookToBookDtoWithInventory( book );
    }

    @Override
    public Book bookDtoToBook(BookDto dto)  {
        return delegate.bookDtoToBook( dto );
    }
}
