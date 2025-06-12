package ro.raduolaru.storemanagementtool.StoreManagementTool.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductNameUpdateRequest (
    @NotBlank(message = "ProductNameUpdateRequest VALIDATION - Name cannot be NULL or blank")
    String name
) {}