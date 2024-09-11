package bookservice.bookservice.services;

import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.Web.controllers.NotFoundException;
import bookservice.bookservice.Web.mappers.BookMapper;
import bookservice.bookservice.domain.Book;
import bookservice.bookservice.domain.BookDto;
import bookservice.bookservice.mappers.BookGenreEnum;
import bookservice.bookservice.mappers.BookPagedList;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    
    private BookMapper bookMapper;
    

    
    @Override
    public bookservice.bookservice.domain.Book getBookById(UUID bookId) {
        return bookRepository.findById (bookId).orElseThrow (() -> new RuntimeException ("Book Not Found"));
    }
    
    @Override
    public bookservice.bookservice.domain.Book getBookByUpc(String upc) {
        return bookRepository.findByUpc (upc);
    }
    
    @Override
    public Book saveNewBook(BookDto book) {
        return null;
    }
    
    @Override
    public Book updateBook(UUID bookId, BookDto book) {
        return null;
    }
    
    @Override
    public BookDto getByIsbn(String isbn) {
        return null;
    }
    
    @Override
    public BookDto getById(UUID bookId, Boolean showInventoryOnHand) {
        if(showInventoryOnHand) {
          return bookMapper.bookToBookDtoWithInventory (
                  bookRepository.findById (bookId).orElseThrow (NotFoundException::new)
          );
        }
        else {
            
            return bookMapper.bookToBookDto ( bookRepository.findById (bookId).orElseThrow (NotFoundException::new));
        }
    }
    
    @Override
    public BookPagedList listBooks(String bookTitle, String author, BookGenreEnum bookGenre, PageRequest of, Boolean showInventoryOnHand) {
        BookPagedList bookPagedList;
        Page<bookservice.bookservice.domain.Book> bookPage;
        if (bookTitle != null && !bookTitle.isEmpty ()) {
            bookPage = bookRepository.findAllByTitle (bookTitle, of);
        } else if (author != null && !author.isEmpty ()) {
            
            bookPage = bookRepository.findAllByAuthor (author, of);
        } else {
            
            bookPage = bookRepository.findAll (of);
        }
        
        if (showInventoryOnHand) {
            
            bookPagedList = new BookPagedList (bookPage.getContent ()
                    .stream ()
                    .map (bookMapper::bookToBookDtoWithInventory)
                    .collect (Collectors.toList ()),
                    PageRequest.of (bookPage.getPageable ().getPageNumber (),
                            bookPage.getPageable ().getPageSize ()),
                    bookPage.getTotalPages ());
        } else {
            
            bookPagedList = new BookPagedList (bookPage.getContent ().stream ()
                    .map (bookMapper::bookToBookDto)
                    .collect (Collectors.toList ()),
                    PageRequest.of (bookPage.getPageable ().getPageNumber (),
                            bookPage.getPageable ().getPageSize ()),
                    bookPage.getTotalPages ());
        }
        
        return  bookPagedList;
    }
}