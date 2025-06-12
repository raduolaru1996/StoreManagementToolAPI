package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.mapper.ProductMapper;
import ro.raduolaru.storemanagementtool.StoreManagementTool.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDto addProduct(ProductDto productDto){
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    public List<ProductDto> getProducts(){
        return productRepository.findAll().stream()
            .map(productMapper::toDto)
            .toList();
    }
}
