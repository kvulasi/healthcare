package com.example.healthcare.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class declared for patients addresses.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "addresses")
public class Address {
	/**
	 * Address Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long id;
	/**
	 * Patient Id.
	 */
	@Column(name = "patient_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long patientId;
	/**
	 * Address type.
	 */
	@Pattern(regexp = "^(?:Permanent Address|Current Address)", message = "Possible values = Permanent Address/Current Address")
	@NotBlank(message = "Please enter addressType")
	@Column(name = "address_type")
	private String addressType;
	/**
	 * Address.
	 */
	@NotBlank(message = "Please enter address")
	@Size(max = 225, message = "Length of the address should be less than 255 characters")
	@Column(name = "address")
	private String address;
	/**
	 * City.
	 */
	@NotBlank(message = "Please enter city")
	@Size(max = 225, message = "Length of the city should be less than 255 characters")
	@Column(name = "city")
	private String city;
	/**
	 * State.
	 */
	@NotBlank(message = "Please enter state")
	@Size(max = 225, message = "Length of the state should be less than 255 characters")
	@Column(name = "state")
	private String state;
	/**
	 * Country.
	 */
	@NotBlank(message = "Please enter country")
	@Size(max = 225, message = "Length of the country should be less than 255 characters")
	@Column(name = "country")
	private String country;
	/**
	 * Pincode.
	 */
	@NotBlank(message = "Please enter pincode")
	@Size(max = 6, message = "Length of the pincode should be 6")
	@Pattern(regexp = "[0-9]*", message = "Enter pincode number in digits")
	@Column(name = "pincode")
	private String pincode;
}
