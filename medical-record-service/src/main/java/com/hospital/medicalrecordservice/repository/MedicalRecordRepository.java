package com.hospital.medicalrecordservice.repository;

import com.hospital.medicalrecordservice.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientIdOrderByDateDesc(Long patientId);
}
