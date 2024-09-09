package bookservice.bookorderservice.service;

import bookservice.bookorderservice.Web.mappers.BookOrderMapper;
import bookservice.bookorderservice.Web.model.BookOrderDto;
import bookservice.bookorderservice.Web.model.BookOrderPagedList;
import bookservice.bookorderservice.domain.BookOrder;
import bookservice.bookorderservice.domain.Customer;
import bookservice.bookorderservice.domain.OrderStatusEnum;
import bookservice.bookorderservice.repositories.BookOrderRepository;
import bookservice.bookorderservice.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookOrderServiceImpl implements BookOrderService {
    
    private final BookOrderRepository bookOrderRepository;
    private final CustomerRepository customerRepository;
    private final BookOrderMapper bookOrderMapper;
    private final ApplicationEventPublisher publisher;
    
    public BookOrderServiceImpl(BookOrderRepository bookOrderRepository,
                                CustomerRepository customerRepository,
                                BookOrderMapper bookOrderMapper, ApplicationEventPublisher publisher) {
        this.bookOrderRepository = bookOrderRepository;
        this.customerRepository = customerRepository;
        this.bookOrderMapper = bookOrderMapper;
        this.publisher = publisher;
    }
    
    @Override
    public BookOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        
        if (customerOptional.isPresent()) {
            Page<BookOrder> bookOrderPage =
                    bookOrderRepository.findAllByCustomer(customerOptional.get(), pageable);
            
            return new BookOrderPagedList(bookOrderPage
                    .stream()
                    .map(bookOrderMapper::bookOrderToDto)
                    .collect(Collectors.toList()), PageRequest.of(
                    bookOrderPage.getPageable().getPageNumber(),
                    bookOrderPage.getPageable().getPageSize()),
                    bookOrderPage.getTotalElements());
        } else {
            return null;
        }
    }
    
    @Transactional
    @Override
    public BookOrderDto placeOrder(UUID customerId, BookOrderDto bookOrderDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        
        if (customerOptional.isPresent()) {
            BookOrder bookOrder = bookOrderMapper.dtoToBookOrder(bookOrderDto);
            bookOrder.setId(null); //should not be set by outside client
            bookOrder.setCustomer(customerOptional.get());
            bookOrder.setOrderStatus(OrderStatusEnum.NEW);
            
            bookOrder.getBookOrderLines().forEach(line -> line.setBookOrder(bookOrder));
            
            BookOrder savedBookOrder = bookOrderRepository.saveAndFlush(bookOrder);
            
            log.debug("Saved Book Order: " + bookOrder.getId());
            
            // Implement any necessary event publishing logic here
            // publisher.publishEvent(new NewBookOrderEvent(savedBookOrder));
            
            return bookOrderMapper.bookOrderToDto(savedBookOrder);
        }
        
        throw new RuntimeException("Customer Not Found");
    }
    
    @Override
    public BookOrderDto getOrderById(UUID customerId, UUID orderId) {
        return bookOrderMapper.bookOrderToDto(getOrder(customerId, orderId));
    }
    
    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BookOrder bookOrder = getOrder(customerId, orderId);
        bookOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);
        
        bookOrderRepository.save(bookOrder);
    }
    
    private BookOrder getOrder(UUID customerId, UUID orderId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        
        if(customerOptional.isPresent()){
            Optional<BookOrder> bookOrderOptional = bookOrderRepository.findById(orderId);
            
            if(bookOrderOptional.isPresent()){
                BookOrder bookOrder = bookOrderOptional.get();
                
                // Ensure the order belongs to the customer
                if(bookOrder.getCustomer().getId().equals(customerId)){
                    return bookOrder;
                }
            }
            throw new RuntimeException("Book Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
    }
}