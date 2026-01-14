package uk.bit1.spring_backend_services.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRestControllerTest {

    @Autowired
    private CustomerRestController customerRestController;

    @Test
    void getAllCustomers() {
        customerRestController.getAllCustomers();
    }
}
