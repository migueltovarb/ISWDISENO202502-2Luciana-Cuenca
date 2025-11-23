package com.example.vet.controller;

import com.example.vet.dtos.OwnerDTO;
import com.example.vet.services.OwnerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/owners")
@AllArgsConstructor
@Validated
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerDTO> createOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        log.info("Creating owner with document number: {}", ownerDTO.getDocumentNumber());
        OwnerDTO createdOwner = ownerService.createOwner(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable String id) {
        log.info("Getting owner with id: {}", id);
        OwnerDTO owner = ownerService.getOwnerById(id);
        return ResponseEntity.ok(owner);
    }

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> getAllOwners() {
        log.info("Getting all owners");
        List<OwnerDTO> owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<OwnerDTO> getOwnerByDocumentNumber(@PathVariable String documentNumber) {
        log.info("Getting owner with document number: {}", documentNumber);
        OwnerDTO owner = ownerService.getOwnerByDocumentNumber(documentNumber);
        return ResponseEntity.ok(owner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable String id,
                                               @Valid @RequestBody OwnerDTO ownerDTO) {
        log.info("Updating owner with id: {}", id);
        OwnerDTO updatedOwner = ownerService.updateOwner(id, ownerDTO);
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable String id) {
        log.info("Deleting owner with id: {}", id);
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }
}
