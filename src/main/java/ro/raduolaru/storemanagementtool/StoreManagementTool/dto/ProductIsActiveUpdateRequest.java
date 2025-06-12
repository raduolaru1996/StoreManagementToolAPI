package ro.raduolaru.storemanagementtool.StoreManagementTool.dto;

import jakarta.validation.constraints.NotNull;

public record ProductIsActiveUpdateRequest (
    @NotNull(message = "ProductIsActiveUpdateRequest VALIDATION - IsActive field cannot be null!")
    Boolean isActive
) {}