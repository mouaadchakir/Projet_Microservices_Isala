package com.hospital.patientservice.mapper;

import com.hospital.patientservice.dto.PatientRequest;
import com.hospital.patientservice.dto.PatientResponse;
import com.hospital.patientservice.model.Patient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T14:46:16+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public Patient toEntity(PatientRequest patientRequest) {
        if ( patientRequest == null ) {
            return null;
        }

        Patient patient = new Patient();

        patient.setContact( patientRequest.getContact() );
        patient.setDateNaissance( patientRequest.getDateNaissance() );
        patient.setNom( patientRequest.getNom() );
        patient.setPrenom( patientRequest.getPrenom() );

        return patient;
    }

    @Override
    public PatientResponse toDto(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientResponse.PatientResponseBuilder patientResponse = PatientResponse.builder();

        patientResponse.contact( patient.getContact() );
        patientResponse.createdAt( patient.getCreatedAt() );
        patientResponse.dateNaissance( patient.getDateNaissance() );
        patientResponse.id( patient.getId() );
        patientResponse.nom( patient.getNom() );
        patientResponse.prenom( patient.getPrenom() );
        patientResponse.updatedAt( patient.getUpdatedAt() );

        return patientResponse.build();
    }

    @Override
    public void updatePatientFromRequest(PatientRequest patientRequest, Patient patient) {
        if ( patientRequest == null ) {
            return;
        }

        if ( patientRequest.getContact() != null ) {
            patient.setContact( patientRequest.getContact() );
        }
        if ( patientRequest.getDateNaissance() != null ) {
            patient.setDateNaissance( patientRequest.getDateNaissance() );
        }
        if ( patientRequest.getNom() != null ) {
            patient.setNom( patientRequest.getNom() );
        }
        if ( patientRequest.getPrenom() != null ) {
            patient.setPrenom( patientRequest.getPrenom() );
        }
    }
}
