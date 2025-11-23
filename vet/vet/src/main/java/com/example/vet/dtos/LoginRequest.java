package com.example.vet.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @JsonProperty("usuario")
    @NotBlank(message = "Usuario es requerido")
    @Email(message = "Usuario debe ser un correo valido")
    private String email;

    @JsonProperty("contrasena")
    @NotBlank(message = "Contrasena es requerida")
    private String password;
}
