package com.example.healthcare.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.models.Patient;
import com.example.healthcare.models.SuccessResponse;
import com.example.healthcare.services.PatientService;

/**
 * Rest controller class declared for all patients CURD operations.
 */
@RestController
@RequestMapping("/patients")
public class PatientController {
	/**
	 * PatientService injection.
	 **/
	@Autowired
	private PatientService patientService;
	/**
	 * Logger object.
	 */
	private static Logger logger = LogManager.getLogger();

	/**
	 * Method defined get all patients data.
	 * 
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPatients() {
		if (patientService.getPatients().isEmpty()) {
			return new ResponseEntity<Object>(new SuccessResponse(HttpStatus.OK, "Patients data not available", null),
					new HttpHeaders(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(new SuccessResponse(HttpStatus.OK, patientService.getPatients(), null),
					new HttpHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * Method defined to get patient data for given input patient Id.
	 * 
	 * @param patientId {@link Long}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPatient(@PathVariable("id") Long patientId) {
		logger.info("PatientController...getPatient()...patientId = [{}]", patientId);
		return new ResponseEntity<Object>(
				new SuccessResponse(HttpStatus.OK, patientService.getPatient(patientId), null), new HttpHeaders(),
				HttpStatus.OK);
	}

	/**
	 * Method defined to add new patient data.
	 * 
	 * @param patient {@link Patient}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addPatient(@Valid @RequestBody Patient patient) {
		logger.info("PatientController...addPatient()...patient = [{}]", patient);
		return new ResponseEntity<Object>(new SuccessResponse(HttpStatus.OK, patientService.addPatient(patient), null),
				new HttpHeaders(), HttpStatus.CREATED);
	}

	/**
	 * Method defined to update existing patient data.
	 * 
	 * @param patientId     {@link Long}
	 * @param updatePatient {@link Patient}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updatePatient(@PathVariable("id") Long patientId,
			@Valid @RequestBody Patient updatePatient) {
		logger.info("PatientController...updatePatient()...patientId = [{}]...patient = [{}]", patientId,
				updatePatient);
		return new ResponseEntity<Object>(
				new SuccessResponse(HttpStatus.OK, patientService.updatePatient(patientId, updatePatient), null),
				new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Method defined to delete patient data for given input patient Id.
	 * 
	 * @param patientId {@link Long}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deletePatient(@PathVariable("id") Long patientId) {
		logger.info("PatientController...deletePatient()...patientId = [{}]", patientId);
		patientService.deletePatient(patientId);
		return new ResponseEntity<Object>(
				new SuccessResponse(HttpStatus.OK, "Patient is deleted....patientId = " + patientId, null),
				new HttpHeaders(), HttpStatus.OK);
	}
}
