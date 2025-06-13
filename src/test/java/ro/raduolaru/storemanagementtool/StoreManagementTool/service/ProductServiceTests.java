package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.exception.ProductNotFoundException;
import ro.raduolaru.storemanagementtool.StoreManagementTool.mapper.ProductMapper;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Product;
import ro.raduolaru.storemanagementtool.StoreManagementTool.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;

    @Test
    void findProductById_existingId_returnsProduct(){
        Product product = new Product(1L, "TEST", "", 100.0, 100, true, LocalDateTime.now(), LocalDateTime.now());
        ProductDto productDto = new ProductDto(1L, "TEST", "", 100.0, 100, true, LocalDateTime.now(), LocalDateTime.now());
    
        when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);
        
        ProductDto productResult = productService.getProductById(1l);

        assertNotNull(productResult);
        assertEquals(productResult, productDto);

        verify(productRepository, times(1)).findById(1l);
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void findProductById_nonExistingId_throwsProductNotFoundException(){
        when(productRepository.findById(123l)).thenReturn(Optional.empty());
        
        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(123l);
        });
        
        assertEquals("Could not find product with ID=123", e.getMessage());

        verify(productRepository, times(1)).findById(123l);
    }

    @Test
    void addValidProduct_savesAndReturnsProduct(){
        Product product = new Product(1L, "TEST", "", 30.0, 100, true, LocalDateTime.now(), LocalDateTime.now());
        ProductDto productDto = new ProductDto(1l, "TEST", "", 30.0, 100, true, LocalDateTime.now(), LocalDateTime.now());
        ProductDto expectedDto = new ProductDto(1l, "TEST", "", 30.0, 100, true, LocalDateTime.now(), LocalDateTime.now());

        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);
        when(productMapper.toEntity(productDto)).thenReturn(product);

        ProductDto result = productService.addProduct(productDto);

        assertEquals(result, expectedDto);

        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toDto(product);
        verify(productMapper, times(1)).toEntity(productDto);
    }

    @Test
    void patchProductPrice_existingProductAndPositivePrice_updatesPrice(){
        Product product = new Product(1L, "TEST", "", 100.0, 100, true, LocalDateTime.now(), LocalDateTime.now());
        Product expectedEntity = new Product(1L, "TEST", "", 50.0, 100, true, LocalDateTime.now(), LocalDateTime.now());
        ProductDto productDto = new ProductDto(1l, "TEST", "", 50.0, 100, true, LocalDateTime.now(), LocalDateTime.now());

        when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(expectedEntity);
        when(productMapper.toDto(expectedEntity)).thenReturn(productDto);

        ProductDto result = productService.patchProductPrice(1l, 50.0);

        assertEquals(result, productDto);

        verify(productRepository, times(1)).findById(1l);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    void patchProductPrice_existingProductAndNegativePrice_updatesPrice(){

        Exception e = assertThrows(Exception.class, () -> {
            productService.patchProductPrice(1l, -50.0);
        });

        assertEquals("Price must be positive", e.getMessage());
    }
}
