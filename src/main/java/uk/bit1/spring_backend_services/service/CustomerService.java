package uk.bit1.spring_backend_services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return convertToDtos(customers);
    }

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

    private List<CustomerDto> convertToDtos(List<Customer> customers) {
        List<CustomerDto> customerDtos = new ArrayList<CustomerDto>();
        for (Customer customer : customers) {
            customerDtos.add(convertToDto(customer));
        }
        return customerDtos;
    }

    private Customer convertFromDto(CustomerDto customerDto) {
        return new Customer(
                customerDto.id(),
                customerDto.lastName(),
                customerDto.firstName()
        );
    }
}
