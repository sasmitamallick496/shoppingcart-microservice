package com.sasmita.musings.shoppingcartservice.saga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.model.SubmitOrderContext;

@Component
public class SubmitCartSagaExecutor implements SagaExecutor<SubmitOrderContext> {
	
	@Autowired
	private List<SagaStage<SubmitOrderContext>> sagaStages;

	@Override
	public List<SagaStage<SubmitOrderContext>> getSagaStages() {
		return sagaStages;
	}

}
