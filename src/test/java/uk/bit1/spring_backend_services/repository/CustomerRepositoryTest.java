package uk.bit1.spring_backend_services.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.entity.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    private Customer testCustomer;
    private Order testOrder1;
    private Order testOrder2;

    Long orderId1;
    Long orderId2;


    @BeforeEach
    public void setUp() {
        testCustomer = new Customer("Bloggs", "Jo");
        testOrder1 = new Order("Order 1 for testOrder");
        testOrder2 = new Order("Order 2 for testOrder");
        testCustomer.addOrder(testOrder1);
        testCustomer.addOrder(testOrder2);
        customerRepository.save(testCustomer);

        orderId1 = testOrder1.getId();
        orderId2 = testOrder2.getId();
    }

    @AfterEach
    public void tearDown() {
        customerRepository.delete(testCustomer);
    }

    @Test
    void customer_can_be_found_by_id() {
        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertNotNull(customer);
        assertEquals(testCustomer.getLastName(), customer.getLastName());
        assertEquals(testCustomer.getFirstName(), customer.getFirstName());
    }

    @Test
    void customer_has_orders() {
        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertEquals(2, customer.getOrders().size());
        assertEquals(testOrder1.getId(), customer.getOrders().get(0).getId());
        assertEquals(testOrder2.getId(), customer.getOrders().get(1).getId());
    }

    @Test
    void customer_details_can_be_updated() {
        testCustomer.setLastName("Mouse");
        testCustomer.setFirstName("Mickey");
        customerRepository.save(testCustomer);

        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertNotNull(customer);
        assertEquals("Mouse", customer.getLastName());
        assertEquals("Mickey", customer.getFirstName());
    }

//    @Test
//    void customer_can_have_zero_orders() {
//        assertDoesNotThrow(() -> testCustomer.getOrders().size());
//        assertEquals(0, testCustomer.getOrders().size());
//    }
//
//    @Test
//    void customer_can_have_orders_added() {
//        testCustomer.addOrder(new Order("Order 1 for testOrder"));
//        testCustomer.addOrder(new Order("Order 2 for testOrder"));
//        customerRepository.save(testCustomer);
//
//        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
//        List<Order> orders = customer.getOrders();
//        assertEquals(2, orders.size());
//        assertEquals("Order 1 for testOrder", orders.get(0).getDescription());
//        assertEquals("Order 2 for testOrder", orders.get(1).getDescription());
//    }
//
//    @Test
//    void customer_can_have_orders_removed() {
//        Order order1 = new Order("Order 1 for testOrder");
//        Order order2 = new Order("Order 2 for testOrder");
//        testCustomer.addOrder(order1);
//        testCustomer.addOrder(order2);
//        customerRepository.save(testCustomer);
//        testCustomer.removeOrder(testCustomer.getOrders().get(0));
//        customerRepository.save(testCustomer);
//
//        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
//        List<Order> orders = customer.getOrders();
//        assertEquals(1, orders.size());
//        assertEquals("Order 2 for testOrder", orders.get(0).getDescription());
//    }

    @Test
    void customer_orders_can_be_deletes() {
        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);

    }

    @Test
    void customer_and_associated_orders_can_be_deleted() {
        Customer customer = customerRepository.findById(testCustomer.getId()).orElse(null);
        customerRepository.delete(customer);

        assertEquals(null, customerRepository.findById(testCustomer.getId()).orElse(null));
        assertEquals(null, orderRepository.findById(orderId1).orElse(null));
        assertEquals(null, orderRepository.findById(orderId2).orElse(null));
    }

    @Test
    void reacts_ok_when_removing_non_existent_object() {
        assertThrows(NullPointerException.class, () -> testCustomer.removeOrder(null));
        assertDoesNotThrow(() -> testCustomer.removeOrder(new Order("Spurious order")));
    }

}
