package com.hospital.patientservice.repository;

import com.hospital.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByNomAndPrenomAndDateNaissance(String nom, String prenom, LocalDate dateNaissance);
    
    Optional<Patient> findById(Long id);
    
    List<Patient> findAll();
    
    boolean existsById(Long id);
}
