package com.sasmita.musings.shoppingcartservice.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	
	private Integer status;
	private String message;
	private String path;
	private List<ValidationError> validationErrors;
	
	@Getter
	@Setter
	@RequiredArgsConstructor
	private static class ValidationError{
		private final String field;
		private final String message;
	}
	
	public void addValidationError(String field,String message) {
		if(Objects.isNull(validationErrors)) {
			validationErrors = new ArrayList<>();
		}
		validationErrors.add(new ValidationError(field,message));
	}

	
	

}
