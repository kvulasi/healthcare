package com.example.healthcare.models;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Class declared for patients.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "patients")
@Data
public class Patient {
	/**
	 * Patient Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long id;
	/**
	 * Patient first name.
	 */
	@Pattern(regexp = "[a-zA-Z0-9]*", message = "Enter valid first name")
	@NotBlank(message = "Please enter first name")
	@Size(max = 225, message = "Length of the first name should be less than 255 characters")
	@JsonProperty(access = Access.WRITE_ONLY)
	@Transient
	private String firstName;
	/**
	 * Patient last name.
	 */
	@Pattern(regexp = "[a-zA-Z0-9]*", message = "Enter valid last name")
	@NotBlank(message = "Please enter last name")
	@Size(max = 225, message = "Length of the last name should be less than 255 characters")
	@JsonProperty(access = Access.WRITE_ONLY)
	@Transient
	private String lastName;

	/**
	 * Patient gender.
	 */
	@Pattern(regexp = "^(?:Male|Female)", message = "Possible values = Female/Male")
	@NotBlank(message = "Please enter gender")
	@Column(name = "gender")
	private String gender;
	/**
	 * Patient dateOfBirth.
	 */
	@Column(name = "date_of_birth")
	@Past
	private LocalDate dateOfBirth;
	/**
	 * Patient dateOfBirth.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
	@NotNull(message = "Please enter addresses")
	private List<Address> addresses;
	/**
	 * Patient phone number.
	 */
	@Pattern(regexp = "[0-9]*", message = "Enter phone number in digits")
	@NotBlank(message = "Please enter phone number")
	@Size(min = 10, max = 10, message = "Length of the phone number must be 10")
	@Column(name = "phone_number")
	private String phoneNumber;
	/**
	 * Patient secondary phone number.
	 */
	@Pattern(regexp = "[0-9]*", message = "Enter secondary phone number in digits")
	@Size(min = 10, max = 10, message = "Length of the secondary phone number must be 10")
	@Column(name = "secondary_phone_number")
	private String secondaryPhoneNumber;
	/**
	 * Patient land line number.
	 */
	@Column(name = "landline_number")
	private Long landLineNumber;
	/**
	 * Patient created date time.
	 */
	@JsonIgnore
	@Column(name = "created_datetime")
	private LocalDateTime createdDateTime = LocalDateTime.now();
	/**
	 * Patient modified date time.
	 */
	@Column(name = "modified_datetime")
	@JsonIgnore
	private LocalDateTime updatedDateTime;
	/**
	 * Patient full name.
	 * 
	 * @return {@link String}
	 *//*
		 * public String getPatientName() { return this.firstName + " " + this.lastName;
		 * }
		 */
}
