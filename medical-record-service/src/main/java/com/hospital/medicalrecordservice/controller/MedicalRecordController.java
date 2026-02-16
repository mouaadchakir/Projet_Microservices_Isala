package com.hospital.medicalrecordservice.controller;

import com.hospital.medicalrecordservice.dto.MedicalRecordRequest;
import com.hospital.medicalrecordservice.dto.MedicalRecordResponse;
import com.hospital.medicalrecordservice.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> addMedicalRecord(@Valid @RequestBody MedicalRecordRequest request) {
        MedicalRecordResponse response = medicalRecordService.addMedicalRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordResponse>> getMedicalRecordsByPatient(@PathVariable Long patientId) {
        List<MedicalRecordResponse> responses = medicalRecordService.getMedicalRecordsByPatientId(patientId);
        return ResponseEntity.ok(responses);
    }
}
