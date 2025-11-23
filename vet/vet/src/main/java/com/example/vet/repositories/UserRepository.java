package com.example.vet.repositories;

import com.example.vet.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.UserRole role);
    List<User> findByActive(Boolean active);
    Optional<User> findByEmailAndActive(String email, Boolean active);
}
