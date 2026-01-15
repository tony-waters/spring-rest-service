package uk.bit1.spring_backend_services.rest;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CustomerRestControllerTest {

    @Autowired
    private CustomerRestController customerRestController;

    @Test
    void getCustomerById() {
        CustomerDto newCustomerDto = new CustomerDto(null, "Bloggs", "Jo", null);
        CustomerDto returnedCustomerDto = customerRestController.addCustomer(newCustomerDto);

        CustomerDto customerDto = customerRestController.getCustomer(returnedCustomerDto.id());
        assertEquals("Bloggs", customerDto.lastName());
    }


    @Test
    void getAllCustomers() {
        List<CustomerDto> customerDtos = customerRestController.getAllCustomers();
        assertEquals(0L, customerDtos.size());
    }

    @Test
    void getAllCustomersWithOrders() {
        for(int i=0; i<100; i++) {
            customerRestController.addCustomer(new CustomerDto(null, "Bloggs${i}", "Jo${i}", null));
        }
        for(int i=100; i<103; i++) {
            List<OrderDto> orders = new ArrayList<>();
            orders.add(new OrderDto(null, "Test Order ${i}", Boolean.FALSE, null, null));
            customerRestController.addCustomer(new CustomerDto(null, "Bloggs${i}", "Jo${i}", orders));
        }
        List<CustomerDto> customerDtos = customerRestController.getAllCustomersWithOrders();
        assertEquals(3L, customerDtos.size());
    }

    @Test
    void addCustomer() {
        CustomerDto newCustomerDto = new CustomerDto(null, "Bloggs", "Jo", null);
        CustomerDto returnedCustomerDto = customerRestController.addCustomer(newCustomerDto);
        assertNull(newCustomerDto.id());
        assertNotNull(returnedCustomerDto.id());
    }

    @Test
    void addManyCustomers() {
        for(int i=0; i<1000; i++) {
            customerRestController.addCustomer(new CustomerDto(null, "Bloggs${i}", "Jo${i}", null));
        }
        List<CustomerDto> customerDtos = customerRestController.getAllCustomers();
        assertEquals(1000L, customerDtos.size());
    }
}
