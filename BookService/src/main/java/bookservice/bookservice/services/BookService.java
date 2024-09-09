package bookservice.bookservice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Book;
import java.util.UUID;

public interface BookService {
    Page<bookservice.bookservice.domain.Book> listBooks(String title, String author, PageRequest pageRequest);
    bookservice.bookservice.domain.Book getBookById(UUID bookId);
    bookservice.bookservice.domain.Book getBookByUpc(String upc);
    Book saveNewBook(Book book);
    Book updateBook(UUID bookId, Book book);
}