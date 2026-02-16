package com.hospital.medicalrecordservice.service;

import com.hospital.medicalrecordservice.dto.MedicalRecordRequest;
import com.hospital.medicalrecordservice.dto.MedicalRecordResponse;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordResponse addMedicalRecord(MedicalRecordRequest request);

    List<MedicalRecordResponse> getMedicalRecordsByPatientId(Long patientId);
}
