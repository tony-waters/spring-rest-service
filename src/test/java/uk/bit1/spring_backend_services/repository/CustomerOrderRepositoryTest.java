package uk.bit1.spring_backend_services.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.entity.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CustomerOrderRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Customer testCustomer;
    private Order testCustomerOrder;

    @BeforeEach
    public void setUp() {
        testCustomerOrder = new Order("Test Order 1");
        testCustomer = new Customer("Bloggs", "Jo");
        testCustomer.addOrder(testCustomerOrder);
        customerRepository.save(testCustomer);
    }

    @AfterEach
    public void tearDown() {
        customerRepository.delete(testCustomer);
    }

    @Test
    void order_can_be_found_by_id() {
        Order customerOrder = orderRepository.findById(testCustomerOrder.getId()).orElse(null);
        assertNotNull(customerOrder);
        assertEquals(testCustomerOrder.getDescription(), customerOrder.getDescription());
    }

    @Test
    void customer_can_be_found_through_order() {
        Order customerOrder = orderRepository.findById(testCustomerOrder.getId()).orElse(null);
        assertNotNull(customerOrder);

        Customer customer = customerOrder.getCustomer();
        assertEquals(testCustomer.getId(), customer.getId());
        assertEquals(testCustomer.getLastName(), customer.getLastName());
        assertEquals(testCustomer.getFirstName(), customer.getFirstName());
    }

    @Test
    void order_details_can_be_updated() {
        String newDescription = "Updated description";
        testCustomerOrder.setDescription(newDescription);
        customerRepository.save(testCustomer);

        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertNotNull(customer);
        assertEquals(newDescription, customer.getCustomerOrders().get(0).getDescription());
    }

}
