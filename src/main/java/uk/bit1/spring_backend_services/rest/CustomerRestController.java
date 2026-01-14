package uk.bit1.spring_backend_services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.service.CustomerService;

import java.util.List;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/addCustomer")
    public CustomerDto addCustomer(CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}


