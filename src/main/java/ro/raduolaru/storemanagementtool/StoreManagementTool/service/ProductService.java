package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.mapper.ProductMapper;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Product;
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

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id){
        ProductDto product = productRepository.findById(id)
                                .map(productMapper::toDto)
                                .orElseThrow(() -> new RuntimeException("[getProductById] Could not find product."));
        log.info("Serving product with ID=" + product.getId());
        return product;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByName(String name){
        List<Product> productEntities = productRepository.findByNameContainingIgnoreCase(name);
        if (productEntities.isEmpty()){
            throw new RuntimeException("No products for NAME=" + name);
        }

        log.info("Serving " + productEntities.size() + " products for NAME=" + name);

        return productEntities.stream().map(productMapper::toDto).toList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto patchProductPrice(Long id, Double price) {
        log.info("PATCH Request for id=" + id + " with new PRICE=" + price);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("[patchProductPrice] Could not find product."));
        product.setPrice(price);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto patchProductName(Long id, String name) {
        log.info("PATCH Request for id=" + id + " with new NAME=" + name);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("[patchProductPrice] Could not find product."));
        product.setName(name);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto patchProductIsActive(Long id, Boolean isActive) {
        log.info("PATCH Request for id=" + id + " with new ISACTIVE=" + isActive);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("[patchProductPrice] Could not find product."));
        product.setIsActive(isActive);
        return productMapper.toDto(productRepository.save(product));
    }
}
