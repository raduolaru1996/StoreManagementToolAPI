package ro.raduolaru.storemanagementtool.StoreManagementTool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    public String username;
    public String password;
    public Role role;
}
