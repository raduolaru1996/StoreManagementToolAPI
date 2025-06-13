package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
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
    void getProducts_returnsProducts(){
        LocalDateTime now = LocalDateTime.now();
        Product product1 = new Product(1L, "TEST", "", 50.0, 100, true, now, now);
        Product product2 = new Product(2L, "TEST", "", 50.0, 100, true, now, now);
        List<Product> productList = List.of(product1, product2);
        ProductDto productDto1 = new ProductDto(1L, "TEST", "", 50.0, 100, true, now, now);
        ProductDto productDto2 = new ProductDto(2L, "TEST", "", 50.0, 100, true, now, now);
        List<ProductDto> expectedList = List.of(productDto1,productDto2);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toDto(product1)).thenReturn(productDto1);
        when(productMapper.toDto(product2)).thenReturn(productDto2);
        
        List<ProductDto> productResult = productService.getProducts();

        assertEquals(productResult, expectedList);

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(2)).toDto(any(Product.class));
    }

    @Test
    void getProductById_existingId_returnsProduct(){
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "TEST", "", 100.0, 100, true, now, now);
        ProductDto productDto = new ProductDto(1L, "TEST", "", 100.0, 100, true, now, now);
    
        when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);
        
        ProductDto productResult = productService.getProductById(1l);

        assertNotNull(productResult);
        assertEquals(productResult, productDto);

        verify(productRepository, times(1)).findById(1l);
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void getProductById_nonExistingId_throwsProductNotFoundException(){
        when(productRepository.findById(123l)).thenReturn(Optional.empty());
        
        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(123l);
        });
        
        assertEquals("Could not find product with ID=123", e.getMessage());

        verify(productRepository, times(1)).findById(123l);
    }

    @Test
    void getProductByName_existingName_returnsProducts(){
        LocalDateTime now = LocalDateTime.now();
        Product product1 = new Product(1L, "TEST", "", 50.0, 100, true, now, now);
        Product product2 = new Product(2L, "TEST", "", 50.0, 100, true, now, now);
        List<Product> productEntities = List.of(product1,product2);
        ProductDto productDto1 = new ProductDto(1L, "TEST", "", 50.0, 100, true, now, now);
        ProductDto productDto2 = new ProductDto(2L, "TEST", "", 50.0, 100, true, now, now);
        List<ProductDto> expectedResult = List.of(productDto1,productDto2);
        when(productRepository.findByNameContainingIgnoreCase("TEST")).thenReturn(productEntities);
        when(productMapper.toDto(product1)).thenReturn(productDto1);
        when(productMapper.toDto(product2)).thenReturn(productDto2);

        List<ProductDto> productResult = productService.getProductsByName("TEST");

        assertNotNull(productResult);
        assertEquals(expectedResult, productResult);

        verify(productRepository, times(1)).findByNameContainingIgnoreCase("TEST");
        verify(productMapper, times(2)).toDto(any(Product.class));
    }

    @Test
    void getProductByName_missingName_throwsProductNotFoundException(){
        when(productRepository.findByNameContainingIgnoreCase("THROW")).thenReturn(Collections.emptyList());

        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductsByName("THROW");
        }); 

        assertEquals("Could not find product with NAME=THROW", e.getMessage());

        verify(productRepository, times(1)).findByNameContainingIgnoreCase("THROW");
    }

    @Test
    void addValidProduct_savesAndReturnsProduct(){
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "TEST", "", 30.0, 100, true, now, now);
        ProductDto productDto = new ProductDto(1l, "TEST", "", 30.0, 100, true, now, now);
        ProductDto expectedDto = new ProductDto(1l, "TEST", "", 30.0, 100, true, now, now);

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
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "TEST", "", 100.0, 100, true, now, now);
        Product expectedEntity = new Product(1L, "TEST", "", 50.0, 100, true, now, now);
        ProductDto productDto = new ProductDto(1l, "TEST", "", 50.0, 100, true, now, now);

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
    void patchProductPrice_existingProductAndNegativePrice_throwsError(){

        Exception e = assertThrows(Exception.class, () -> {
            productService.patchProductPrice(1l, -50.0);
        });

        assertEquals("Price must be positive", e.getMessage());
    }

    @Test
    void patchProductName_existingProductAndCorrectName_updatesName(){
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "TEST", "", 50.0, 100, true, now, now);
        Product expectedEntity = new Product(1L, "CHANGED", "", 50.0, 100, true, now, now);
        ProductDto expectedDto = new ProductDto(1l, "CHANGED", "", 50.0, 100, true, now, now);

        when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(expectedEntity);
        when(productMapper.toDto(expectedEntity)).thenReturn(expectedDto);

        ProductDto result = productService.patchProductName(1l, "CHANGED");

        assertEquals(result.getName(), expectedDto.getName());

        verify(productRepository, times(1)).findById(1l);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    void patchProductName_missingProduct_throwsError(){

        when(productRepository.findById(123l)).thenReturn(Optional.empty());
        
        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> {
            productService.patchProductName(123l, "THROWS");
        });
        
        assertEquals("Could not find product with ID=123", e.getMessage());

        verify(productRepository, times(1)).findById(123l);
    }

    @Test
    void patchProductIsActive_existingProductAndCorrectIsActive_updatesIsActive(){
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "TEST", "", 50.0, 100, true, now, now);
        Product expectedEntity = new Product(1L, "TEST", "", 50.0, 100, true, now, now);
        ProductDto expectedDto = new ProductDto(1l, "TEST", "", 50.0, 100, false, now, now);

        when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(expectedEntity);
        when(productMapper.toDto(expectedEntity)).thenReturn(expectedDto);

        ProductDto result = productService.patchProductIsActive(1l, false);

        assertFalse(result.getIsActive());

        verify(productRepository, times(1)).findById(1l);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    void patchProductIsActive_missingProduct_throwsError(){

        when(productRepository.findById(123l)).thenReturn(Optional.empty());
        
        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> {
            productService.patchProductIsActive(123l, false);
        });
        
        assertEquals("Could not find product with ID=123", e.getMessage());

        verify(productRepository, times(1)).findById(123l);
    }
}
