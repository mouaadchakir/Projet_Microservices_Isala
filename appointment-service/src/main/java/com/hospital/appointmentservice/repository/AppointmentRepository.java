package com.hospital.appointmentservice.repository;

import com.hospital.appointmentservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    
    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);
    
    boolean existsByAppointmentDateBetweenAndPatientId(
        LocalDateTime start, 
        LocalDateTime end, 
        Long patientId
    );
    
    Optional<Appointment> findById(Long id);
    
    boolean existsById(Long id);
}
