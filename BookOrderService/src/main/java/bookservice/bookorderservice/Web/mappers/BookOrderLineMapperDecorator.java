package bookservice.bookorderservice.Web.mappers;

import java.util.Optional;

import bookservice.bookorderservice.Web.model.BookDto;
import bookservice.bookorderservice.Web.model.BookOrderLineDto;
import bookservice.bookorderservice.domain.BookOrderLine;
import bookservice.bookorderservice.service.book.BookService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by jt on 2019-06-09.
 */

@Data
public abstract class BookOrderLineMapperDecorator implements BookOrderLineMapper {
    
    private BookService bookService;
    private BookOrderLineMapper bookOrderLineMapper;
    

    
    
    @Qualifier("delegate")
    public void setBookOrderLineMapper(BookOrderLineMapper bookOrderLineMapper) {
        this.bookOrderLineMapper = bookOrderLineMapper;
    }
    
    @Override
    public BookOrderLineDto bookOrderLineToDto(BookOrderLine line) {
        BookOrderLineDto orderLineDto = bookOrderLineMapper.bookOrderLineToDto(line);
        Optional<BookDto> bookDtoOptional = bookService.getBeerByIsbn (line.getIsbn());
        
        bookDtoOptional.ifPresent(bookDto -> {
            orderLineDto.setTitle (bookDto.getTitle());
            orderLineDto.setAuthor (bookDto.getAuthor());
            orderLineDto.setPrice(bookDto.getPrice());
        });
        
        return orderLineDto;
    }
}