package com.hospital.medicalrecordservice.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class PatientDto {

    private Long id;

    @JsonProperty("prenom")
    private String firstName;

    @JsonProperty("nom")
    private String lastName;

    @JsonProperty("dateNaissance")
    private LocalDate dateOfBirth;

    private String contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}