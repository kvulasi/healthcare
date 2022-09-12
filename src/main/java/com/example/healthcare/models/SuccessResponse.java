package com.example.healthcare.models;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class declared to success response.
 */
@Getter
@AllArgsConstructor
public class SuccessResponse {
	/**
	 * Status code.
	 */
	private HttpStatus statusCode;
	/**
	 * Response body.
	 */
	private Object responseBody;
	/**
	 * List of errors.
	 */
	private List<String> errors;
}
