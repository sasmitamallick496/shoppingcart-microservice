package com.sasmita.musings.shoppingcartservice.exception;

public class PriceServiceNotAvailableException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public PriceServiceNotAvailableException(String message) {
		super(message);
	}
}
