package com.sasmita.musings.shoppingcartservice.model;

import java.util.List;

import com.sasmita.musings.shoppingcartservice.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubmitOrderContext {
	
	private ShoppingCartRequest request;
	
	private List<Item>reservedItems;

}
