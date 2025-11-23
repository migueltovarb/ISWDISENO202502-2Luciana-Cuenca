package com.example.vet.security;

import com.example.vet.dtos.LoginRequest;
import com.example.vet.dtos.LoginResponse;
import com.example.vet.entities.User;
import com.example.vet.exceptions.BadRequestException;
import com.example.vet.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse authenticate(LoginRequest request) {
        log.info("Authenticating user: {}", request.getEmail());

        User user = userService.getUserEntityByEmail(request.getEmail());

        if (!userService.validatePassword(request.getPassword(), user.getPassword())) {
            log.warn("Invalid password for user: {}", request.getEmail());
            throw new BadRequestException("Invalid email or password");
        }

        if (!user.getActive()) {
            log.warn("User account is inactive: {}", request.getEmail());
            throw new BadRequestException("User account is inactive");
        }

        String token = jwtTokenProvider.generateToken(user.getId());

        log.info("User authenticated successfully: {}", request.getEmail());

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .build();
    }
}
