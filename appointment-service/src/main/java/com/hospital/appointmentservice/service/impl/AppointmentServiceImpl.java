package com.hospital.appointmentservice.service.impl;

import com.hospital.appointmentservice.dto.AppointmentRequest;
import com.hospital.appointmentservice.dto.AppointmentResponse;
import com.hospital.appointmentservice.exception.AppointmentException;
import com.hospital.appointmentservice.feign.PatientClient;
import com.hospital.appointmentservice.feign.PatientDto;
import com.hospital.appointmentservice.model.Appointment;
import com.hospital.appointmentservice.model.Appointment.AppointmentStatus;
import com.hospital.appointmentservice.repository.AppointmentRepository;
import com.hospital.appointmentservice.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  PatientClient patientClient) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        // Verify patient existence via Patient Service (Feign + Resilience4j)
        boolean patientExists = patientClient.checkPatientExists(request.getPatientId());
        if (!patientExists) {
            throw new AppointmentException("Patient with id " + request.getPatientId() + " does not exist");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());
        appointment.setPatientId(request.getPatientId());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment saved = appointmentRepository.save(appointment);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentException("Appointment not found with id " + id));

        return toResponse(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentException("Appointment not found with id " + id));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());

        Appointment updated = appointmentRepository.save(appointment);
        return toResponse(updated);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentException("Appointment not found with id " + id));
        appointmentRepository.delete(appointment);
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate())
                .reason(appointment.getReason())
                .patientId(appointment.getPatientId())
                .status(appointment.getStatus())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }
}
