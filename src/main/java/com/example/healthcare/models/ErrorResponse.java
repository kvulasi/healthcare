package com.example.healthcare.models;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class declared for Error response.
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
	/**
	 * Status code.
	 */
	private HttpStatus statusCode;
	/**
	 * Error message.
	 */
	private String message;
	/**
	 * List of errors.
	 */
	private List<String> errors;
}
