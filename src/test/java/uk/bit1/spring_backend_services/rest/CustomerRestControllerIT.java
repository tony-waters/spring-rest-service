package uk.bit1.spring_backend_services.rest;

//import com.fasterxml.jackson.databind.JsonNode;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.bit1.spring_backend_services.dto.CustomerDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // rollback after each test
class CustomerRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomer_then_getById_returnsSameCustomer() throws Exception {
        // POST /customers
        CustomerDto request = new CustomerDto(null, "Taylor", "Alex", List.of());

        MvcResult postResult = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.lastName").value("Taylor"))
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andReturn();

        // extract id from response JSON
        JsonNode json = objectMapper.readTree(postResult.getResponse().getContentAsString());
        long id = json.get("id").asLong();

        // GET /customers/{id}
        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value((int) id)) // jsonPath uses int for small numbers
                .andExpect(jsonPath("$.lastName").value("Taylor"))
                .andExpect(jsonPath("$.firstName").value("Alex"));
    }

    @Test
    void addOrder_then_customerAppearsInWithOrdersEndpoint() throws Exception {
        // Create customer
        CustomerDto request = new CustomerDto(null, "Doe", "Jane", List.of());

        MvcResult postResult = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn();

        long customerId = objectMapper.readTree(postResult.getResponse().getContentAsString())
                .get("id").asLong();

        // Add order
        mockMvc.perform(post("/customers/{id}/orders", customerId)
                        .param("orderDescription", "New order"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value((int) customerId));

        // GET /customers/with-orders should include the customer
        mockMvc.perform(get("/customers/with-orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value((int) customerId))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].firstName").value("Jane"));
    }

    @Test
    void getAllCustomers_returnsCreatedCustomers() throws Exception {
        // Create two customers
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDto(null, "Smith", "Jane", List.of()))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDto(null, "Jones", "Bob", List.of()))))
                .andExpect(status().isOk());

        // GET /customers
        MvcResult result = mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andReturn();

        // Optional: assert via Jackson parsing too (nice when order isn't guaranteed)
        JsonNode arr = objectMapper.readTree(result.getResponse().getContentAsString());
        assertThat(arr.isArray()).isTrue();
        assertThat(arr).hasSize(2);
    }
}
