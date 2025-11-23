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
@Document(collection = "consultations")
public class Consultation {

    @Id
    private String id;

    @DBRef
    private Pet pet;

    @DBRef
    private User veterinarian;

    private LocalDateTime consultationDate;

    private String diagnosis;

    private String treatment;

    private String medication;

    private String notes;
}
