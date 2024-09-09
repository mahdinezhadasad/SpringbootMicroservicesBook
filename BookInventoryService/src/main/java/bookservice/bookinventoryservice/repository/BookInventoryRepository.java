package bookservice.bookinventoryservice.repository;

import bookservice.bookinventoryservice.domain.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookInventoryRepository extends JpaRepository<BookInventory, UUID> {
    
    List<BookInventory> findAllByBookId(UUID bookId);
}