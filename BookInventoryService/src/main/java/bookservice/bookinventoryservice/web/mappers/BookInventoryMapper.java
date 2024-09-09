package bookservice.bookinventoryservice.web.mappers;

import bookservice.bookinventoryservice.domain.BookInventory;
import bookservice.bookinventoryservice.web.model.BookInventoryDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BookInventoryMapper {
    
    BookInventory bookInventoryDtoToBookInventory(BookInventoryDto bookInventoryDTO);
    
    BookInventoryDto bookInventoryToBookInventoryDto(BookInventory bookInventory);
}