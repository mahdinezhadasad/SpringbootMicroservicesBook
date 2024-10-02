package bookservice.bookservice.services.order;

import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.Web.model.BookOrder;
import bookservice.bookservice.Web.model.BookOrderEVent;
import bookservice.bookservice.Web.model.BookOrderStatusEnum;
import bookservice.bookservice.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookOrderManagerImpl implements BookOrderManager{
    
    private final StateMachineFactory<BookOrderStatusEnum, BookOrderEVent> stateMachineFactory;
    private final BookRepository bookRepository;
    

    @Override
    public Book newBookOrder(Book bookOrder) {
        
        bookOrder.setId (null);
        bookOrder.setOrderStatus (BookOrderStatusEnum.NEW);
        Book  saveBookOrder = bookRepository.save (bookOrder);
        return  saveBookOrder;
    }
    
    
    private void sendBookOrderEvent(BookOrder bookOrder,BookOrderEVent bookOrderEvent) {
    
    
    }
    
    private StateMachine<BookOrderStatusEnum, BookOrderEVent> getStateMachine() {
    
    
    }
}