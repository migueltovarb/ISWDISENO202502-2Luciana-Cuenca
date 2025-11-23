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
public class AppointmentDTO {

    @JsonProperty("idCita")
    private String id;

    @JsonProperty("idMascota")
    @NotBlank(message = "ID de la mascota es requerido")
    private String petId;

    @JsonProperty("idVeterinario")
    @NotBlank(message = "ID del veterinario es requerido")
    private String veterinarianId;

    @JsonProperty("fechaHora")
    @NotNull(message = "Fecha y hora de la cita es requerida")
    private LocalDateTime appointmentDateTime;

    @JsonProperty("diagnostico")
    @NotBlank(message = "Diagnostico es requerido")
    private String reason;

    @JsonProperty("observaciones")
    private String notes;

    @JsonProperty("estado")
    @NotBlank(message = "Estado es requerido")
    private String status;

    @JsonProperty("tratamiento")
    private String treatment;
}
