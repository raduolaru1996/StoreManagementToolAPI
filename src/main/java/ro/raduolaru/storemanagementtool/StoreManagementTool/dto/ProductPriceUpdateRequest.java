package ro.raduolaru.storemanagementtool.StoreManagementTool.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPriceUpdateRequest (
    @NotNull(message = "ProductPriceUpdateRequest VALIDATION - Price cannot be NULL")
    @Positive(message = "ProductPriceUpdateRequest VALIDATION - Price must be positive")
    Double price
) {}