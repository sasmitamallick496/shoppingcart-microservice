package com.sasmita.musings.shoppingcartservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.sasmita.musings.shoppingcartservice.model.PaymentInfo;


@FeignClient(name = "payment-service")
public interface PaymentProxy {
	
	@PostMapping("/payment-service/payment")
	public Boolean processPayment(PaymentInfo payment);

}
