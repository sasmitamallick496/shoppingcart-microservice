package com.sasmita.musings.shoppingcartservice.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sasmita.musings.shoppingcartservice.model.InventoryInfo;

@FeignClient(name = "inventory-service")
public interface InventoryProxy {

	@GetMapping("/inventory-service/{productId}")
	public InventoryInfo retrieveInventoryById(@PathVariable("productId") String productId) throws Exception;

	@PostMapping("/inventory-service/reserve/{orderId}/{productId}/{quantity}")
	public Boolean reserveInventory(@PathVariable("orderId") UUID orderId, @PathVariable("productId") String productId,
			@PathVariable("quantity") int quantity);

	@PostMapping("/inventory-service/unreserve/{orderId}/{productId}/{quantity}")
	public Boolean unReserveInventory(@PathVariable("orderId") UUID orderId,
			@PathVariable("productId") String productId, @PathVariable("quantity") int quantity);

}
