package uk.bit1.spring_backend_services.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import uk.bit1.spring_backend_services.entity.Customer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = true)
public class CustomerRepositoryCrudTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testInsert() {
        // insert using Repository
        Customer newCustomer = new Customer("Bloggs", "Jo");
        Customer insertedCustomer = customerRepository.save(newCustomer);
        // test using EntityManager
        assertEquals(newCustomer, entityManager.find(Customer.class, insertedCustomer.getId()));
    }

    @Test
    void testUpdate() {
        // insert using EntityManager
        Customer newCustomer = new Customer("Bloggs", "Jo");
        entityManager.persist(newCustomer);
        // update using Repository
        String newFirstName = "Joseph";
        newCustomer.setFirstName(newFirstName);
        customerRepository.save(newCustomer);
        // test using EntityManager
        assertEquals(newFirstName, entityManager.find(Customer.class, newCustomer.getId()).getFirstName());
    }

    @Test
    void testFindById() {
        // insert using EntityManager
        Customer newCustomer = new Customer("Bloggs", "Jo");
        entityManager.persist(newCustomer);
        // test using Repository
        Customer retrievedCustomer = customerRepository.findById(newCustomer.getId()).orElse(null);
        assertEquals(retrievedCustomer.getId(), newCustomer.getId());
        assertEquals("Bloggs", retrievedCustomer.getLastName());
        assertEquals("Jo", retrievedCustomer.getFirstName());
    }

    @Test
    void testFindByLastName() {
        // insert using EntityManager
        Customer newCustomer1 = new Customer("Bloggs", "Jo");
        Customer newCustomer2 = new Customer("Groggs", "Fred");
        entityManager.persist(newCustomer1);
        entityManager.persist(newCustomer2);
        // test using Repository
        List<Customer> customers = customerRepository.findByLastName("Bloggs");
        assertEquals(1, customers.size());
        Customer retrievedCustomer = customers.get(0);
        assertEquals(newCustomer1.getFirstName(), retrievedCustomer.getFirstName());
        assertEquals(newCustomer1.getLastName(), retrievedCustomer.getLastName());
    }

    @Test
    void testDelete() {
        // insert using EntityManager
        Customer newCustomer = new Customer("Bloggs", "Jo");
        entityManager.persist(newCustomer);
        // test using Repository
        customerRepository.delete(newCustomer);
        assertEquals(null, entityManager.find(Customer.class, newCustomer.getId()));
    }
}
