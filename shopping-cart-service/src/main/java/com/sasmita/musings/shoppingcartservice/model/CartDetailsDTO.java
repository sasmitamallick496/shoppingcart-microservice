package com.sasmita.musings.shoppingcartservice.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.sasmita.musings.shoppingcartservice.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailsDTO {
	
	private UUID cartId;
	
	private List<Item> items;
	
	private BigDecimal orderTotal;
	
	private ShoppingCartStatus cartStatus;

}
