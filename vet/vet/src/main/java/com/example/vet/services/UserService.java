package com.example.vet.services;

import com.example.vet.dtos.UserDTO;
import com.example.vet.entities.User;
import com.example.vet.exceptions.BadRequestException;
import com.example.vet.exceptions.ResourceNotFoundException;
import com.example.vet.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO, String rawPassword) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists: " + userDTO.getEmail());
        }

        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phone(userDTO.getPhone())
                .role(User.UserRole.valueOf(userDTO.getRole()))
                .active(true)
                .build();

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return mapToDTO(user);
    }

    public User getUserEntityById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersByRole(String role) {
        User.UserRole userRole = User.UserRole.valueOf(role.toUpperCase());
        return userRepository.findByRole(userRole).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getActiveUsers() {
        return userRepository.findByActive(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(String id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new BadRequestException("Email already exists: " + userDTO.getEmail());
            }
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getPhone() != null) {
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getRole() != null) {
            user.setRole(User.UserRole.valueOf(userDTO.getRole()));
        }
        if (userDTO.getActive() != null) {
            user.setActive(userDTO.getActive());
        }

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    public User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole().toString())
                .active(user.getActive())
                .build();
    }
}
