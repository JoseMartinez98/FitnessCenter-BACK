package com.macaelfitnesscenterback.fitnesscenterback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FitnesscenterbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnesscenterbackApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	System.out.println(encoder.encode("admin123")); 
	}
	
}
