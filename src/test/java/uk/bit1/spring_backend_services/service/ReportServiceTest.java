package uk.bit1.spring_backend_services.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.repository.CustomerRepository;
import uk.bit1.spring_backend_services.repository.OrderRepository;
import uk.bit1.spring_backend_services.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReportServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        reportService = new ReportService(customerRepository, orderRepository, productRepository);
    }

    @AfterEach
    public void tearDown() {
        reportService = null;
    }

    @Test
    void test_getCustomerCount() {
        assertEquals(0, reportService.getCustomerCount());

        Customer customer = new Customer("Waters", "Tony");
        customerRepository.save(customer);
        assertEquals(1, reportService.getCustomerCount());
    }

}
