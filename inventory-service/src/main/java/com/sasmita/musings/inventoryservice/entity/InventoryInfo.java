package com.sasmita.musings.inventoryservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class InventoryInfo {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	
	private String productId;
	private Boolean available;
	private Long stockAvailable;
	private int stockThreshold;
	private int cartLimit;
	public InventoryInfo() {
		super();
	}
	
}
