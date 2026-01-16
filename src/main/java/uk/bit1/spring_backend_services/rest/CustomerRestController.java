package uk.bit1.spring_backend_services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
    }

    @PostMapping("/{id}/orders")
    public CustomerDto addOrderToCustomer(
            @PathVariable Long id,
            @RequestParam String orderDescription) {
        return customerService.addOrderToCustomer(id, orderDescription);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/with-orders")
    public List<CustomerDto> getAllCustomersWithOrders() {
        return customerService.getAllCustomersWithOrders();
    }
}
