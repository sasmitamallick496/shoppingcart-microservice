package com.sasmita.musings.priceservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sasmita.musings.priceservice.entity.PriceInfo;
import com.sasmita.musings.priceservice.service.PriceInfoService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class PriceInfoController {
	
	private @Getter PriceInfoService priceService;
	
	@GetMapping("/price-service/{productId}")
	public ResponseEntity<PriceInfo> retrievePriceById(@PathVariable("productId") String productId) {
		log.info("Price-Service Call recieved");
		PriceInfo priceInfo = getPriceService().findPriceByProductId(productId).orElseThrow();
		return new ResponseEntity<PriceInfo>(priceInfo,HttpStatus.OK);
	}
	
}
