package bookservice.bookservice.services;

import bookservice.bookservice.Repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    
    @Override
    public Page<bookservice.bookservice.domain.Book> listBooks(String title, String author, PageRequest pageRequest) {
        if (title != null && !title.isEmpty()) {
            return bookRepository.findAllByTitle(title, pageRequest);
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findAllByAuthor(author, pageRequest);
        } else {
            return bookRepository.findAll(pageRequest);
        }
    }
    
    @Override
    public bookservice.bookservice.domain.Book getBookById(UUID bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Found"));
    }
    
    @Override
    public bookservice.bookservice.domain.Book getBookByUpc(String upc) {
        return bookRepository.findByUpc(upc);
    }
    
    @Override
    public Book saveNewBook(Book book) {
        return null;
    }
    
    @Override
    public Book updateBook(UUID bookId, Book book) {
        return null;
    }
    
    
}