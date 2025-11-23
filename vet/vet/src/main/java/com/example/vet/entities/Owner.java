package com.example.vet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "owners")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Owner extends Person {

    @Indexed(unique = true)
    private String documentNumber;

    private String email;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String mobile;
}
