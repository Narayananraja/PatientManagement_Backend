package com.patient.service;

import com.patient.entity.Patient;
import com.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Patient registerPatient(Patient patient) throws Exception {
        if (getPatientByEmail(patient.getEmail()).isPresent()) {
            throw new Exception("Email already exists!"); // Throw exception if email exists
        }
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));  // Hash password
        return patientRepository.save(patient);
    }

    public Optional<Patient> login(String contact, String rawPassword) {
        Optional<Patient> patientOpt = patientRepository.findByContact(contact);

        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (passwordEncoder.matches(rawPassword, patient.getPassword())) {
                return Optional.of(patient);
            }
        }
        return Optional.empty();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        updatedPatient.setId(id);  // Ensure the ID is set for update
        return patientRepository.save(updatedPatient);
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email); // Method to find by email
    }

    public boolean isEmailInUse(String email) {
        return patientRepository.findByEmail(email).isPresent(); // Check if email exists
    }


}
