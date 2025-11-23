package com.example.vet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

/**
 * Base class for people-related documents to share common fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Person {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String phone;
}
