package com.example.vet.services;

import com.example.vet.dtos.PetDTO;
import com.example.vet.entities.Owner;
import com.example.vet.entities.Pet;
import com.example.vet.exceptions.BadRequestException;
import com.example.vet.exceptions.ResourceNotFoundException;
import com.example.vet.repositories.OwnerRepository;
import com.example.vet.repositories.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetDTO createPet(PetDTO petDTO) {
        Owner owner = ownerRepository.findById(petDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "id", petDTO.getOwnerId()));

        if (petDTO.getMicrochip() != null && !petDTO.getMicrochip().isEmpty()) {
            if (petRepository.findByMicrochip(petDTO.getMicrochip()).isPresent()) {
                throw new BadRequestException("Microchip already exists: " + petDTO.getMicrochip());
            }
        }

        Pet pet = Pet.builder()
                .name(petDTO.getName())
                .species(petDTO.getSpecies())
                .breed(petDTO.getBreed())
                .dateOfBirth(petDTO.getDateOfBirth())
                .color(petDTO.getColor())
                .microchip(petDTO.getMicrochip())
                .weight(petDTO.getWeight())
                .owner(owner)
                .medicalHistory(petDTO.getMedicalHistory())
                .build();

        Pet savedPet = petRepository.save(pet);
        return mapToDTO(savedPet);
    }

    public PetDTO getPetById(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", id));
        return mapToDTO(pet);
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PetDTO> getPetsByOwnerId(String ownerId) {
        ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "id", ownerId));
        return petRepository.findByOwnerId(ownerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PetDTO> getPetsBySpecies(String species) {
        return petRepository.findBySpecies(species).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PetDTO getPetByMicrochip(String microchip) {
        Pet pet = petRepository.findByMicrochip(microchip)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "microchip", microchip));
        return mapToDTO(pet);
    }

    public PetDTO updatePet(String id, PetDTO petDTO) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", id));

        if (petDTO.getMicrochip() != null && !petDTO.getMicrochip().isEmpty() &&
            !petDTO.getMicrochip().equals(pet.getMicrochip())) {
            if (petRepository.findByMicrochip(petDTO.getMicrochip()).isPresent()) {
                throw new BadRequestException("Microchip already exists: " + petDTO.getMicrochip());
            }
            pet.setMicrochip(petDTO.getMicrochip());
        }

        if (petDTO.getName() != null) {
            pet.setName(petDTO.getName());
        }
        if (petDTO.getSpecies() != null) {
            pet.setSpecies(petDTO.getSpecies());
        }
        if (petDTO.getBreed() != null) {
            pet.setBreed(petDTO.getBreed());
        }
        if (petDTO.getDateOfBirth() != null) {
            pet.setDateOfBirth(petDTO.getDateOfBirth());
        }
        if (petDTO.getColor() != null) {
            pet.setColor(petDTO.getColor());
        }
        if (petDTO.getWeight() != null) {
            pet.setWeight(petDTO.getWeight());
        }
        if (petDTO.getMedicalHistory() != null) {
            pet.setMedicalHistory(petDTO.getMedicalHistory());
        }

        Pet updatedPet = petRepository.save(pet);
        return mapToDTO(updatedPet);
    }

    public void deletePet(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", id));
        petRepository.delete(pet);
    }

    private PetDTO mapToDTO(Pet pet) {
        return PetDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .dateOfBirth(pet.getDateOfBirth())
                .color(pet.getColor())
                .microchip(pet.getMicrochip())
                .weight(pet.getWeight())
                .ownerId(pet.getOwner().getId())
                .medicalHistory(pet.getMedicalHistory())
                .build();
    }
}
