package com.example.vet.repositories;

import com.example.vet.entities.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends MongoRepository<Owner, String> {
    Optional<Owner> findByDocumentNumber(String documentNumber);
    List<Owner> findByEmail(String email);
}
