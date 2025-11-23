package com.example.vet.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDTO {

    @JsonProperty("idConsulta")
    private String id;

    @JsonProperty("idMascota")
    @NotBlank(message = "ID de la mascota es requerido")
    private String petId;

    @JsonProperty("idVeterinario")
    @NotBlank(message = "ID del veterinario es requerido")
    private String veterinarianId;

    @JsonProperty("fecha")
    @NotNull(message = "Fecha de la consulta es requerida")
    private LocalDateTime consultationDate;

    @JsonProperty("diagnostico")
    @NotBlank(message = "Diagnostico es requerido")
    private String diagnosis;

    @JsonProperty("tratamiento")
    @NotBlank(message = "Tratamiento es requerido")
    private String treatment;

    @JsonProperty("receta")
    @NotBlank(message = "Receta es requerida")
    private String medication;

    @JsonProperty("observaciones")
    private String notes;
}
