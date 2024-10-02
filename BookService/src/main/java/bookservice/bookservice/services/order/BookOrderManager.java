package bookservice.bookservice.services.order;

import bookservice.bookservice.domain.Book;

public interface BookOrderManager {
    
    Book newBookOrder(Book bookOrder);
}