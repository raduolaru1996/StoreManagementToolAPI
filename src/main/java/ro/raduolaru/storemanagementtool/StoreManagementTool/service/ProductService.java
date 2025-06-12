package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.mapper.ProductMapper;
import ro.raduolaru.storemanagementtool.StoreManagementTool.repository.ProductRepository;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto addProduct(ProductDto productDto){
        log.info("Add product request -> " + productDto.getName());
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProducts(){
        List<ProductDto> products = productRepository.findAll().stream()
                                        .map(productMapper::toDto)
                                        .toList();
        log.info("Serving " + products.size() + " products");
        return products;
    }
}
