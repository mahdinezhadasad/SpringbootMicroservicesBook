package bookservice.bookorderservice.Web.mappers;

import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.domain.BookOrder;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BookOrderLineMapper.class})
public interface BookOrderMapper {
    
    BookOrderDto bookOrderToDto(BookOrder bookOrder);
    
    BookOrder dtoToBookOrder(BookOrderDto dto);
}