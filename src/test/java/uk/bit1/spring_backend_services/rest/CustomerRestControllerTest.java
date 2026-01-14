package uk.bit1.spring_backend_services.rest;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.bit1.spring_backend_services.dto.CustomerDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
        CustomerDto newCustomerDto = new CustomerDto(null, "Bloggs", "Jo", null);
        CustomerDto returnedCustomerDto = customerRestController.addCustomer(newCustomerDto);
        assertNull(newCustomerDto.id());
        assertNotNull(returnedCustomerDto.id());
    }
}
