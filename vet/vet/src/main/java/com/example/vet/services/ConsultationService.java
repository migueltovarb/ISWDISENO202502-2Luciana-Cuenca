package com.example.vet.services;

import com.example.vet.dtos.ConsultationDTO;
import com.example.vet.entities.Consultation;
import com.example.vet.entities.Pet;
import com.example.vet.entities.User;
import com.example.vet.exceptions.ResourceNotFoundException;
import com.example.vet.repositories.ConsultationRepository;
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
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public ConsultationDTO createConsultation(ConsultationDTO consultationDTO) {
        Pet pet = petRepository.findById(consultationDTO.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", consultationDTO.getPetId()));

        User veterinarian = userRepository.findById(consultationDTO.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", consultationDTO.getVeterinarianId()));

        Consultation consultation = Consultation.builder()
                .pet(pet)
                .veterinarian(veterinarian)
                .consultationDate(consultationDTO.getConsultationDate())
                .diagnosis(consultationDTO.getDiagnosis())
                .treatment(consultationDTO.getTreatment())
                .medication(consultationDTO.getMedication())
                .notes(consultationDTO.getNotes())
                .build();

        Consultation savedConsultation = consultationRepository.save(consultation);
        return mapToDTO(savedConsultation);
    }

    public ConsultationDTO getConsultationById(String id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        return mapToDTO(consultation);
    }

    public List<ConsultationDTO> getAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationDTO> getConsultationsByPetId(String petId) {
        petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        return consultationRepository.findByPetId(petId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationDTO> getConsultationsByVeterinarianId(String veterinarianId) {
        userRepository.findById(veterinarianId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", veterinarianId));
        return consultationRepository.findByVeterinarianId(veterinarianId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationDTO> getConsultationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return consultationRepository.findByConsultationDateBetween(startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ConsultationDTO updateConsultation(String id, ConsultationDTO consultationDTO) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));

        if (consultationDTO.getConsultationDate() != null) {
            consultation.setConsultationDate(consultationDTO.getConsultationDate());
        }
        if (consultationDTO.getDiagnosis() != null) {
            consultation.setDiagnosis(consultationDTO.getDiagnosis());
        }
        if (consultationDTO.getTreatment() != null) {
            consultation.setTreatment(consultationDTO.getTreatment());
        }
        if (consultationDTO.getMedication() != null) {
            consultation.setMedication(consultationDTO.getMedication());
        }
        if (consultationDTO.getNotes() != null) {
            consultation.setNotes(consultationDTO.getNotes());
        }

        Consultation updatedConsultation = consultationRepository.save(consultation);
        return mapToDTO(updatedConsultation);
    }

    public void deleteConsultation(String id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));
        consultationRepository.delete(consultation);
    }

    private ConsultationDTO mapToDTO(Consultation consultation) {
        return ConsultationDTO.builder()
                .id(consultation.getId())
                .petId(consultation.getPet().getId())
                .veterinarianId(consultation.getVeterinarian().getId())
                .consultationDate(consultation.getConsultationDate())
                .diagnosis(consultation.getDiagnosis())
                .treatment(consultation.getTreatment())
                .medication(consultation.getMedication())
                .notes(consultation.getNotes())
                .build();
    }
}
