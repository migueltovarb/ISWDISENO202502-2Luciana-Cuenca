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
@Document(collection = "users")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class User extends Person {

    @Indexed(unique = true)
    private String email;

    private String password;

    private UserRole role;

    private Boolean active;

    public enum UserRole {
        ADMIN, VETERINARIAN, RECEPTIONIST, OWNER
    }
}
