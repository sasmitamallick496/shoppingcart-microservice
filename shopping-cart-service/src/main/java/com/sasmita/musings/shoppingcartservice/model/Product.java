package com.sasmita.musings.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Product {
	
	private String productId;
	private String name;
	private String description;
	private String imageUrl;
	private Boolean salable;
}
