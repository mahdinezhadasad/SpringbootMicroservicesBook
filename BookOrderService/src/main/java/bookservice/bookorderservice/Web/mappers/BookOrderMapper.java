package bookservice.bookorderservice.Web.mappers;

import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.domain.BookOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BookOrderLineMapper.class})
public interface BookOrderMapper {
    @Mapping(target = "customerId", source = "customer.id")
    BookOrderDto bookOrderToDto(BookOrder bookOrder);
    
    BookOrder dtoToBookOrder(BookOrderDto dto);
}