package com.sasmita.musings.paymentservice.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
@AllArgsConstructor
public class CreditCard {
	
	private String cardNumber;
	private LocalDate expirationDate;
	private String cvv;
	private BigDecimal limit;

}
