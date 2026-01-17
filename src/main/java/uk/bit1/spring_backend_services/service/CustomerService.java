package uk.bit1.spring_backend_services.service;

import org.springframework.stereotype.Service;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.entity.Order;
import uk.bit1.spring_backend_services.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

//    public CustomerService() {
//
//    }

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.map(customers);
    }

    public List<CustomerDto> getAllCustomersWithOrders() {
        List<Customer> customers = customerRepository.findByOrdersNotEmpty();
        return customerMapper.map(customers);
    }

    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return  customerMapper.toDto(customer.get());
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toDto(saved);
    }

    public CustomerDto addOrderToCustomer(Long customerId, String orderDescription) {
        Customer customer = customerRepository.findById(customerId).get();
        Order customerOrder = new Order(orderDescription);
        customer.addOrder(customerOrder);
        return customerMapper.toDto(customerRepository.save(customer));
    }

//    public  CustomerDto updateCustomer(CustomerDto customerDto) {
//
//    }
}
