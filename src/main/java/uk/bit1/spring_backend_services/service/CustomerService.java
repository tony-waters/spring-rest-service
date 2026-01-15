package uk.bit1.spring_backend_services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return CustomerMapper.INSTANCE.map(customers);
    }

    public List<CustomerDto> getAllCustomersWithOrders() {
        List<Customer> customers = customerRepository.findByOrdersNotEmpty();
        return CustomerMapper.INSTANCE.map(customers);
    }

    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return  CustomerMapper.INSTANCE.toDto(customer.get());
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.INSTANCE.toEntity(customerDto);
        customerRepository.save(customer);
        return CustomerMapper.INSTANCE.toDto(customer);
    }


}
