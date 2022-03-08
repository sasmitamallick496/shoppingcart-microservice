package com.sasmita.musings.shoppingcartservice.validator;

import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.model.InventoryInfo;
import com.sasmita.musings.shoppingcartservice.proxy.InventoryProxy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
@AllArgsConstructor
public class InventoryValidator implements Validator<String>{
	
	private @Getter InventoryProxy inventoryProxy;
	
	@Override
	public boolean validate(String productId) throws Exception {
		InventoryInfo inventoryInfo = getInventoryProxy().retrieveInventoryById(productId);
		return inventoryInfo.getAvailable();
	}

}
