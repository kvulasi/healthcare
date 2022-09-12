package com.example.healthcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.healthcare.models.Patient;

/**
 * Patient Interface declared for all JPA CRUD operations.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
