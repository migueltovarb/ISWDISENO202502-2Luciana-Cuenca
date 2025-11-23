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
public class OwnerDTO {

    @JsonProperty("idDueno")
    private String id;

    @JsonProperty("nombre")
    @NotBlank(message = "Nombre es requerido")
    @Size(min = 2, max = 100, message = "Nombre debe estar entre 2 y 100 caracteres")
    private String firstName;

    @JsonProperty("apellido")
    @NotBlank(message = "Apellido es requerido")
    @Size(min = 2, max = 100, message = "Apellido debe estar entre 2 y 100 caracteres")
    private String lastName;

    @JsonProperty("documento")
    @NotBlank(message = "Numero de documento es requerido")
    private String documentNumber;

    @JsonProperty("correo")
    @NotBlank(message = "Correo es requerido")
    @Email(message = "Correo debe ser valido")
    private String email;

    @JsonProperty("telefono")
    private String phone;

    @JsonProperty("direccion")
    private String address;

    @JsonProperty("ciudad")
    private String city;

    @JsonProperty("estado")
    private String state;

    @JsonProperty("codigoPostal")
    private String postalCode;

    @JsonProperty("celular")
    private String mobile;
}
