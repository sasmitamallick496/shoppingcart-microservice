package com.sasmita.musings.shoppingcartservice.saga;

import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.PRODUCT_NOT_FOUND_EXCEPTION_MSG;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.entity.Item;
import com.sasmita.musings.shoppingcartservice.exception.ProductNotFoundException;
import com.sasmita.musings.shoppingcartservice.model.SubmitOrderContext;
import com.sasmita.musings.shoppingcartservice.validator.ProductValidator;

import io.vavr.Tuple2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Order(1)
@Component
@AllArgsConstructor
@Slf4j
public class ProductValidationStage implements SagaStage<SubmitOrderContext>{
	
	private @Getter ProductValidator productValidator;

	@Override
	public void onSuccess(SubmitOrderContext context) throws ProductNotFoundException {
		log.info("---Inside ProductValidationStage OnSuccess Method---");
		List<String> unavailableProductIds = context.getRequest().getItems().stream()
															.map(item -> checkProductCataLog(item))
															.filter(result -> !result._1)
															.map(result -> result._2)
															.collect(Collectors.toList());
		if(!unavailableProductIds.isEmpty()) {
			throw new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION_MSG + unavailableProductIds);
		}
		
	}

	@Override
	public void onFailure(SubmitOrderContext context) throws Exception {
		log.info("---Inside ProductValidationStage OnFailure Method---");
		
	}
	
	private Tuple2<Boolean, String> checkProductCataLog(Item item){
		if(getProductValidator().validate(item.getProductId())){
			return new Tuple2<Boolean, String>(Boolean.TRUE, item.getProductId());
		}else {
			return new Tuple2<Boolean, String>(Boolean.FALSE, item.getProductId());
		}
	}

	
	
	
	
	

}
