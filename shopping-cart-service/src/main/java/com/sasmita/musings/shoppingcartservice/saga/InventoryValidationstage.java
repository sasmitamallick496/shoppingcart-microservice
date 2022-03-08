package com.sasmita.musings.shoppingcartservice.saga;

import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.INVENTORY_UNAVAILBALE_EXCEPTION_MSG;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.entity.Item;
import com.sasmita.musings.shoppingcartservice.exception.InventoryNotAvailableException;
import com.sasmita.musings.shoppingcartservice.model.SubmitOrderContext;
import com.sasmita.musings.shoppingcartservice.validator.InventoryValidator;

import io.vavr.Tuple2;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Order(2)
@Component	
@Slf4j
@Getter @Setter
public class InventoryValidationstage implements SagaStage<SubmitOrderContext> {
	
	@Value("${inventory.validation.saga.enabled}")
	private boolean enabled;
	
	@Autowired
	private InventoryValidator inventoryValidator;

	@Override
	public void onSuccess(SubmitOrderContext context) throws Exception {
		log.info("---Inside InventoryValidationstage OnSuccess Method---");
		List<String> invenNotAvailProducts = context.getRequest().getItems().stream()
				// (123, 124, 125)
				.map(item -> checkInventory(item))
				// (TRUE, 123), (FALSE, 124), (FALSE, 125)
				.filter(result -> !result._1)
				.map(result -> result._2)
				.collect(Collectors.toList());
		log.info("-----unavailableProductIds------"+invenNotAvailProducts);

		if (!invenNotAvailProducts.isEmpty()) {
			throw new InventoryNotAvailableException(INVENTORY_UNAVAILBALE_EXCEPTION_MSG+ invenNotAvailProducts);
		}

	}

	@Override
	public void onFailure(SubmitOrderContext context) throws Exception {
		log.info("---Inside InventoryValidationstage OnFailure Method---");

	}

	public Tuple2<Boolean, String> checkInventory(Item item) {
		try {
			if (getInventoryValidator().validate(item.getProductId())) {
				log.info("-----valid ProductId-----"+item.getProductId());
				return new Tuple2<Boolean, String>(Boolean.TRUE, item.getProductId());
			} else {
				return new Tuple2<Boolean, String>(Boolean.FALSE, item.getProductId());
			}
		} catch (Exception e) {
			return new Tuple2<Boolean, String>(Boolean.FALSE, item.getProductId());
		}
	}

	// Change this to read configuration from application.properties (GIT repo)
	
}
