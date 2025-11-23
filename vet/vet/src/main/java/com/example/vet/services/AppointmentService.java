package com.example.vet.services;

import com.example.vet.dtos.AppointmentDTO;
import com.example.vet.entities.Appointment;
import com.example.vet.entities.Pet;
import com.example.vet.entities.User;
import com.example.vet.exceptions.BadRequestException;
import com.example.vet.exceptions.ResourceNotFoundException;
import com.example.vet.repositories.AppointmentRepository;
import com.example.vet.repositories.PetRepository;
import com.example.vet.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        Pet pet = petRepository.findById(appointmentDTO.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", appointmentDTO.getPetId()));

        User veterinarian = userRepository.findById(appointmentDTO.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", appointmentDTO.getVeterinarianId()));

        if (!veterinarian.getRole().toString().equals("VETERINARIAN")) {
            throw new BadRequestException("User must have VETERINARIAN role");
        }

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .veterinarian(veterinarian)
                .appointmentDateTime(appointmentDTO.getAppointmentDateTime())
                .reason(appointmentDTO.getReason())
                .notes(appointmentDTO.getNotes())
                .treatment(appointmentDTO.getTreatment())
                .status(parseStatus(appointmentDTO.getStatus()))
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToDTO(savedAppointment);
    }

    public AppointmentDTO getAppointmentById(String id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        return mapToDTO(appointment);
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByPetId(String petId) {
        petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        return appointmentRepository.findByPetId(petId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByVeterinarianId(String veterinarianId) {
        userRepository.findById(veterinarianId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", veterinarianId));
        return appointmentRepository.findByVeterinarianId(veterinarianId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByStatus(String status) {
        Appointment.AppointmentStatus appointmentStatus = parseStatus(status);
        return appointmentRepository.findByStatus(appointmentStatus).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByAppointmentDateTimeBetween(startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO updateAppointment(String id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        if (appointmentDTO.getAppointmentDateTime() != null) {
            appointment.setAppointmentDateTime(appointmentDTO.getAppointmentDateTime());
        }
        if (appointmentDTO.getReason() != null) {
            appointment.setReason(appointmentDTO.getReason());
        }
        if (appointmentDTO.getNotes() != null) {
            appointment.setNotes(appointmentDTO.getNotes());
        }
        if (appointmentDTO.getTreatment() != null) {
            appointment.setTreatment(appointmentDTO.getTreatment());
        }
        if (appointmentDTO.getStatus() != null) {
            appointment.setStatus(parseStatus(appointmentDTO.getStatus()));
        }

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return mapToDTO(updatedAppointment);
    }

    public void deleteAppointment(String id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        appointmentRepository.delete(appointment);
    }

    private AppointmentDTO mapToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .petId(appointment.getPet().getId())
                .veterinarianId(appointment.getVeterinarian().getId())
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .reason(appointment.getReason())
                .notes(appointment.getNotes())
                .treatment(appointment.getTreatment())
                .status(formatStatus(appointment.getStatus()))
                .build();
    }

    private Appointment.AppointmentStatus parseStatus(String rawStatus) {
        String normalized = rawStatus.toUpperCase();
        return switch (normalized) {
            case "PROGRAMADA", "PROGRAMADO", "SCHEDULED" -> Appointment.AppointmentStatus.SCHEDULED;
            case "EN_PROGRESO", "EN PROGRESO", "IN_PROGRESS" -> Appointment.AppointmentStatus.IN_PROGRESS;
            case "COMPLETADA", "COMPLETADO", "COMPLETED" -> Appointment.AppointmentStatus.COMPLETED;
            case "CANCELADA", "CANCELADO", "CANCELLED" -> Appointment.AppointmentStatus.CANCELLED;
            default -> throw new BadRequestException("Estado de cita no soportado: " + rawStatus);
        };
    }

    private String formatStatus(Appointment.AppointmentStatus status) {
        return switch (status) {
            case SCHEDULED -> "PROGRAMADA";
            case IN_PROGRESS -> "EN_PROGRESO";
            case COMPLETED -> "COMPLETADA";
            case CANCELLED -> "CANCELADA";
        };
    }
}
