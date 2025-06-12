package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Product;
import ro.raduolaru.storemanagementtool.StoreManagementTool.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductDto addProduct(ProductDto productDto){
        return toDto(productRepository.save(toEntity(productDto)));
    }

    public List<ProductDto> getProducts(){
        return productRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    private Product toEntity(ProductDto productDto){
        return Product.builder()
            .id(productDto.getId())
            .name(productDto.getName())
            .description(productDto.getDescription())
            .price(productDto.getPrice())
            .quantity(productDto.getQuantity())
            .isActive(productDto.getIsActive())
            .createdAt(productDto.getCreatedAt())
            .updatedAt(productDto.getUpdatedAt())
            .build();
    }

    private ProductDto toDto(Product product){
        return ProductDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .isActive(product.getIsActive())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .build();
    }
}
