package ro.raduolaru.storemanagementtool.StoreManagementTool.exception;

public class ProductNotFoundException extends RuntimeException{
    
    public ProductNotFoundException(Long id){
        super("Could not find product with ID=" + id);
    }

    public ProductNotFoundException(String name){
        super("Could not find product with NAME=" + name);
    }
}
