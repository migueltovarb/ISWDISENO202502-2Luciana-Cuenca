package com.example.vet.services;

import com.example.vet.dtos.OwnerDTO;
import com.example.vet.entities.Owner;
import com.example.vet.exceptions.BadRequestException;
import com.example.vet.exceptions.ResourceNotFoundException;
import com.example.vet.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        if (ownerRepository.findByDocumentNumber(ownerDTO.getDocumentNumber()).isPresent()) {
            throw new BadRequestException("Document number already exists: " + ownerDTO.getDocumentNumber());
        }

        Owner owner = Owner.builder()
                .firstName(ownerDTO.getFirstName())
                .lastName(ownerDTO.getLastName())
                .documentNumber(ownerDTO.getDocumentNumber())
                .email(ownerDTO.getEmail())
                .phone(ownerDTO.getPhone())
                .mobile(ownerDTO.getMobile())
                .address(ownerDTO.getAddress())
                .city(ownerDTO.getCity())
                .state(ownerDTO.getState())
                .postalCode(ownerDTO.getPostalCode())
                .build();

        Owner savedOwner = ownerRepository.save(owner);
        return mapToDTO(savedOwner);
    }

    public OwnerDTO getOwnerById(String id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "id", id));
        return mapToDTO(owner);
    }

    public OwnerDTO getOwnerByDocumentNumber(String documentNumber) {
        Owner owner = ownerRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "documentNumber", documentNumber));
        return mapToDTO(owner);
    }

    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<OwnerDTO> searchOwnersByEmail(String email) {
        return ownerRepository.findByEmail(email).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public OwnerDTO updateOwner(String id, OwnerDTO ownerDTO) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "id", id));

        if (ownerDTO.getDocumentNumber() != null && 
            !ownerDTO.getDocumentNumber().equals(owner.getDocumentNumber())) {
            if (ownerRepository.findByDocumentNumber(ownerDTO.getDocumentNumber()).isPresent()) {
                throw new BadRequestException("Document number already exists: " + ownerDTO.getDocumentNumber());
            }
            owner.setDocumentNumber(ownerDTO.getDocumentNumber());
        }

        if (ownerDTO.getFirstName() != null) {
            owner.setFirstName(ownerDTO.getFirstName());
        }
        if (ownerDTO.getLastName() != null) {
            owner.setLastName(ownerDTO.getLastName());
        }
        if (ownerDTO.getEmail() != null) {
            owner.setEmail(ownerDTO.getEmail());
        }
        if (ownerDTO.getPhone() != null) {
            owner.setPhone(ownerDTO.getPhone());
        }
        if (ownerDTO.getAddress() != null) {
            owner.setAddress(ownerDTO.getAddress());
        }
        if (ownerDTO.getMobile() != null) {
            owner.setMobile(ownerDTO.getMobile());
        }
        if (ownerDTO.getCity() != null) {
            owner.setCity(ownerDTO.getCity());
        }
        if (ownerDTO.getState() != null) {
            owner.setState(ownerDTO.getState());
        }
        if (ownerDTO.getPostalCode() != null) {
            owner.setPostalCode(ownerDTO.getPostalCode());
        }

        Owner updatedOwner = ownerRepository.save(owner);
        return mapToDTO(updatedOwner);
    }

    public void deleteOwner(String id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "id", id));
        ownerRepository.delete(owner);
    }

    private OwnerDTO mapToDTO(Owner owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .documentNumber(owner.getDocumentNumber())
                .email(owner.getEmail())
                .phone(owner.getPhone())
                .mobile(owner.getMobile())
                .address(owner.getAddress())
                .city(owner.getCity())
                .state(owner.getState())
                .postalCode(owner.getPostalCode())
                .build();
    }
}
