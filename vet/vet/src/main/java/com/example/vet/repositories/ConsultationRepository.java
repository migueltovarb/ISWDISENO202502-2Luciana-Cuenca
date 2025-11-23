package com.example.vet.repositories;

import com.example.vet.entities.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, String> {
    List<Consultation> findByPetId(String petId);
    List<Consultation> findByVeterinarianId(String veterinarianId);
    List<Consultation> findByConsultationDateBetween(LocalDateTime start, LocalDateTime end);
}
