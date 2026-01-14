package uk.bit1.spring_backend_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.bit1.spring_backend_services.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
