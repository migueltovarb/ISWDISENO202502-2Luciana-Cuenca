package com.example.vet.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty("idUsuario")
    private String id;

    @JsonProperty("correo")
    @NotBlank(message = "Correo es requerido")
    @Email(message = "Correo debe ser valido")
    private String email;

    @JsonProperty("nombre")
    @NotBlank(message = "Nombre es requerido")
    @Size(min = 2, max = 100, message = "Nombre debe estar entre 2 y 100 caracteres")
    private String firstName;

    @JsonProperty("apellido")
    @NotBlank(message = "Apellido es requerido")
    @Size(min = 2, max = 100, message = "Apellido debe estar entre 2 y 100 caracteres")
    private String lastName;

    @JsonProperty("telefono")
    @Size(min = 10, max = 20, message = "Telefono debe estar entre 10 y 20 caracteres")
    private String phone;

    @JsonProperty("tipoUsuario")
    @NotBlank(message = "Tipo de usuario es requerido")
    private String role;

    @JsonProperty("activo")
    private Boolean active;
}
