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
public class OrderRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Customer testCustomer;
    private Order testOrder;

    @BeforeEach
    public void setUp() {
        testOrder = new Order("Test Order 1");
        testCustomer = new Customer("Bloggs", "Jo");
        testCustomer.addOrder(testOrder);
        customerRepository.save(testCustomer);
    }

    @AfterEach
    public void tearDown() {
        customerRepository.delete(testCustomer);
    }

    @Test
    void order_can_be_found_by_id() {
        Order order = orderRepository.findById(testOrder.getId()).orElse(null);
        assertNotNull(order);
        assertEquals(testOrder.getDescription(), order.getDescription());
    }

    @Test
    void customer_can_be_found_through_order() {
        Order order = orderRepository.findById(testOrder.getId()).orElse(null);
        assertNotNull(order);

        Customer customer = order.getCustomer();
        assertEquals(testCustomer.getId(), customer.getId());
        assertEquals(testCustomer.getLastName(), customer.getLastName());
        assertEquals(testCustomer.getFirstName(), customer.getFirstName());
    }

    @Test
    void order_details_can_be_updated() {
        String newDescription = "Updated description";
        testOrder.setDescription(newDescription);
        customerRepository.save(testCustomer);

        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertNotNull(customer);
        assertEquals(newDescription, customer.getOrders().get(0).getDescription());
    }

}
