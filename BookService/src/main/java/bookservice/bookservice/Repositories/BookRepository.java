package bookservice.bookservice.Repositories;

import bookservice.bookservice.domain.Book;
import bookservice.bookservice.mappers.BookGenreEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
  
    Book findByUpc(String upc);
    
    Page<Book> findAllByTitle(String title, PageRequest pageRequest);
    
    Page<Book> findAllByAuthor(String author, PageRequest pageRequest);
    
    boolean findByIsbn(String isbn);
}