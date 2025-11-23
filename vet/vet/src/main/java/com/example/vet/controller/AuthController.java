package com.example.vet.controller;

import com.example.vet.dtos.LoginRequest;
import com.example.vet.dtos.LoginResponse;
import com.example.vet.dtos.UserDTO;
import com.example.vet.entities.User;
import com.example.vet.exceptions.BadRequestException;
import com.example.vet.security.JwtTokenProvider;
import com.example.vet.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Validated
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        User user = userService.getUserEntityByEmail(request.getEmail());

        if (!userService.validatePassword(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        if (!user.getActive()) {
            throw new BadRequestException("User account is inactive");
        }

        String token = jwtTokenProvider.generateToken(user.getId());

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .build();

        log.info("Login successful for user: {}", user.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO,
                                            @RequestParam @NotBlank String password) {
        log.info("Registration attempt for email: {}", userDTO.getEmail());

        UserDTO createdUser = userService.createUser(userDTO, password);
        log.info("User registered successfully: {}", userDTO.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
