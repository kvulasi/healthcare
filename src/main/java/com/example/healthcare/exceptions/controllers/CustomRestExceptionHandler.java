package com.example.healthcare.exceptions.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.healthcare.exceptions.AddressNotFoundException;
import com.example.healthcare.exceptions.PatientNotFoundException;
import com.example.healthcare.models.ErrorResponse;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author VK103040. Class declared for custom error messages handling globally
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Method defined to handle patient not found exception.
	 * 
	 * @param ex {@link PatientNotFoundException}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@ExceptionHandler(value = PatientNotFoundException.class)
	public ResponseEntity<Object> handlePatientNotFound(final PatientNotFoundException ex) {
		return new ResponseEntity<Object>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null),
				new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Method defined to handle address not found exception.
	 * 
	 * @param ex {@link AddressNotFoundException}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@ExceptionHandler(value = AddressNotFoundException.class)
	public ResponseEntity<Object> handleAddressNotFound(final AddressNotFoundException ex) {
		return new ResponseEntity<Object>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null),
				new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Method defined to handle mismatch data types.
	 * 
	 * @param ex      {@link MethodArgumentTypeMismatchException}
	 * @param request {@link WebRequest}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		List<String> errors = new ArrayList<>();
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		errors.add(error);
		ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusCode());
	}

	/**
	 * Method defined to handle bean fields validations.
	 * 
	 * @param ex      {@link MethodArgumentNotValidException}
	 * @param headers {@link Headers}
	 * @param status  {@link HttpStatus}
	 * @param request {@link WebRequest}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatusCode(), request);
	}

	/**
	 * Method defined to handle internal server exception.
	 * 
	 * @param ex      {@link Exception}
	 * @param request {@link WebRequest}
	 * @return {@link ResponseEntity} of {@link Object}
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		ErrorResponse apiError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatusCode());
	}
}
