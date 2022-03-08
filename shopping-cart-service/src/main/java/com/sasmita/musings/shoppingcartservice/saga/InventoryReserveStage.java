package com.sasmita.musings.shoppingcartservice.saga;

import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.INVENTORY_UNAVAILBALE_EXCEPTION_MSG;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.entity.Item;
import com.sasmita.musings.shoppingcartservice.exception.InventoryNotAvailableException;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartRequest;
import com.sasmita.musings.shoppingcartservice.model.SubmitOrderContext;
import com.sasmita.musings.shoppingcartservice.proxy.InventoryProxy;

import io.vavr.Tuple2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Order(3)
@Component
@AllArgsConstructor
@Slf4j
public class InventoryReserveStage implements SagaStage<SubmitOrderContext>{
	
	private @Getter InventoryProxy inventoryProxy;

	@Override
	public void onSuccess(SubmitOrderContext context) throws Exception {
		ShoppingCartRequest orderRequest = context.getRequest();
		List<String>invNotAvailProducts = orderRequest.getItems().stream()
						.map(item -> reserveInventory(orderRequest.getCartId(), item))
						.filter(result -> !result._1)
						.map(result -> result._2)
						.collect(Collectors.toList());
		if(!invNotAvailProducts.isEmpty()) {
			List<Item> reservedItems = orderRequest.getItems().stream()
						   .filter(item -> !invNotAvailProducts.contains(item.getProductId()))
						   .collect(Collectors.toList());
			log.info("Reserved Items if there is some Failure----"+reservedItems);
			context.setReservedItems(reservedItems);
			throw new InventoryNotAvailableException(INVENTORY_UNAVAILBALE_EXCEPTION_MSG + invNotAvailProducts);
		}
		context.setReservedItems(orderRequest.getItems());
		log.info("Reserved Items if everything is Success----"+orderRequest.getItems());
		
		// What happens when few items are reserved and few are un-reserved? This has to be covered
		// Keep list of successfully reserved items, incase of failure.. revert the reserved items
		// intimate the customer about items where inventory reservation was unsuccessful.
		// introduce a context object for saga execution.
		
	}

	@Override
	public void onFailure(SubmitOrderContext context) throws Exception {
		log.info("Inside InventoryReserveStage onFailure method");
		ShoppingCartRequest orderRequest = context.getRequest();
		context.getReservedItems().forEach(item -> unReserveInventory(orderRequest.getCartId(), item));
		
	}

	private Boolean unReserveInventory(UUID orderId,Item item) {
		return getInventoryProxy().unReserveInventory(orderId, item.getProductId(), item.getQuantity());
	}
	
	private Tuple2<Boolean, String> reserveInventory(UUID orderId,Item item){
		if(getInventoryProxy().reserveInventory(orderId, item.getProductId(), item.getQuantity())) {
			return new Tuple2<Boolean, String>(Boolean.TRUE, item.getProductId());
		}else {
			return new Tuple2<Boolean, String>(Boolean.FALSE, item.getProductId());
		}
		
	}
	

}
