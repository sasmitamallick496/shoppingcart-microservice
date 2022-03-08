package com.sasmita.musings.shoppingcartservice.exception;

public class InventoryServiceNotAvailableException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InventoryServiceNotAvailableException(String message) {
		super(message);
	}
}
