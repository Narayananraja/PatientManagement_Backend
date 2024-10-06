package com.patient.repository;
import com.patient.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
