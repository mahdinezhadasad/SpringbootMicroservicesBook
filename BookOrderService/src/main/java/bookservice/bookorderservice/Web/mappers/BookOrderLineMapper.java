package bookservice.bookorderservice.Web.mappers;

import bookservice.bookorderservice.Web.model.BookOrderLineDto;
import bookservice.bookorderservice.domain.BookOrderLine;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith (BookOrderLineMapperDecorator.class)
public interface BookOrderLineMapper {
    BookOrderLineDto bookOrderLineToDto(BookOrderLine line);
    
    BookOrderLine dtoToBookOrderLine(BookOrderLineDto dto);
}