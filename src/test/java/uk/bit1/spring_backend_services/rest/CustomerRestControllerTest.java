package uk.bit1.spring_backend_services.rest;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.bit1.spring_backend_services.dto.CustomerDto;
import uk.bit1.spring_backend_services.service.CustomerService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCustomer_byId_returnsCustomerDto() throws Exception {
        // arrange
        CustomerDto dto = new CustomerDto(1L, "Smith", "Jane", List.of());
        when(customerService.getCustomerById(1L)).thenReturn(dto);

        // act + assert
        mockMvc.perform(get("/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.orders").isArray());
    }

    @Test
    void addCustomer_postBody_returnsSavedCustomer() throws Exception {
        // arrange
        CustomerDto requestBody = new CustomerDto(null, "Taylor", "Alex", List.of());
        CustomerDto responseBody = new CustomerDto(99L, "Taylor", "Alex", List.of());

        when(customerService.addCustomer(any(CustomerDto.class))).thenReturn(responseBody);

        // act + assert
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.lastName").value("Taylor"))
                .andExpect(jsonPath("$.firstName").value("Alex"));
    }

    @Test
    void addOrderToCustomer_post_returnsUpdatedCustomer() throws Exception {
        // arrange
        CustomerDto responseBody = new CustomerDto(5L, "Doe", "Jane", List.of());
        when(customerService.addOrderToCustomer(5L, "New order")).thenReturn(responseBody);

        // act + assert
        mockMvc.perform(post("/customers/{id}/orders", 5L)
                        .param("orderDescription", "New order"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void addOrderToCustomer_missingOrderDescription_returns400() throws Exception {
        mockMvc.perform(post("/customers/{id}/orders", 5L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCustomers_returnsList() throws Exception {
        // arrange
        List<CustomerDto> customers = List.of(
                new CustomerDto(1L, "Smith", "Jane", List.of()),
                new CustomerDto(2L, "Jones", "Bob", List.of())
        );
        when(customerService.getAllCustomers()).thenReturn(customers);

        // act + assert
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getAllCustomersWithOrders_returnsList() throws Exception {
        // arrange
        List<CustomerDto> customers = List.of(
                new CustomerDto(1L, "Smith", "Jane", List.of())
        );
        when(customerService.getAllCustomersWithOrders()).thenReturn(customers);

        // act + assert
        mockMvc.perform(get("/customers/with-orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastName").value("Smith"));
    }
}
