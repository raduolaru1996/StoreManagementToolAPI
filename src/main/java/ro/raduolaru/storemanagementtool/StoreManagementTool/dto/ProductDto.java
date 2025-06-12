package ro.raduolaru.storemanagementtool.StoreManagementTool.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    
    private Long id;

    @NotBlank(message = "ProductDto VALIDATION - Name cannot be NULL or blank")
    private String name;
    private String description;

    @NotNull(message = "ProductDto VALIDATION - Price cannot be NULL")
    @Positive(message = "ProductDto VALIDATION - Price must be positive")
    private Double price;

    @NotNull(message = "ProductDto VALIDATION - Quantity cannot be NULL")
    @Positive(message = "ProductDto VALIDATION - Quantity must be positive")
    private Integer quantity;

    @Builder.Default
    private Boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
