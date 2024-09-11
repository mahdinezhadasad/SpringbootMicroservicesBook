package bookservice.bookservice.Web.mappers;

import bookservice.bookservice.domain.Book;
import bookservice.bookservice.domain.BookDto;
import org.mapstruct.Mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BookMapperDecorator.class)
public interface BookMapper {
    
    BookDto bookToBookDto(Book book);
    
    BookDto bookToBookDtoWithInventory(Book beer);
    Book bookDtoToBook(BookDto dto);
}