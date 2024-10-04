package bookservice.bookservice.services.order;

import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.Web.model.BookOrder;
import bookservice.bookservice.Web.model.BookOrderEVent;
import bookservice.bookservice.Web.model.BookOrderStatusEnum;
import bookservice.bookservice.domain.Book;
import bookservice.bookservice.sm.BookOrderStateChangeinterceptor;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.message.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookOrderManagerImpl implements BookOrderManager{
    
    private final StateMachineFactory<BookOrderStatusEnum, BookOrderEVent> stateMachineFactory;
    private final BookRepository bookRepository;
    private final BookOrderStateChangeinterceptor bookOrderStateChangeinterceptor;
    
    public static final String ORDER_HEADER_ID = "order_id";
    

    @Override
    public Book newBookOrder(Book bookOrder) {
        
        bookOrder.setId (null);
        bookOrder.setOrderStatus (BookOrderStatusEnum.NEW);
        Book  saveBookOrder = bookRepository.save (bookOrder);
        sendBookOrderEvent (saveBookOrder,BookOrderEVent.VALIDATE_ORDER);
        return  saveBookOrder;
    }
    
    
    private void sendBookOrderEvent(BookOrder bookOrder,BookOrderEVent bookOrderEvent) {
      StateMachine<BookOrderStatusEnum,BookOrderEVent> stateMachine = build (bookOrder);
  
        Message mswe = (Message) MessageBuilder.withPayload (bookOrderEvent)
                        .setHeader (ORDER_HEADER_ID,bookOrder.getId ())
                                .build ();
        stateMachine.sendEvent ((org.springframework.messaging.Message<BookOrderEVent>) msg);
    
    }
    
    private StateMachine<BookOrderStatusEnum, BookOrderEVent> getStateMachine() {
    
    
    }
    
    private StateMachine<BookOrderStatusEnum,BookOrderEVent>  build(BookOrder bookOrder){
        
        StateMachine<BookOrderStatusEnum,BookOrderEVent>  sm = stateMachineFactory.getStateMachine (bookOrder.getId ());
        
        sm.stop ();
        
        sm.getStateMachineAccessor ()
                .doWithAllRegions (sma -> {
                    sma.addStateMachineInterceptor (bookOrderStateChangeinterceptor);
                    sma.resetStateMachine (new DefaultStateMachineContext<> (bookOrder.getOrderStatus (),null,null,null);
                });
        
    }
}