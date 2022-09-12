package com.example.healthcare.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.healthcare.repositories.AddressRepository;
import com.example.healthcare.repositories.PatientRepository;
import com.example.healthcare.controllers.AddressNotFoundException;
import com.example.healthcare.exceptions.PatientNotFoundException;
import com.example.healthcare.models.Address;
import com.example.healthcare.models.Patient;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
	@Mock
	private PatientRepository patientRepo;

	@Mock
	private AddressRepository addressRepo;

	@InjectMocks
	private PatientService patientService;

	private List<Patient> getpatient() {
		List<Patient> patients = new ArrayList<Patient>();

		Patient patient1 = new Patient();
		patient1.setId(17L);
		patient1.setFirstName("Kousalya");
		patient1.setLastName("V");
		patient1.setGender("Female");
		patient1.setDateOfBirth(LocalDate.of(1989, 01, 10));
		patient1.setPhoneNumber("0123456789");
		patient1.setSecondaryPhoneNumber(null);
		patient1.setLandLineNumber(null);
		patient1.setCreatedDateTime(LocalDateTime.now());
		patient1.setUpdatedDateTime(LocalDateTime.now());

		List<Address> addresses = new ArrayList<>();
		Address address1 = new Address();
		address1.setId(23L);
		address1.setPatientId(17L);
		address1.setAddressType("Permanenet Address");
		address1.setAddress("Bangalore");
		address1.setCity("Bangalore");
		address1.setState("Karnataka");
		address1.setCountry("India");
		address1.setPincode("560078");

		Address address2 = new Address(234L, 17L, "Current Address", "Bangalore", "Bangalore", "Karnataka", "India",
				"560078");

		addresses.add(address1);
		addresses.add(address2);

		patient1.setAddresses(addresses);

		Patient patient2 = new Patient(18L, "Kousalya", "V", "Female", LocalDate.of(1989, 01, 10), addresses,
				"0123456789", null, null, LocalDateTime.now(), LocalDateTime.now());
		patients.add(patient1);
		patients.add(patient2);

		return patients;
	}

	@Test
	public void getPatients() {
		List<Patient> patients = new ArrayList<>();
		patients.add(getpatient().get(0));

		Mockito.when(patientRepo.findAll()).thenReturn(patients);
		assertThat(!patientService.getPatients().isEmpty());
	}

	@Test
	public void getPatientById() {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient().get(0)));
		Patient patient = patientService.getPatient(17L);
		Address address = patient.getAddresses().get(0);

		assertNotNull(patient.toString());
		assertNotNull(address.toString());
		assertNotNull(patient.getId());
		assertNotNull(patient.getFirstName());
		assertNotNull(patient.getLastName());
		assertNotNull(patient.getGender());
		assertNotNull(patient.getDateOfBirth());
		assertNotNull(patient.getAddresses());
		assertNotNull(patient.getPhoneNumber());
		assertNull(patient.getSecondaryPhoneNumber());
		assertNull(patient.getLandLineNumber());
		assertNotNull(patient.getCreatedDateTime());
		assertNotNull(patient.getUpdatedDateTime());
		assertNotNull(address.getId());
		assertNotNull(address.getPatientId());
		assertNotNull(address.getAddressType());
		assertNotNull(address.getAddress());
		assertNotNull(address.getCity());
		assertNotNull(address.getState());
		assertNotNull(address.getCountry());
		assertNotNull(address.getPincode());

	}

	@Test
	public void getPatientByIdNotFound() {
		Exception exception = Assertions.assertThrows(PatientNotFoundException.class, () -> {
			patientService.getPatient(Mockito.anyLong());
		});
		assertThat(exception.getMessage().contains("Patient not found"));
	}

	@Test
	public void addPatient() {
		Mockito.when(patientRepo.save(Mockito.any(Patient.class))).thenReturn(getpatient().get(0));
		assertNotNull(patientService.addPatient(getpatient().get(0)));
	}

	@Test
	public void updateNewPatient() {
		Patient updatePatient = getpatient().get(0);

		List<Address> addresses = new ArrayList<>();
		Address address = new Address();
		address.setId(null);
		address.setPatientId(17L);
		address.setAddressType("Permanenet Address");
		address.setAddress("Bangalore");
		address.setCity("Bangalore");
		address.setState("Karnataka");
		address.setCountry("India");
		address.setPincode("560078");
		addresses.add(address);
		updatePatient.setAddresses(addresses);

		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient().get(0)));
		assertThat(patientService.updatePatient(17L, updatePatient) == null);
	}

	@Test
	public void updateExistingPatient() {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient().get(0)));
		Mockito.when(addressRepo.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(getpatient().get(0).getAddresses().get(0)));
		assertThat(patientService.updatePatient(17L, getpatient().get(0)) == null);
	}

	@Test
	public void updatePatientNotFound() {
		Exception exception = Assertions.assertThrows(PatientNotFoundException.class, () -> {
			patientService.updatePatient(Mockito.anyLong(), getpatient().get(0));
		});
		assertThat(exception.getMessage().contains("Patient not found"));
	}

	@Test
	public void updateAddressNotFound() {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient().get(0)));
		Exception exception = Assertions.assertThrows(AddressNotFoundException.class, () -> {
			patientService.updatePatient(Mockito.anyLong(), getpatient().get(0));
		});
		assertThat(exception.getMessage().contains("Patient address not found"));
	}

	@Test
	public void deletePatient() {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient().get(0)));
		patientService.deletePatient(1L);
	}

	@Test
	public void deleteForNotFound() {
		Exception exception = Assertions.assertThrows(PatientNotFoundException.class, () -> {
			patientService.deletePatient(Mockito.anyLong());
		});
		assertThat(exception.getMessage().contains("Patient not found"));
	}
}
