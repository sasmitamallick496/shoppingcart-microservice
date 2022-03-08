package com.sasmita.musings.shoppingcartservice.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.sasmita.musings.shoppingcartservice.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ShoppingCartRequest {

	private UUID cartId;
	
	private List<Item> items;
	
	private PaymentInfo paymentInfo;
	
	private BigDecimal orderTotal;
	
	private ShoppingCartStatus cartStatus;
}
