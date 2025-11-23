package com.example.vet.controller;

import com.example.vet.dtos.PetDTO;
import com.example.vet.services.PetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
@Validated
@Slf4j
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetDTO> createPet(@Valid @RequestBody PetDTO petDTO) {
        log.info("Creating pet: {} for owner: {}", petDTO.getName(), petDTO.getOwnerId());
        PetDTO createdPet = petService.createPet(petDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable String id) {
        log.info("Getting pet with id: {}", id);
        PetDTO pet = petService.getPetById(id);
        return ResponseEntity.ok(pet);
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        log.info("Getting all pets");
        List<PetDTO> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwnerId(@PathVariable String ownerId) {
        log.info("Getting pets for owner: {}", ownerId);
        List<PetDTO> pets = petService.getPetsByOwnerId(ownerId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/species/{species}")
    public ResponseEntity<List<PetDTO>> getPetsBySpecies(@PathVariable String species) {
        log.info("Getting pets by species: {}", species);
        List<PetDTO> pets = petService.getPetsBySpecies(species);
        return ResponseEntity.ok(pets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable String id,
                                           @Valid @RequestBody PetDTO petDTO) {
        log.info("Updating pet with id: {}", id);
        PetDTO updatedPet = petService.updatePet(id, petDTO);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable String id) {
        log.info("Deleting pet with id: {}", id);
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
