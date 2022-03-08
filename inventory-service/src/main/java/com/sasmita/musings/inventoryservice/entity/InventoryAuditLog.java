package com.sasmita.musings.inventoryservice.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class InventoryAuditLog {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	
	private String productId;
	private UUID orderId;
	private int quantity;
	private String inventoryAction;
	private String status;
	
	public InventoryAuditLog() {
		super();
	}
}
