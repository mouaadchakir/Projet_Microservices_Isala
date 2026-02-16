package com.hospital.medicalrecordservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientClient {

    String PATIENT_SERVICE = "patientService";

    @GetMapping("/patients/{id}")
    @CircuitBreaker(name = PATIENT_SERVICE, fallbackMethod = "getPatientByIdFallback")
    @Retry(name = PATIENT_SERVICE)
    PatientDto getPatientById(@PathVariable("id") Long id);

    default PatientDto getPatientByIdFallback(Long id, Throwable throwable) {
        return null;
    }
}
