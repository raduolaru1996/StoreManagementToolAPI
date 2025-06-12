package ro.raduolaru.storemanagementtool.StoreManagementTool.mapper;

import org.springframework.stereotype.Component;

import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Product;

@Component
public class ProductMapper {
    public Product toEntity(ProductDto productDto){
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

    public ProductDto toDto(Product product){
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
