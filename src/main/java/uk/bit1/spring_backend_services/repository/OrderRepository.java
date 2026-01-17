package uk.bit1.spring_backend_services.repository;

import org.springframework.data.repository.CrudRepository;
import uk.bit1.spring_backend_services.entity.CustomerOrder;

public interface OrderRepository extends CrudRepository<CustomerOrder, Long> {

//    Order findById(long id);

}
