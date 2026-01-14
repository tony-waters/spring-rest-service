package uk.bit1.spring_backend_services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.repository.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return  convertToDto(customer.get());
    }

    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getLastName(),
                customer.getFirstName(),
                null
        );
    }

    private Customer convertFromDto(CustomerDto customerDto) {
        return new Customer(
                customerDto.id(),
                customerDto.lastName(),
                customerDto.firstName()
        );
    }
}
