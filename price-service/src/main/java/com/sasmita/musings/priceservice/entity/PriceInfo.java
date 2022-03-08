package com.sasmita.musings.priceservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
public class PriceInfo {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	
	private String productId;
	private BigDecimal listPrice;
	private BigDecimal salePrice;
	private Boolean onSale;
	private LocalDate effectiveDate;
	public PriceInfo() {
		super();
	}
}
