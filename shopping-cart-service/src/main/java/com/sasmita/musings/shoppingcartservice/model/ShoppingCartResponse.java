package com.sasmita.musings.shoppingcartservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShoppingCartResponse extends ResponseTemplate{

private CartDetailsDTO cartDetails;

public ShoppingCartResponse(StatusEnum status, String code, String message, CartDetailsDTO cartDetails) {
	super(status, code, message);
	this.cartDetails = cartDetails;
}

	
	
	
}
