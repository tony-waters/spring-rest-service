package uk.bit1.spring_backend_services.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import uk.bit1.spring_backend_services.dto.ProductDto;
import uk.bit1.spring_backend_services.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;;

    private ProductDto testProductDto;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(productRepository);
        testProductDto = new ProductDto(null, "Product Name", "product descriptipon here");
        testProductDto = productService.addNewProduct(testProductDto);
    }

    @AfterEach
    public void tearDown() {
        productService = null;
    }

    @Test
    void test_getProductById() {
        ProductDto productDto = productService.getProductById(testProductDto.id());
        assertEquals(testProductDto.id(), productDto.id());
        assertEquals(testProductDto.name(), productDto.name());
        assertEquals(testProductDto.description(), productDto.description());
    }
}
