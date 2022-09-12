package com.example.healthcare.controllers;

import lombok.Getter;

/**
 * Class declared for AddressNotFoundException custom exception.
 */
public class AddressNotFoundException extends RuntimeException {
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
	public AddressNotFoundException(String message) {
		this.message = message;
	}

}
