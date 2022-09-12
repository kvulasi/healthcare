package com.example.healthcare.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.healthcare.exceptions.AddressNotFoundException;
import com.example.healthcare.exceptions.PatientNotFoundException;
import com.example.healthcare.models.Address;
import com.example.healthcare.models.Patient;
import com.example.healthcare.repositories.AddressRepository;
import com.example.healthcare.repositories.PatientRepository;

/**
 * Service class declared for patient CRUD operations.
 */
@Service
public class PatientService {
	/**
	 * PatientRepository injection.
	 */
	@Autowired
	private PatientRepository patientRepo;
	/**
	 * AddressRepository injection.
	 */
	@Autowired
	private AddressRepository addressRepo;
	/**
	 * Logger object.
	 */
	private static Logger logger = LogManager.getLogger();

	/**
	 * Method defined to get all patients data from database.
	 * 
	 * @return {@link List} of {@link Patient}
	 */
	public List<Patient> getPatients() {
		return patientRepo.findAll();
	}

	/**
	 * Method defined to get patient data by given input patient id from database.
	 * 
	 * @param patientId {@link Long}
	 * @return {@link Patient}
	 */
	public Patient getPatient(Long patientId) {
		logger.info("PatientService...getPatient()...patientId = [{}]", patientId);
		return patientRepo.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with Id = " + patientId));
	}

	/**
	 * Method defined to add new patient data in database.
	 * 
	 * @param patient {@link Patient}
	 * @return {@link List} of {@link Patient}
	 */
	public Patient addPatient(Patient patient) {
		//patient.setPatientName(patient.getFirstName() + " " + patient.getLastName());
		//System.out.println("patient name = " + patient.getPatientName());
		logger.info("PatientService...addPatient()...patient = [{}]", patient);
		return patientRepo.save(patient);
	}

	/**
	 * Method defined to update patient data by given input patient id in database.
	 * 
	 * @param patientId         {@link Long}
	 * @param updatePatientInfo {@link Patient}
	 * @return {@link Patient}
	 */
	public Patient updatePatient(Long patientId, Patient updatePatientInfo) {
		logger.info("PatientService...addPatient()...patientId = [{}]...patient = [{}]", patientId, updatePatientInfo);
		Patient oldPatient = patientRepo.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with Id = " + patientId));
		
		updatePatientInfo.setId(patientId);
		List<Address> oldAddresses = oldPatient.getAddresses();
		List<Address> newAddresses = updatePatientInfo.getAddresses();
				
		for (int i = 0; i < newAddresses.size(); i++) {
			Address newAddress = newAddresses.get(i);
			if (newAddress.getId() == null) {
				oldAddresses.add(newAddress);
			} else {
				Address oldAddress = addressRepo.findById(newAddress.getId())
						.orElseThrow(() -> new AddressNotFoundException(
								"Patient address not found with the Id = " + newAddress.getId()));
				oldAddress.setId(newAddress.getId());
				oldAddress.setPatientId(patientId);
				oldAddress.setAddressType(newAddress.getAddressType());
				oldAddress.setCity(newAddress.getCity());
				oldAddress.setState(newAddress.getState());
				oldAddress.setCountry(newAddress.getAddress());
				oldAddress.setPincode(newAddress.getPincode());
				oldAddresses.add(i, oldAddress);
			}
		}
		oldPatient.setAddresses(oldAddresses);
		oldPatient.setId(patientId);
		oldPatient.setFirstName(updatePatientInfo.getFirstName());
		oldPatient.setLastName(updatePatientInfo.getLastName());
		oldPatient.setGender(updatePatientInfo.getGender());
		oldPatient.setDateOfBirth(updatePatientInfo.getDateOfBirth());
		oldPatient.setPhoneNumber(updatePatientInfo.getPhoneNumber());
		oldPatient.setSecondaryPhoneNumber(updatePatientInfo.getSecondaryPhoneNumber());
		oldPatient.setLandLineNumber(updatePatientInfo.getLandLineNumber());
		oldPatient.setUpdatedDateTime(LocalDateTime.now());

		return patientRepo.save(oldPatient);
	}

	/**
	 * Method defined to delete patient data by given input patient id from
	 * database.
	 * 
	 * @param patientId {@link Long}
	 * @return {@link void}
	 */
	public void deletePatient(Long patientId) {
		logger.info("PatientService...deletePatient()...patientId = [{}]", patientId);
		Patient patient = patientRepo.findById(patientId)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with Id = " + patientId));
		patientRepo.delete(patient);
	}
	
	
}
