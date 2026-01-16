package uk.bit1.spring_backend_services.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.entity.Order;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findByLastName_returnsMatchingCustomers() {
        // arrange
        customerRepository.save(new Customer("Smith", "Jane"));
        customerRepository.save(new Customer("Smith", "John"));
        customerRepository.save(new Customer("Jones", "Bob"));

        // act
        List<Customer> smiths = customerRepository.findByLastName("Smith");

        // assert
        assertThat(smiths).hasSize(2);
        assertThat(smiths).allMatch(c -> "Smith".equals(c.getLastName()));
        assertThat(smiths).extracting(Customer::getFirstName)
                .containsExactlyInAnyOrder("Jane", "John");
    }

    @Test
    void findByOrdersNotEmpty_returnsOnlyCustomersThatHaveOrders() {
        // arrange
        Customer noOrders = new Customer("Zero", "Orders");

        Customer hasOrders = new Customer("Has", "Orders");
        hasOrders.addOrder(new Order("Order 1"));
        hasOrders.addOrder(new Order("Order 2"));

        customerRepository.save(noOrders);
        customerRepository.save(hasOrders);

        // act
        List<Customer> result = customerRepository.findByOrdersNotEmpty();

        // assert
        assertThat(result).hasSize(1);
        Customer returned = result.get(0);
        assertThat(returned.getLastName()).isEqualTo("Has");
        assertThat(returned.getOrders()).hasSize(2);
        assertThat(returned.getOrders())
                .extracting(Order::getDescription) // assumes Order has getDescription()
                .containsExactlyInAnyOrder("Order 1", "Order 2");
    }

    @Test
    void findByOrdersNotEmpty_returnsEmptyListWhenNoOneHasOrders() {
        // arrange
        customerRepository.save(new Customer("A", "Person"));
        customerRepository.save(new Customer("B", "Person"));

        // act
        List<Customer> result = customerRepository.findByOrdersNotEmpty();

        // assert
        assertThat(result).isEmpty();
    }
}
