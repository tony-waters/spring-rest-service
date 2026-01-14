package uk.bit1.spring_backend_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.bit1.spring_backend_services.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    Customer findById(long id);
//
    List<Customer> findByLastName(String lastName);

}
