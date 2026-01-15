package uk.bit1.spring_backend_services.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.repository.CustomerRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DataJpaTest
class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

//    @Autowired
    private CustomerService customerService = new CustomerService(customerRepository, customerMapper);


//    @BeforeAll
//    static void setup() {
//        this.customerService = new CustomerService();
//    }

    @Test
    void doNothing() {

    }

    @Test
    void addCustomer_persistsAndCanBeReloaded() {
        // Act
        CustomerDto saved = customerService.addCustomer(
                new CustomerDto(null, "Smith", "Jane", List.of())
        );

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.id(), "Expected saved CustomerDto to have an id");

        Customer reloaded = customerRepository.findById(saved.id()).orElseThrow();
        assertEquals("Smith", reloaded.getLastName());
        assertEquals("Jane", reloaded.getFirstName());
    }

    @Test
    void getAllCustomers_returnsAllPersistedCustomers() {
        // Arrange (persist via repository to set up DB state)
        customerRepository.save(new Customer("Smith", "Jane"));
        customerRepository.save(new Customer("Jones", "Bob"));

        // Act
        List<CustomerDto> customers = customerService.getAllCustomers();

        // Assert
        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

    @Test
    void addOrderToCustomer_persistsOrderAndLinksToCustomer() {
        // Arrange
        Customer customer = customerRepository.save(new Customer("Smith", "Jane"));

        // Act
        customerService.addOrderToCustomer(customer.getId(), "H2 order");

        // Assert (reload from DB)
        Customer reloaded = customerRepository.findById(customer.getId()).orElseThrow();
        assertEquals(1, reloaded.getOrders().size());
        assertEquals("H2 order", reloaded.getOrders().get(0).getDescription());

        // Relationship check (ManyToOne)
        assertNotNull(reloaded.getOrders().get(0).getCustomer());
        assertEquals(reloaded.getId(), reloaded.getOrders().get(0).getCustomer().getId());
    }

    @Test
    void getAllCustomersWithOrders_returnsOnlyCustomersThatHaveOrders() {
        // Arrange
        Customer noOrders = customerRepository.save(new Customer("NoOrders", "Nina"));
        Customer hasOrders = customerRepository.save(new Customer("HasOrders", "Harry"));

        customerService.addOrderToCustomer(hasOrders.getId(), "Order 1");

        // Act
        List<CustomerDto> result = customerService.getAllCustomersWithOrders();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("HasOrders", result.get(0).lastName());
    }
}
