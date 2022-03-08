package com.sasmita.musings.shoppingcartservice.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentInfo {
	
	private UUID orderId;	
	private String cardNumber;
	private BigDecimal amount;
}
