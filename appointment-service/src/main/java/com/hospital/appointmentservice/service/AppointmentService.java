package com.hospital.appointmentservice.service;

import com.hospital.appointmentservice.dto.AppointmentRequest;
import com.hospital.appointmentservice.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);
    AppointmentResponse getAppointmentById(Long id);
    List<AppointmentResponse> getAllAppointments();
    List<AppointmentResponse> getAppointmentsByPatientId(Long patientId);
    AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest);
    void deleteAppointment(Long id);
    
}
