package com.sasmita.musings.shoppingcartservice.saga;

public interface SagaStage<C> extends Toggle {
	
	public void onSuccess(C context) throws Exception;
	
	public void onFailure(C context) throws Exception;

}
