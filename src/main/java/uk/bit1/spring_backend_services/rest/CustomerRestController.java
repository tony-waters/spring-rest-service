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

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers() {
        return null;
    }
}



//@RestController
//public class GreetingController {
//
//    private static final String template = "Hello %s!";
//    private final AtomicLong counter = new AtomicLong();
//
//    @GetMapping("/greeting")
//    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
//        return new Greeting(counter.incrementAndGet(), template.formatted(name));
//    }
//}
