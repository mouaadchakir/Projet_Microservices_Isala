package com.hospital.patientservice.service.impl;

import com.hospital.patientservice.dto.PatientRequest;
import com.hospital.patientservice.dto.PatientResponse;
import com.hospital.patientservice.exception.ResourceNotFoundException;
import com.hospital.patientservice.mapper.PatientMapper;
import com.hospital.patientservice.model.Patient;
import com.hospital.patientservice.repository.PatientRepository;
import com.hospital.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponse createPatient(PatientRequest patientRequest) {
        log.info("Creating new patient: {} {}", patientRequest.getPrenom(), patientRequest.getNom());
        
        // Check if patient already exists
        if (patientRepository.existsByNomAndPrenomAndDateNaissance(
                patientRequest.getNom(),
                patientRequest.getPrenom(),
                patientRequest.getDateNaissance())) {
            throw new IllegalArgumentException("Un patient avec le même nom, prénom et date de naissance existe déjà.");
        }

        Patient patient = patientMapper.toEntity(patientRequest);
        Patient savedPatient = patientRepository.save(patient);
        
        log.info("Created patient with ID: {}", savedPatient.getId());
        return patientMapper.toDto(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        log.info("Fetching patient with ID: {}", id);
        
        return patientRepository.findById(id)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé avec l'ID : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        log.info("Fetching all patients");
        
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest patientRequest) {
        log.info("Updating patient with ID: {}", id);
        
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouvé avec l'ID : " + id));
        
        // Update patient fields
        existingPatient.setNom(patientRequest.getNom());
        existingPatient.setPrenom(patientRequest.getPrenom());
        existingPatient.setDateNaissance(patientRequest.getDateNaissance());
        existingPatient.setContact(patientRequest.getContact());
        
        Patient updatedPatient = patientRepository.save(existingPatient);
        log.info("Updated patient with ID: {}", id);
        
        return patientMapper.toDto(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        log.info("Deleting patient with ID: {}", id);
        
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient non trouvé avec l'ID : " + id);
        }
        
        patientRepository.deleteById(id);
        log.info("Deleted patient with ID: {}", id);
    }

    @Override
    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }
}
