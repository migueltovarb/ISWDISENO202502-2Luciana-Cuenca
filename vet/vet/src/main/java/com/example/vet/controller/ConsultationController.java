package com.example.vet.controller;

import com.example.vet.dtos.ConsultationDTO;
import com.example.vet.services.ConsultationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/consultations")
@AllArgsConstructor
@Validated
@Slf4j
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationDTO> createConsultation(@Valid @RequestBody ConsultationDTO consultationDTO) {
        log.info("Creating consultation for pet: {} by veterinarian: {}", 
                consultationDTO.getPetId(), consultationDTO.getVeterinarianId());
        ConsultationDTO createdConsultation = consultationService.createConsultation(consultationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConsultation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDTO> getConsultationById(@PathVariable String id) {
        log.info("Getting consultation with id: {}", id);
        ConsultationDTO consultation = consultationService.getConsultationById(id);
        return ResponseEntity.ok(consultation);
    }

    @GetMapping
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {
        log.info("Getting all consultations");
        List<ConsultationDTO> consultations = consultationService.getAllConsultations();
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ConsultationDTO>> getConsultationsByPetId(@PathVariable String petId) {
        log.info("Getting consultations for pet: {}", petId);
        List<ConsultationDTO> consultations = consultationService.getConsultationsByPetId(petId);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/veterinarian/{veterinarianId}")
    public ResponseEntity<List<ConsultationDTO>> getConsultationsByVeterinarianId(@PathVariable String veterinarianId) {
        log.info("Getting consultations for veterinarian: {}", veterinarianId);
        List<ConsultationDTO> consultations = consultationService.getConsultationsByVeterinarianId(veterinarianId);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/range")
    public ResponseEntity<List<ConsultationDTO>> getConsultationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("Getting consultations between {} and {}", start, end);
        List<ConsultationDTO> consultations = consultationService.getConsultationsByDateRange(start, end);
        return ResponseEntity.ok(consultations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationDTO> updateConsultation(@PathVariable String id,
                                                             @Valid @RequestBody ConsultationDTO consultationDTO) {
        log.info("Updating consultation with id: {}", id);
        ConsultationDTO updatedConsultation = consultationService.updateConsultation(id, consultationDTO);
        return ResponseEntity.ok(updatedConsultation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable String id) {
        log.info("Deleting consultation with id: {}", id);
        consultationService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }
}
