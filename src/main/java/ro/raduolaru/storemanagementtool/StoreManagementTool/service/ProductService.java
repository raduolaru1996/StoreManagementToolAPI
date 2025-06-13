package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.exception.ProductNotFoundException;
import ro.raduolaru.storemanagementtool.StoreManagementTool.mapper.ProductMapper;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Product;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount.DiscountPolicy;
import ro.raduolaru.storemanagementtool.StoreManagementTool.repository.ProductRepository;
import ro.raduolaru.storemanagementtool.StoreManagementTool.util.DiscountPolicyFactory;

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

        DiscountPolicy discount = DiscountPolicyFactory.discountForPrice(productDto.getPrice());
        Double finalPrice = discount.applyDiscount(productDto.getPrice());

        productDto.setPrice(finalPrice);

        log.info("Applied " + discount.discountInfo());

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
                                .orElseThrow(() -> new ProductNotFoundException(id));
        log.info("Serving product with ID=" + product.getId());
        return product;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByName(String name){
        List<Product> productEntities = productRepository.findByNameContainingIgnoreCase(name);
        if (productEntities.isEmpty()){
            throw new ProductNotFoundException(name);
        }

        log.info("Serving " + productEntities.size() + " products for NAME=" + name);

        return productEntities.stream().map(productMapper::toDto).toList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto patchProductPrice(Long id, Double price) {
        if (price < 0){
            throw new RuntimeException("Price must be positive");
        }

        log.info("PATCH Request for id=" + id + " with new PRICE=" + price);
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setPrice(price);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto patchProductName(Long id, String name) {
        log.info("PATCH Request for id=" + id + " with new NAME=" + name);
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(name);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto patchProductIsActive(Long id, Boolean isActive) {
        log.info("PATCH Request for id=" + id + " with new ISACTIVE=" + isActive);
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setIsActive(isActive);
        return productMapper.toDto(productRepository.save(product));
    }
}
