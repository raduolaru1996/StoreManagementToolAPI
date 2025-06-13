package ro.raduolaru.storemanagementtool.StoreManagementTool.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Product;

@DataJpaTest
public class ProductRepositoryTests {
    
    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveProduct_savesSuccessfully(){
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(null, "TEST", "", 100.0, 100, true, now, now);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("TEST", savedProduct.getName());
    }

    @Test
    void findById_returnsProduct(){
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(null, "TEST", "", 100.0, 100, true, now, now);

        Product savedProduct = productRepository.save(product);
        Optional<Product> result = productRepository.findById(1L);
        assertNotNull(result);
        assertEquals(savedProduct.getId(), savedProduct.getId());
        assertEquals("TEST", savedProduct.getName());
    }

    @Test
    void findByNameContainingIgnoreCase_returnsMatchingProducts(){
        LocalDateTime now = LocalDateTime.now();
        productRepository.save(new Product(null, "TEST_1", "", 50.0, 100, true, now, now));
        productRepository.save(new Product(null, "TEST_2", "", 50.0, 100, true, now, now));
        productRepository.save(new Product(null, "TEST_3", "", 50.0, 100, true, now, now));
        productRepository.save(new Product(null, "NOT_THIS_ONE", "", 50.0, 100, true, now, now));

        List<Product> result = productRepository.findByNameContainingIgnoreCase("TEST");
        
        assertEquals(3, result.size());
    }

    @Test
    void updateProduct_updatesColumns_returnsUpdatedProduct(){
        LocalDateTime now = LocalDateTime.now();
        Product product = productRepository.save(new Product(null, "TEST", "", 50.0, 100, true, now, now));

        product.setPrice(1234.5);
        product.setName("CHANGED");
        product.setIsActive(false);

        Product result = productRepository.save(product);
        
        assertEquals(1234.5, result.getPrice());
        assertEquals("CHANGED", result.getName());
        assertEquals(false, result.getIsActive());
    }
}
