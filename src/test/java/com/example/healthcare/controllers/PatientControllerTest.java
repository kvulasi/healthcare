package com.example.healthcare.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.healthcare.exceptions.AddressNotFoundException;
import com.example.healthcare.exceptions.PatientNotFoundException;
import com.example.healthcare.models.Address;
import com.example.healthcare.models.Patient;
import com.example.healthcare.repositories.AddressRepository;
import com.example.healthcare.repositories.PatientRepository;
import com.example.healthcare.services.PatientService;

@WebMvcTest
public class PatientControllerTest {
	@SuppressWarnings("unused")
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientRepository patientRepo;

	@MockBean
	private AddressRepository addressRepo;

	@MockBean
	private PatientService patientService;

	@InjectMocks
	private PatientController patientController;

	private String validResponseBody = "{" + "\"firstName\":\"Kousalya\"," + "\"lastName\":\"V\","
			+ "\"gender\":\"Female\"," + "\"dateOfBirth\":\"1989-05-10\"," + "\"addresses\":[{"
			+ "\"addressType\":\"CurrentAddress\"," + "\"address\":\"Bangalore\"," + "\"city\":\"Bangalore\","
			+ "\"state\":\"Karnataka\"," + "\"country\":\"India\"," + "\"pincode\":\"560078\"}],"
			+ "\"phoneNumber\":\"1234567890\"}";

	private String invalidResponseBody = "{" + "\"firstName\":\"@\"," + "\"lastName\":\"V\"," + "\"gender\":\"Female\","
			+ "\"dateOfBirth\":\"1989-05-10\"," + "\"addresses\":[{" + "\"addressType\":\"CurrentAddress\","
			+ "\"address\":\"Bangalore\"," + "\"city\":\"Bangalore\"," + "\"state\":\"Karnataka\","
			+ "\"country\":\"India\"," + "\"pincode\":\"560078\"}]," + "\"phoneNumber\":\"1234567890\"}";

	private Patient getpatient() {
		Patient patient = new Patient();
		patient.setId(17L);
		patient.setFirstName("Kousalya");
		patient.setLastName("V");
		patient.setGender("Female");
		patient.setDateOfBirth(LocalDate.of(1989, 01, 10));
		patient.setPhoneNumber("0123456789");
		patient.setSecondaryPhoneNumber(null);
		patient.setLandLineNumber(null);
		patient.setCreatedDateTime(LocalDateTime.now());
		patient.setUpdatedDateTime(LocalDateTime.now());

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

		addresses.add(address1);
		patient.setAddresses(addresses);

		return patient;
	}

	@Test
	public void getPatients() throws Exception {
		List<Patient> patients = new ArrayList<>();
		patients.add(getpatient());
		Mockito.when(patientRepo.findAll()).thenReturn(patients);
		Mockito.when(patientService.getPatients()).thenReturn(patients);

		MvcResult requestResult = this.mockMvc.perform(get("/patients/")).andExpect(status().isOk())
				.andExpect(status().isOk()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void getPatientsEmpty() throws Exception {
		Mockito.when(patientRepo.findAll()).thenReturn(new ArrayList<Patient>());
		Mockito.when(patientService.getPatients()).thenReturn(new ArrayList<Patient>());

		MvcResult requestResult = this.mockMvc.perform(get("/patients/")).andExpect(status().isOk())
				.andExpect(status().isOk()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void getPatientById() throws Exception {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient()));
		Mockito.when(patientService.getPatient(Mockito.anyLong())).thenReturn(getpatient());

		MvcResult requestResult = this.mockMvc.perform(get("/patients/1")).andExpect(status().isOk())
				.andExpect(status().isOk()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void getPatientByIdNotFound() throws Exception {
		Mockito.when(patientService.getPatient(Mockito.anyLong())).thenThrow(PatientNotFoundException.class);

		MvcResult requestResult = this.mockMvc.perform(get("/patients/110")).andExpect(status().isNotFound())
				.andExpect(status().isNotFound()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void getPatientByIdAddressNotFound() throws Exception {
		Mockito.when(patientService.getPatient(Mockito.anyLong())).thenThrow(AddressNotFoundException.class);

		MvcResult requestResult = this.mockMvc.perform(get("/patients/110")).andExpect(status().isNotFound())
				.andExpect(status().isNotFound()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void getPatientByInvalidPatientId() throws Exception {
		MvcResult requestResult = this.mockMvc.perform(get("/patients/@")).andExpect(status().isBadRequest())
				.andExpect(status().isBadRequest()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void addPatient() throws Exception {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient()));
		Mockito.when(patientService.getPatient(Mockito.anyLong())).thenReturn(getpatient());

		MvcResult requestResult = mockMvc
				.perform(post("/patients/create").header("Content-Type", MediaType.APPLICATION_JSON)
						.content(validResponseBody))
				.andExpect(status().isCreated()).andExpect(status().isCreated()).andReturn();

		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void addPatientWithInvalidData() throws Exception {
		MvcResult requestResult = mockMvc
				.perform(post("/patients/create").header("Content-Type", MediaType.APPLICATION_JSON)
						.content(invalidResponseBody))
				.andExpect(status().isBadRequest()).andExpect(status().isBadRequest()).andReturn();

		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void updatePatient() throws Exception {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient()));
		Mockito.when(patientService.updatePatient(Mockito.anyLong(), Mockito.any(Patient.class)))
				.thenReturn(getpatient());

		MvcResult requestResult = mockMvc.perform(
				put("/patients/17").header("Content-Type", MediaType.APPLICATION_JSON).content(validResponseBody))
				.andExpect(status().isOk()).andExpect(status().isOk()).andReturn();

		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void deletePatient() throws Exception {
		Mockito.when(patientRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(getpatient()));

		MvcResult requestResult = this.mockMvc.perform(delete("/patients/1")).andExpect(status().isOk())
				.andExpect(status().isOk()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}

	@Test
	public void getPatientWithNull() throws Exception {
		Mockito.when(patientService.getPatient(Mockito.anyLong())).thenThrow(NullPointerException.class);

		MvcResult requestResult = this.mockMvc.perform(get("/patients/1")).andExpect(status().isInternalServerError())
				.andExpect(status().isInternalServerError()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		assertNotNull(result);
	}
}
