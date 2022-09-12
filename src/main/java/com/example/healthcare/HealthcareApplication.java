package com.example.healthcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Class declared for spring boot main method.
 */
@SpringBootApplication
@EnableJpaRepositories("com.example.healthcare.repositories")
public class HealthcareApplication {
	/**
	 * Main method.
	 * 
	 * @param args {@link Array} of {@link String}
	 */
	public static void main(String[] args) {
		SpringApplication.run(HealthcareApplication.class, args);
	}
}
