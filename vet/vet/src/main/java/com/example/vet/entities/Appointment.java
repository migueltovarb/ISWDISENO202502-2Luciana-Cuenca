package com.example.vet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {

    @Id
    private String id;

    @DBRef
    private Pet pet;

    @DBRef
    private User veterinarian;

    private LocalDateTime appointmentDateTime;

    private String reason;

    private String notes;

    private String treatment;

    private AppointmentStatus status;

    public enum AppointmentStatus {
        SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    }
}
