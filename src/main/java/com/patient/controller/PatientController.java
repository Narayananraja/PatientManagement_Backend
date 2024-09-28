package com.patient.controller;

import com.patient.entity.AdminLoginRequest;
import com.patient.entity.Patient;
import com.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;


    @PostMapping("/login")
    public ResponseEntity<Patient> login(@RequestBody Map<String, String> loginData) {
        return patientService.getPatientByEmail(loginData.get("email"))
                .filter(patient -> passwordEncoder.matches(loginData.get("password"), patient.getPassword())) // Use matches method
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Patient patient) {
        try {
            patientService.registerPatient(patient);
            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error message if email exists
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@PathVariable String email) {
        boolean isAvailable = !patientService.isEmailInUse(email); // Check if email is in use
        return ResponseEntity.ok(Collections.singletonMap("available", isAvailable)); // Return availability status
    }

    @PostMapping("/admin")
    public ResponseEntity<String> adminLogin(@RequestBody AdminLoginRequest loginRequest) {
        // Validate admin credentials
        if (patientService.validateAdminCredentials(loginRequest.getEmail(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful"); // Return success message
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        }
    }

    @GetMapping("/patients-list")
    public ResponseEntity<List<Patient>> getAllPatients() {
        // Fetch all patients from the database
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        boolean isDeleted = patientService.deletePatientById(id); // Service method to delete patient

        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found if patient not found
        }
    }

}
