package com.hospital.appointmentservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientClient {

    @GetMapping("/patients/{id}")
    @CircuitBreaker(name = "patientService", fallbackMethod = "getPatientFallback")
    @Retry(name = "patientService")
    PatientDto getPatientById(@PathVariable("id") Long id);
    
    @GetMapping("/patients/{id}/exists")
    @CircuitBreaker(name = "patientService", fallbackMethod = "checkPatientExistsFallback")
    @Retry(name = "patientService")
    Boolean checkPatientExists(@PathVariable("id") Long id);
    
    default PatientDto getPatientFallback(Long id, Exception e) {
        // Fallback: no patient details available
        return null;
    }
    
    default Boolean checkPatientExistsFallback(Long id, Exception e) {
        // In case of failure, assume patient doesn't exist to prevent creating appointments for non-existent patients
        return false;
    }
}
