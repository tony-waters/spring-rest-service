package uk.bit1.spring_backend_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJpaRelationshipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaRelationshipsApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(CustomerRepository repository, DataSourcePoolMetadataProvider dataSourcePoolMetadataProvider) {
//		return (args) -> {
//
//			// save a few Customers
//			Order order1 = new Order("This is order 1 for customer 1");
//			System.out.println("Order1:" + order1);
//			Order order2 = new Order("This is order 2 for customer 1");
//			System.out.println("Order2:" + order2);
//			Customer customer1 = new Customer("Smith", "john");
//
//			repository.save(customer1);
//			customer1.addOrder(order1);
//			repository.save(customer1);
//			customer1.addOrder(order2);
//			repository.save(customer1);
//			System.out.println("Customer1:" + customer1);
//
//			Customer customer2 = new Customer("Waters", "Tony");
//			customer2.addOrder(new Order("This is order 1 for customer 2"));
//			customer2.addOrder(new Order("This is order 2 for customer 2"));
//			customer2.addOrder(new Order("This is order 3 for customer 2"));
//			repository.save(customer2);
////			repository.save(new Customer("Waters", "Anthony"));
////			repository.save(new Customer("Waters", "John"));
////			repository.save(new Customer("Waters", "Annie"));
////			repository.save(new Customer("Waters", "Tony"));
//
//			// fetch all customers
//			System.out.println("Customers found with findAll()");
//			System.out.println("------------------------------");
//			List<Customer> customers = (List<Customer>) repository.findAll();
//			System.out.println(customers.toString());
//			System.out.println("");
//
//			// fetch customer by id
//			System.out.println("Customers found with findById(1L)");
//			System.out.println("------------------------------");
//			Customer customer = repository.findById(1L);
//			System.out.println(customer.toString());
//			System.out.println("");
//
//			// fetch customer by last name
//			System.out.println("Customers found with findByLastName(String)");
//			System.out.println("------------------------------");
//			customers = repository.findByLastName("Waters");
//			System.out.println(customers.toString());
//			System.out.println("");
//		};
//	}

}
