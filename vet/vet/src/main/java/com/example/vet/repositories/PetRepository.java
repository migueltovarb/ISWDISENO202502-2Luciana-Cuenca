package com.example.vet.repositories;

import com.example.vet.entities.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    List<Pet> findByOwnerId(String ownerId);
    Optional<Pet> findByMicrochip(String microchip);
    List<Pet> findBySpecies(String species);
}
