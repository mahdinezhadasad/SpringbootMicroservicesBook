package bookservice.bookservice.services;

import bookservice.bookservice.domain.Book;
import bookservice.bookservice.domain.BookDto;
import bookservice.bookservice.mappers.BookGenreEnum;
import bookservice.bookservice.mappers.BookPagedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.UUID;

public interface BookService {
    //Page<bookservice.bookservice.domain.Book> listBooks(String title, String author, PageRequest pageRequest);
    bookservice.bookservice.domain.Book getBookById(UUID bookId);
    bookservice.bookservice.domain.Book getBookByUpc(String upc);
    Book saveNewBook(BookDto book);
    Book updateBook(UUID bookId, BookDto book);
    
    BookDto getByIsbn(String isbn);
    
    BookDto getById(UUID bookId, Boolean showInventoryOnHand);
    
    BookPagedList listBooks(String bookTitle, String author,BookGenreEnum bookGenre, PageRequest of, Boolean showInventoryOnHand);
}