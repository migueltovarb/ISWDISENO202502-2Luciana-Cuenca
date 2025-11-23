package com.example.vet.controller;

import com.example.vet.dtos.AppointmentDTO;
import com.example.vet.services.AppointmentService;
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
@RequestMapping("/appointments")
@AllArgsConstructor
@Validated
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        log.info("Creating appointment for pet: {} with veterinarian: {}", 
                appointmentDTO.getPetId(), appointmentDTO.getVeterinarianId());
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable String id) {
        log.info("Getting appointment with id: {}", id);
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        log.info("Getting all appointments");
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPetId(@PathVariable String petId) {
        log.info("Getting appointments for pet: {}", petId);
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPetId(petId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/veterinarian/{veterinarianId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByVeterinarianId(@PathVariable String veterinarianId) {
        log.info("Getting appointments for veterinarian: {}", veterinarianId);
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByVeterinarianId(veterinarianId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByStatus(@PathVariable String status) {
        log.info("Getting appointments with status: {}", status);
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByStatus(status);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/range")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("Getting appointments between {} and {}", start, end);
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDateRange(start, end);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable String id,
                                                           @Valid @RequestBody AppointmentDTO appointmentDTO) {
        log.info("Updating appointment with id: {}", id);
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String id) {
        log.info("Deleting appointment with id: {}", id);
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
