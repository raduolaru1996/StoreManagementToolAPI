package ro.raduolaru.storemanagementtool.StoreManagementTool.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.AuthRequest;
import ro.raduolaru.storemanagementtool.StoreManagementTool.dto.AuthResponse;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.User;
import ro.raduolaru.storemanagementtool.StoreManagementTool.repository.UserRepository;
import ro.raduolaru.storemanagementtool.StoreManagementTool.security.JwtService;

@Slf4j
@Service
@RequiredArgsConstructor    
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(AuthRequest request){
        log.info("Register request for " + request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("USER ALREADY EXISTS: " + request.getUsername());
        }

        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

        userRepository.save(user);

        log.info(request.getUsername() + " saved.");

        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(AuthRequest request){
        log.info("Login request for " + request.getUsername());
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        return new AuthResponse(jwtService.generateToken(user));
    }
}
