package com.sasmita.musings.shoppingcartservice.validator;

import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
@AllArgsConstructor
public class ProductValidator implements Validator<String> {
	
	private @Getter ProductService productService;
	
	@Override
	public boolean validate(String productId) {
		return getProductService().getProductIds().contains(productId);
	}

}
