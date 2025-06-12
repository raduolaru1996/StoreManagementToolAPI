package ro.raduolaru.storemanagementtool.StoreManagementTool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByNameContainingIgnoreCase(String name);
}
