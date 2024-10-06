package bookservice.bookinventoryservice.repository;


import bookservice.bookinventoryservice.web.model.BookOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements  AllocationService{
    
    private final BookInventoryRepository bookInventoryRepository;
    
    @Override
    public Boolean allocateOrder(BookOrderDto bookOrderDto) {
        log.debug("Allocating OrderId: " + bookOrderDto.getId());
        
        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger ();
        
        bookOrderDto.getBookOrderLines().forEach(bookOrderLine -> {
            if ((((bookOrderLine.getOrderQuantityAllocated () != null ? bookOrderLine.getOrderQuantityAllocated () : 0)
                    - (bookOrderLine.getOrderQuantityAllocated () != null ? bookOrderLine.getOrderQuantityAllocated () : 0)) > 0)) {
                allocateOrder (bookOrderDto);
            }
            totalOrdered.set(totalOrdered.get() + bookOrderLine.getOrderQuantityAllocated ());
            totalAllocated.set(totalAllocated.get() + (bookOrderLine.getOrderQuantityAllocated () != null ? bookOrderLine.getOrderQuantityAllocated () : 0));
        });
        
        log.debug("Total Ordered: " + totalOrdered.get() + " Total Allocated: " + totalAllocated.get());
        
        return totalOrdered.get() == totalAllocated.get();
    }
}