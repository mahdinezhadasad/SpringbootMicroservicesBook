package bookservice.bookorderservice.service.book;

import bookservice.bookorderservice.Web.model.BookDto;

import java.util.Optional;
import java.util.UUID;

public interface BookService {
    
    
    Optional<BookDto> getBeerById(UUID uuid);
    
    Optional<BookDto> getBeerByIsbn(String upc);
}