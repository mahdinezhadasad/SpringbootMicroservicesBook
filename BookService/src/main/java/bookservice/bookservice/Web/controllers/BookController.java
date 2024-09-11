package bookservice.bookservice.Web.controllers;

import bookservice.bookservice.domain.BookDto;
import bookservice.bookservice.mappers.BookGenreEnum;
import bookservice.bookservice.mappers.BookPagedList;
import bookservice.bookservice.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BookController {
    
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    
    private final BookService bookService;
    
    @GetMapping(produces = { "application/json" }, path = "book")
    public ResponseEntity<BookPagedList> listBooks(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "bookTitle", required = false) String bookTitle,
                                                   @RequestParam(value = "author", required = false) String author,
                                                   @RequestParam(value = "bookGenre", required = false) BookGenreEnum bookGenre,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        
        BookPagedList bookList = bookService.listBooks (bookTitle,author, bookGenre, PageRequest.of(pageNumber, pageSize),showInventoryOnHand);
        
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
    
    @GetMapping("book/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("bookId") UUID bookId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        
        return new ResponseEntity<>(bookService.getById(bookId, showInventoryOnHand), HttpStatus.OK);
    }
    
    @GetMapping("bookIsbn/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(bookService.getByIsbn(isbn), HttpStatus.OK);
    }
    
    @PostMapping(path = "book")
    public ResponseEntity saveNewBook(@RequestBody @Validated BookDto bookDto) {
        return new ResponseEntity<>(bookService.saveNewBook(bookDto), HttpStatus.CREATED);
    }
    
    @PutMapping("book/{bookId}")
    public ResponseEntity updateBookById(@PathVariable("bookId") UUID bookId, @RequestBody @Validated BookDto bookDto) {
        return new ResponseEntity<>(bookService.updateBook(bookId, bookDto), HttpStatus.NO_CONTENT);
    }
}