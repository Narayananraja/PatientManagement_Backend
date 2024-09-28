package com.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.patient"})
public class PatientManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientManagementApplication.class, args);
	}

}
