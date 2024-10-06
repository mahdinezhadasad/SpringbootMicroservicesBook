package bookservice.bookinventoryservice.repository;

import bookservice.bookinventoryservice.web.model.BookOrderDto;

public interface AllocationService {
    
    Boolean allocateOrder(BookOrderDto bookOrderDto);
    
    Boolean allocateOrder(BookOrderDto bookOrderDto);
}