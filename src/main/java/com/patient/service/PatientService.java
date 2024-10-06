package com.patient.service;

import com.patient.entity.Doctor;
import com.patient.entity.Patient;
import com.patient.repository.DoctorRepository;
import com.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

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

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public boolean validateAdminCredentials(String email, String password) {
        // Check if the provided credentials match the expected admin credentials
        return adminEmail.equals(email) && adminPassword.equals(password);
    }

    public boolean deletePatientById(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true; // Indicate successful deletion
        }
        return false; // Indicate patient not found
    }
    public Doctor dregister(Doctor doctor){
        return doctorRepository.save(doctor);
    }
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    public boolean deleteDoctorById(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true; // Indicate successful deletion
        }
        return false; // Indicate patient not found
    }
    public Doctor editedDoctor(Long id, Doctor editedDoctor) {
        editedDoctor.setId(id);  // Ensure the ID is set for update
        return doctorRepository.save(editedDoctor);

    }


}
