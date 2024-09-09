package bookservice.bookorderservice.repositories;



import bookservice.bookorderservice.domain.BookOrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BookOrderLineRepository extends PagingAndSortingRepository<BookOrderLine, UUID> {
}