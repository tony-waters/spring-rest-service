package uk.bit1.spring_backend_services.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.entity.Order;
import uk.bit1.spring_backend_services.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void getAllCustomers_returnsMappedDtos() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(List.of(
                new Customer(1L, "Smith", "Jane"),
                new Customer(2L, "Jones", "Bob")
        ));

        // Act
        List<CustomerDto> result = customerService.getAllCustomers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Smith", result.get(0).lastName());
        assertEquals("Jane", result.get(0).firstName());

        verify(customerRepository).findAll();
    }

    @Test
    void getCustomerById_found_returnsDto() {
        // Arrange
        long id = 10L;
        Customer customer = new Customer(id, "Smith", "Jane");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // Act
        CustomerDto dto = customerService.getCustomerById(id);

        // Assert
        assertNotNull(dto);
        assertEquals(10L, dto.id());
        assertEquals("Smith", dto.lastName());
        assertEquals("Jane", dto.firstName());

        verify(customerRepository).findById(id);
    }

    @Test
    void addCustomer_savesEntity_andReturnsMappedDto() {
        // Arrange
        CustomerDto input = new CustomerDto(null, "Smith", "Jane", List.of());

        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        CustomerDto result = customerService.addCustomer(input);

        // Assert
        assertEquals("Smith", result.lastName());
        assertEquals("Jane", result.firstName());

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void addOrderToCustomer_addsOrder_andReturnsMappedDto() {
        // Arrange
        long customerId = 1L;
        Customer customer = new Customer(customerId, "Smith", "Jane");

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        CustomerDto result =
                customerService.addOrderToCustomer(customerId, "New order");

        // Assert
        assertNotNull(result);
        assertEquals(1, customer.getOrders().size());

        Order added = customer.getOrders().get(0);
        assertEquals("New order", added.getDescription());
        assertSame(customer, added.getCustomer());

        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(customer);
    }

    @Test
    void addOrderToCustomer_missingCustomer_throwsException() {
        // Arrange
        when(customerRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> customerService.addOrderToCustomer(999L, "desc"));
    }
}
