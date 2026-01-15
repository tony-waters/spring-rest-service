package uk.bit1.spring_backend_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.bit1.spring_backend_services.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    Customer findById(long id);
//
    List<Customer> findByLastName(String lastName);

    @Query("select c from Customer c where c.orders is not empty")
    List<Customer> findByOrdersNotEmpty();

//    @Query("SELECT c FROM  Customer c")
//    List<Customer> findAllWithOrders();



}
