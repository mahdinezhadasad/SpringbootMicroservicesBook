package bookservice.bookorderservice.sm;

import bookservice.bookorderservice.domain.BookOrderEventEnum;
import bookservice.bookorderservice.domain.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@RequiredArgsConstructor
@Configuration
@EnableStateMachineFactory
public class BookOrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum,BookOrderEventEnum> {
    
    private final Action<OrderStatusEnum, BookOrderEventEnum> validateOrderAction;
    private final Action<OrderStatusEnum, BookOrderEventEnum>  allocateOrderAction;
    
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, BookOrderEventEnum> states) throws Exception {
        states.withStates ()
                .initial (OrderStatusEnum.NEW)
                .states (EnumSet.allOf (OrderStatusEnum.class))
                .end (OrderStatusEnum.PICKED_UP)
                .end (OrderStatusEnum.DELIVERED)
                .end (OrderStatusEnum.DELIVERY_EXCEPTION)
                .end (OrderStatusEnum.VALIDATION_EXCEPTION)
                .end (OrderStatusEnum.ALLOCATION_EXCEPTION);
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, BookOrderEventEnum> transitions) throws Exception {
        transitions.withExternal ()
                .source (OrderStatusEnum.NEW).target (OrderStatusEnum.NEW
                ).event (BookOrderEventEnum.VALIDATE_ORDER)
                .action (validateOrderAction)
                .and ().withExternal ()
                .source (OrderStatusEnum.NEW).target (OrderStatusEnum.VALIDATED)
                .event (BookOrderEventEnum.VALIDATION_PASSED)
                
                .and ().withExternal ()
                .source (OrderStatusEnum.NEW).target (OrderStatusEnum.VALIDATION_EXCEPTION)
                .event (BookOrderEventEnum.VALIDATION_FAILED)
                .and ().withExternal ()
                .source (OrderStatusEnum.VALIDATED).target (OrderStatusEnum.ALLOCATION_PENDING)
                .event (BookOrderEventEnum.ALLOCATED).action (allocateOrderAction)
                .and().withExternal()
                .source(OrderStatusEnum.ALLOCATION_PENDING).target(OrderStatusEnum.ALLOCATED)
                .event(BookOrderEventEnum.ALLOCATION_SUCCESS).and().withExternal()
                .source(OrderStatusEnum.ALLOCATION_PENDING).target(OrderStatusEnum.ALLOCATION_EXCEPTION)
                .event(BookOrderEventEnum.ALLOCATION_FAILED)
                .and().withExternal()
                .source(OrderStatusEnum.ALLOCATION_PENDING).target(OrderStatusEnum.PENDING_INVENTORY)
                .event(BookOrderEventEnum.ALLOCATION_NO_INVENTORY);;
                
    }
}