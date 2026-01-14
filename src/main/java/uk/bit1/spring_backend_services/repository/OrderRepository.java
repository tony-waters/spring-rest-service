package uk.bit1.spring_backend_services.repository;

import org.springframework.data.repository.CrudRepository;
import uk.bit1.spring_backend_services.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

//    Order findById(long id);

}
