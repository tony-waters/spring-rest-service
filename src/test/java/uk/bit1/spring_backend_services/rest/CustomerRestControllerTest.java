package uk.bit1.spring_backend_services.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.bit1.spring_backend_services.dto.CustomerDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerRestControllerTest {

    @Autowired
    private CustomerRestController customerRestController;

    @Test
    void getAllCustomers() {
        List<CustomerDto> customerDtos = customerRestController.getAllCustomers();
        assertEquals(0L, customerDtos.size());
    }

    @Test
    void addCustomer() {
        CustomerDto customerDto = new CustomerDto(null, "Bloggs", "Jo", null);
        assertNull(customerDto.id());
        CustomerDto returnedCustomerDto = customerRestController.addCustomer(customerDto);
        assertNotNull(returnedCustomerDto.id());
    }
}
