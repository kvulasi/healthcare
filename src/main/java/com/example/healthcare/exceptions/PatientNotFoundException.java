package com.example.healthcare.exceptions;

import lombok.Getter;

/**
 * Class declared for PatientNotFoundException custom exception.
 */
public class PatientNotFoundException extends RuntimeException {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Exception message.
	 */
	@Getter
	private String message;

	/**
	 * AllArgsConstructor.
	 * 
	 * @param message {@link String}
	 */
	public PatientNotFoundException(String message) {
		this.message = message;
	}
}
