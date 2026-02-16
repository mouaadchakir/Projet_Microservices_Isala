package com.hospital.medicalrecordservice.service.impl;

import com.hospital.medicalrecordservice.dto.MedicalRecordRequest;
import com.hospital.medicalrecordservice.dto.MedicalRecordResponse;
import com.hospital.medicalrecordservice.feign.PatientClient;
import com.hospital.medicalrecordservice.feign.PatientDto;
import com.hospital.medicalrecordservice.model.MedicalRecord;
import com.hospital.medicalrecordservice.repository.MedicalRecordRepository;
import com.hospital.medicalrecordservice.service.MedicalRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientClient patientClient;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository,
                                    PatientClient patientClient) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientClient = patientClient;
    }

    @Override
    public MedicalRecordResponse addMedicalRecord(MedicalRecordRequest request) {
        // Verify patient exists (if Patient service is down, Feign fallback will return null)
        PatientDto patient = patientClient.getPatientById(request.getPatientId());
        if (patient == null) {
            // graceful degradation: we still allow storing record but without patient details
            // or you could throw custom exception depending on business rules
        }

        MedicalRecord record = new MedicalRecord();
        record.setPatientId(request.getPatientId());
        record.setDiagnosis(request.getDiagnosis());
        record.setDescription(request.getDescription());
        record.setDate(request.getDate());

        MedicalRecord saved = medicalRecordRepository.save(record);

        return toResponse(saved, patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> getMedicalRecordsByPatientId(Long patientId) {
        PatientDto patient = patientClient.getPatientById(patientId);

        return medicalRecordRepository.findByPatientIdOrderByDateDesc(patientId).stream()
                .map(record -> toResponse(record, patient))
                .collect(Collectors.toList());
    }

    private MedicalRecordResponse toResponse(MedicalRecord record, PatientDto patient) {
        MedicalRecordResponse response = new MedicalRecordResponse();
        response.setId(record.getId());
        response.setPatientId(record.getPatientId());
        response.setDiagnosis(record.getDiagnosis());
        response.setDescription(record.getDescription());
        response.setDate(record.getDate());

        if (patient != null) {
            response.setPatientFirstName(patient.getFirstName());
            response.setPatientLastName(patient.getLastName());
        }

        return response;
    }
}
