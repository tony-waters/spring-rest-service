package uk.bit1.spring_backend_services.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.entity.Customer;
import uk.bit1.spring_backend_services.entity.Order;
import uk.bit1.spring_backend_services.repository.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers_mapsEntitiesToDtos() {
        // arrange
        List<Customer> customers = List.of(
                new Customer(1L, "Smith", "Jane"),
                new Customer(2L, "Jones", "Bob")
        );

        List<CustomerDto> dtos = List.of(
                new CustomerDto(1L, "Smith", "Jane", List.of()),
                new CustomerDto(2L, "Jones", "Bob", List.of())
        );

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.map(customers)).thenReturn(dtos);

        // act
        List<CustomerDto> result = customerService.getAllCustomers();

        // assert
        assertThat(result).isEqualTo(dtos);

        verify(customerRepository).findAll();
        verify(customerMapper).map(customers);
        verifyNoMoreInteractions(customerRepository, customerMapper);
    }

    @Test
    void getAllCustomersWithOrders_usesCustomRepoMethod_andMaps() {
        // arrange
        List<Customer> customersWithOrders = List.of(
//                new Customer(1L, "Smith", "Jane", List.of(new Order("Order 1")))
        );

        List<CustomerDto> dtos = List.of(
                new CustomerDto(1L, "Smith", "Jane", List.of())
        );

        when(customerRepository.findByOrdersNotEmpty()).thenReturn(customersWithOrders);
        when(customerMapper.map(customersWithOrders)).thenReturn(dtos);

        // act
        List<CustomerDto> result = customerService.getAllCustomersWithOrders();

        // assert
        assertThat(result).isEqualTo(dtos);

        verify(customerRepository).findByOrdersNotEmpty();
        verify(customerMapper).map(customersWithOrders);
        verifyNoMoreInteractions(customerRepository, customerMapper);
    }

    @Test
    void getCustomerById_findsEntity_andMapsToDto() {
        // arrange
        Customer entity = new Customer(10L, "Brown", "Charlie");
        CustomerDto dto = new CustomerDto(10L, "Brown", "Charlie", List.of());

        when(customerRepository.findById(10L)).thenReturn(Optional.of(entity));
        when(customerMapper.toDto(entity)).thenReturn(dto);

        // act
        CustomerDto result = customerService.getCustomerById(10L);

        // assert
        assertThat(result).isEqualTo(dto);

        verify(customerRepository).findById(10L);
        verify(customerMapper).toDto(entity);
        verifyNoMoreInteractions(customerRepository, customerMapper);
    }

    @Test
    void getCustomerById_whenMissing_throwsNoSuchElementException() {
        // arrange
        when(customerRepository.findById(123L)).thenReturn(Optional.empty());

        // act + assert
        assertThatThrownBy(() -> customerService.getCustomerById(123L))
                .isInstanceOf(NoSuchElementException.class);

        verify(customerRepository).findById(123L);
        verifyNoMoreInteractions(customerRepository, customerMapper);
    }

    @Test
    void addCustomer_mapsDtoToEntity_saves_andReturnsDto() {
        // arrange
        CustomerDto inputDto = new CustomerDto(null, "Taylor", "Alex", List.of());

        Customer entityToSave = new Customer(null, "Taylor", "Alex");
        Customer savedEntity = new Customer(99L, "Taylor", "Alex");

        CustomerDto outputDto = new CustomerDto(99L, "Taylor", "Alex", List.of());

        when(customerMapper.toEntity(inputDto)).thenReturn(entityToSave);
        when(customerRepository.save(entityToSave)).thenReturn(savedEntity);
        when(customerMapper.toDto(savedEntity)).thenReturn(outputDto);

        // act
        CustomerDto result = customerService.addCustomer(inputDto);

        // assert
        assertThat(result).isEqualTo(outputDto);

        verify(customerMapper).toEntity(inputDto);
        verify(customerRepository).save(entityToSave);
        verify(customerMapper).toDto(savedEntity);
        verifyNoMoreInteractions(customerRepository, customerMapper);
    }

    @Test
    void addOrderToCustomer_createsOrder_addsToCustomer_saves_andReturnsDto() {
        // arrange
        Long customerId = 5L;
        String description = "New order";

        Customer customer = new Customer(customerId, "Doe", "Jane");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        CustomerDto mappedDto = new CustomerDto(customerId, "Doe", "Jane", List.of());
        when(customerMapper.toDto(any(Customer.class))).thenReturn(mappedDto);

        // act
        CustomerDto result = customerService.addOrderToCustomer(customerId, description);

        // assert
        assertThat(result).isEqualTo(mappedDto);

        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(captor.capture());
        verify(customerMapper).toDto(any(Customer.class));
        verifyNoMoreInteractions(customerRepository, customerMapper);

        Customer savedCustomer = captor.getValue();
        assertThat(savedCustomer.getCustomerOrders()).hasSize(1);

        Order savedCustomerOrder = savedCustomer.getCustomerOrders().get(0);
        assertThat(savedCustomerOrder.getDescription()).isEqualTo(description);
        assertThat(savedCustomerOrder.getCustomer()).isSameAs(savedCustomer);
    }

    @Test
    void addOrderToCustomer_whenMissingCustomer_throwsNoSuchElementException() {
        // arrange
        when(customerRepository.findById(404L)).thenReturn(Optional.empty());

        // act + assert
        assertThatThrownBy(() -> customerService.addOrderToCustomer(404L, "X"))
                .isInstanceOf(NoSuchElementException.class);

        verify(customerRepository).findById(404L);
        verifyNoMoreInteractions(customerRepository, customerMapper);
    }
}
