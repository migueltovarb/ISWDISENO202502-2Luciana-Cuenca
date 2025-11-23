package com.example.vet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pets")
public class Pet {

    @Id
    private String id;

    private String name;

    private String species;

    private String breed;

    private LocalDate dateOfBirth;

    private String color;

    @Indexed(unique = true, sparse = true)
    private String microchip;

    private Double weight;

    @DBRef
    private Owner owner;

    private String medicalHistory;
}
