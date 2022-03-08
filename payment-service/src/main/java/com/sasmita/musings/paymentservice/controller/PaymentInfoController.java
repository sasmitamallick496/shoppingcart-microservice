package com.sasmita.musings.paymentservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sasmita.musings.paymentservice.entity.PaymentInfo;
import com.sasmita.musings.paymentservice.service.PaymentInfoService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@AllArgsConstructor
@Getter @Setter
public class PaymentInfoController {
	
	private PaymentInfoService paymentService;
	
	@PostMapping("/payment-service/payment")
	public Boolean processPayment(@RequestBody PaymentInfo payment) {
		return getPaymentService().processPayment(payment);
	}
	
	

}
