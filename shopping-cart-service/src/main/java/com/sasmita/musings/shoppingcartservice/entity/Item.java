package com.sasmita.musings.shoppingcartservice.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
	@Id
	@GeneratedValue
	private Long itemId;
	
	@NotBlank(message = "Please provide a valid Product Id")
	private String productId;
	
	@Min(
	value = 1,
	message = "There must be at least 1 quantity in the quantity field")
	private int quantity;
	
	private BigDecimal unitPrice;
	private BigDecimal amount;

	public Item() {
		super();
	}

	public Item(Long itemId, String productId, int quantity, BigDecimal unitPrice, BigDecimal amount) {
		super();
		this.itemId = itemId;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.amount = amount;
	}

}
