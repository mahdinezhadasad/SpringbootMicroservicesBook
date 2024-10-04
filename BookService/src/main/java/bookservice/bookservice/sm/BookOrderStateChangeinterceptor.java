package bookservice.bookservice.sm;

import bookservice.bookservice.Repositories.BookRepository;
import bookservice.bookservice.Web.model.BookOrder;
import bookservice.bookservice.Web.model.BookOrderEVent;
import bookservice.bookservice.Web.model.BookOrderStatusEnum;
import bookservice.bookservice.services.order.BookOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;


@Slf4j
@Component
@RequiredArgsConstructor
public class BookOrderStateChangeinterceptor extends StateMachineInterceptorAdapter<BookOrderStatusEnum, BookOrderEVent> {
    
    private final BookRepository bookOrderRepository;

    @Override
    public void preStateChange(State<BookOrderStatusEnum, BookOrderEVent> state, Message<BookOrderEVent> message, Transition<BookOrderStatusEnum, BookOrderEVent> transition, StateMachine<BookOrderStatusEnum, BookOrderEVent> stateMachine, StateMachine<BookOrderStatusEnum, BookOrderEVent> rootStateMachine) {
        Optional.ofNullable (message)
                .flatMap (msg -> Optional.ofNullable ((String) msg.getHeaders ().getOrDefault (BookOrderManagerImpl.ORDER_HEADER_ID,'')))
                ifPresent(orderId ->{
                    
                    log.debug ("Saving state for order id: " + orderId +"Status"+ state.getId ())
                    ;
                    BookOrder bookOrder = bookOrderRepository.getReferenceById (UUID.fromString (orderId));
                    bookOrder.setOrderStatus (state.getId ());
                    bookOrderRepository.saveAndFlush (bookOrder);
                    
                })
    }
}