package com.hospital.appointmentservice.feign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDto {
    private Long id;
    private String nom;
    private String prenom;
    private String contact;
}
