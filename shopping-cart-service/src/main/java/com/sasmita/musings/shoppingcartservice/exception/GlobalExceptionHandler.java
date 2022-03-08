package com.sasmita.musings.shoppingcartservice.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(CartNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildErrorResponse(ex, HttpStatus.NOT_FOUND.value(), request));
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildErrorResponse(ex, HttpStatus.NOT_FOUND.value(), request));		
	}
	
	@ExceptionHandler(InventoryNotAvailableException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handleInventoryNotAvailableException(InventoryNotAvailableException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildErrorResponse(ex, HttpStatus.NOT_FOUND.value(), request));		
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){
		ErrorResponse errorResponse = buildErrorResponse(ex, HttpStatus.BAD_REQUEST.value(), request);
		if(!ex.getConstraintViolations().isEmpty()) {
			for(ConstraintViolation constraints : ex.getConstraintViolations()) {
				errorResponse.addValidationError(constraints.getPropertyPath().toString(), constraints.getMessage());
			}
			
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		
	}
	@ExceptionHandler(InventoryServiceNotAvailableException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handleInventoryServiceNotAvailable(InventoryServiceNotAvailableException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildErrorResponse(ex, HttpStatus.NOT_FOUND.value(), request));		
	}
	@ExceptionHandler(PriceServiceNotAvailableException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handlePriceServiceNotAvailable(PriceServiceNotAvailableException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildErrorResponse(ex, HttpStatus.NOT_FOUND.value(), request));		
	}
	@ExceptionHandler(PaymentFailedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handlePaymentFailed(PaymentFailedException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildErrorResponse(ex, HttpStatus.NOT_FOUND.value(), request));		
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleAllUncaughtException(Exception ex,WebRequest request){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.value(), request));
	}
	
	private ErrorResponse buildErrorResponse(Exception ex, Integer status, WebRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder().status(status)
															 .message(ex.getLocalizedMessage())
															 .path(request.getDescription(false))
															 .build();
		return errorResponse;
	}
	

}
