package com.sasmita.musings.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ResponseTemplate {
	
	public enum StatusEnum{
		SUCCESS,
		FAILURE
	}
	
	private StatusEnum status;
	
	private String code;
	
	private String message;

}
