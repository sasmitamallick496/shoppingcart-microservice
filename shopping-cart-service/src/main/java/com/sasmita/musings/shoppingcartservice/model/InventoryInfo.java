package com.sasmita.musings.shoppingcartservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryInfo {
	
	private String productId;
	private Boolean available;
	private Long stockAvailable;
	private int stockThreshold;
	private int cartLimit;

}
