package com.sasmita.musings.shoppingcartservice.saga;

import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.PAYMENT_FAILED__EXCEPTION_MSG;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.exception.PaymentFailedException;
import com.sasmita.musings.shoppingcartservice.model.PaymentInfo;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartRequest;
import com.sasmita.musings.shoppingcartservice.model.SubmitOrderContext;
import com.sasmita.musings.shoppingcartservice.proxy.PaymentProxy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Order(4)
@Component
@AllArgsConstructor
@Slf4j
public class PaymentProcessStage  implements SagaStage<SubmitOrderContext>{
	
	private @Getter PaymentProxy paymentProxy;
	

	@Override
	public void onSuccess(SubmitOrderContext context) throws Exception {
		ShoppingCartRequest orderRequest = context.getRequest();
		PaymentInfo paymentInfo = orderRequest.getPaymentInfo();
		paymentInfo.setOrderId(orderRequest.getCartId());
		paymentInfo.setAmount(orderRequest.getOrderTotal());
		if(!getPaymentProxy().processPayment(paymentInfo)) {
			throw new PaymentFailedException(PAYMENT_FAILED__EXCEPTION_MSG+orderRequest.getCartId());
		}
		
	}

	@Override
	public void onFailure(SubmitOrderContext context) throws Exception {
		log.info("Inside PaymentProcessStage OnFalilure Method");
	}

}
