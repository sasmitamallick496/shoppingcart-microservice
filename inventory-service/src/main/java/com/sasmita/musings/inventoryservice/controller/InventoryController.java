package com.sasmita.musings.inventoryservice.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sasmita.musings.inventoryservice.entity.InventoryInfo;
import com.sasmita.musings.inventoryservice.service.InventoryService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class InventoryController {
	
	private @Getter InventoryService service;
	
	@GetMapping("/inventory-service/{productId}")
	public ResponseEntity<InventoryInfo> retrieveInventoryById(@PathVariable("productId") String productId) {
		log.info("Inventory-Service Call recieved");
		InventoryInfo inventoryInfo = getService().retrieveInventoryById(productId).orElseThrow();
		return new ResponseEntity<InventoryInfo>(inventoryInfo,HttpStatus.OK);
	}
	@PostMapping("/inventory-service/reserve/{orderId}/{productId}/{quantity}")
	public Boolean reserveInventory(@PathVariable("orderId") UUID orderId, 
			@PathVariable("productId") String productId,
			@PathVariable("quantity") int quantity) {
		return getService().reserveInventory(orderId,productId, quantity);
	}
	
	@PostMapping("/inventory-service/unreserve/{orderId}/{productId}/{quantity}")
	public Boolean unReserveInventory(@PathVariable("orderId") UUID orderId, 
			@PathVariable("productId") String productId,
			@PathVariable("quantity") int quantity) {
		return getService().unReserveInventory(orderId,productId, quantity);
	}
	
	

}
