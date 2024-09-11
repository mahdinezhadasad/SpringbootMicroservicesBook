package bookservice.bookservice.Web.mappers;

import bookservice.bookservice.domain.Book;
import bookservice.bookservice.domain.BookDto;



public abstract class BookMapperDecorator implements BookMapper {
    
   
    private BookMapper mapper;


    
    @Override
    public BookDto bookToBookDto(Book book) {
        return mapper.bookToBookDto(book);
    }

    @Override
    public Book bookDtoToBook(BookDto bookDto) {
        return mapper.bookDtoToBook(bookDto);
    }
}