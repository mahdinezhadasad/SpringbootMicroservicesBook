package bookservice.bookorderservice.repositories;


import bookservice.bookorderservice.domain.BookOrder;

import bookservice.bookorderservice.domain.Customer;
import bookservice.bookorderservice.domain.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

import java.util.List;
import java.util.UUID;

public interface BookOrderRepository extends JpaRepository<BookOrder, UUID> {
    
    Page<BookOrder> findAllByCustomer(Customer customer, Pageable pageable);
    
    List<BookOrder> findAllByOrderStatus(OrderStatusEnum orderStatusEnum);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    BookOrder findOneById(UUID id);
}