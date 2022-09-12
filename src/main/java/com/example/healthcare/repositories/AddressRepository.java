package com.example.healthcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthcare.models.Address;

/**
 * Address Interface declared for all JPA CRUD operations.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

}
