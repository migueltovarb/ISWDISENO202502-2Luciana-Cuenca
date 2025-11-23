package com.example.vet.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

    private String id;

    @NotBlank(message = "Nombre es requerido")
    private String name;

    @NotBlank(message = "Especie es requerida")
    private String species;

    private String breed;

    private LocalDate dateOfBirth;

    private String color;

    private String microchip;

    private Double weight;

    @NotBlank(message = "ID del propietario es requerido")
    private String ownerId;

    private String medicalHistory;
}
