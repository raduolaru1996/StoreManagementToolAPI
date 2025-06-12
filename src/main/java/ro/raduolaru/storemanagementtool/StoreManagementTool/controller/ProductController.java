package ro.raduolaru.storemanagementtool.StoreManagementTool.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductDto;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductIsActiveUpdateRequest;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductNameUpdateRequest;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.ProductPriceUpdateRequest;
import ro.raduolaru.storemanagementtool.StoreManagementTool.repository.ProductRepository;
import ro.raduolaru.storemanagementtool.StoreManagementTool.service.ProductService;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/name")
    public ResponseEntity<List<ProductDto>> getProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.addProduct(productDto));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductDto> patchProductPrice(@PathVariable Long id, @RequestBody ProductPriceUpdateRequest request) {
        return ResponseEntity.ok(productService.patchProductPrice(id, request.price()));
    }
    
    @PatchMapping("/{id}/name")
    public ResponseEntity<ProductDto> patchProductName(@PathVariable Long id, @RequestBody ProductNameUpdateRequest request) {
        return ResponseEntity.ok(productService.patchProductName(id, request.name()));
    }

    @PatchMapping("/{id}/isActive")
    public ResponseEntity<ProductDto> patchProductIsActive(@PathVariable Long id, @RequestBody ProductIsActiveUpdateRequest request) {
        return ResponseEntity.ok(productService.patchProductIsActive(id, request.isActive()));
    }
}
