package com.sasmita.musings.shoppingcartservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceInfo {
	
	private String productId;
	private BigDecimal listPrice;
	private BigDecimal salePrice;
	private Boolean onSale;
	private LocalDate effectiveDate;
	
	public PriceInfo(String productId, BigDecimal listPrice, BigDecimal salePrice, Boolean onSale,
			LocalDate effectiveDate) {
		super();
		this.productId = productId;
		this.listPrice = listPrice;
		this.salePrice = salePrice;
		this.onSale = onSale;
		this.effectiveDate = effectiveDate;
	}
	
	

}
