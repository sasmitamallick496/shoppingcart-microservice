package com.sasmita.musings.shoppingcartservice.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sasmita.musings.shoppingcartservice.entity.Item;
import com.sasmita.musings.shoppingcartservice.model.PriceInfo;
import com.sasmita.musings.shoppingcartservice.proxy.PriceProxy;
import com.sasmita.musings.shoppingcartservice.repository.ItemRepository;
import com.sasmita.musings.shoppingcartservice.validator.InventoryValidator;
import com.sasmita.musings.shoppingcartservice.validator.ProductValidator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@AllArgsConstructor
public class ItemService {
	
	private @Getter ItemRepository repository;
	
	private @Getter ProductValidator productValidator;
	
	private @Getter InventoryValidator inventoryValidator;
	
	private @Getter PriceProxy priceProxy;
	
	public void deleteItem(String productId) {
		//getRepository().deleteByItemId(itemId);
	}
	
	public Item updateItem(Item item) throws Exception {
		Item updatedItem = null;
		if(getProductValidator().validate(item.getProductId()) &&
				getInventoryValidator().validate(item.getProductId())) {
			updatedItem =  getRepository().save(retrieveItemPrice(item));
		}
		return updatedItem;
	}
	
	private Item retrieveItemPrice(Item item) throws Exception {
		PriceInfo priceInfo = getPriceProxy().retrievePriceById(item.getProductId());
		item.setUnitPrice(priceInfo.getListPrice());
		item.setAmount(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
		return item;
	}
	
	

}
