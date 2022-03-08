package com.sasmita.musings.shoppingcartservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sasmita.musings.shoppingcartservice.model.PriceInfo;

@FeignClient(name = "price-service")
public interface PriceProxy {

	@GetMapping("/price-service/{productId}")
	public PriceInfo retrievePriceById(@PathVariable("productId") String productId) throws Exception;

}
