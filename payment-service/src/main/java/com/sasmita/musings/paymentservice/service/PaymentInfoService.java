package com.sasmita.musings.paymentservice.service;

import org.springframework.stereotype.Service;

import com.sasmita.musings.paymentservice.entity.PaymentInfo;
import com.sasmita.musings.paymentservice.repository.PaymentRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Getter @Setter
@Slf4j
public class PaymentInfoService {
	
	private PaymentRepository repository;
	
	private CardService cardService;
	
	public Boolean processPayment(PaymentInfo payment) {
		log.info("Card number ----"+payment.getCardNumber());
		if(validateCard(payment.getCardNumber())) {
			log.info("validateCard true");
			getRepository().save(payment);
			return true;
		}
		return false;
	}
	
	private Boolean validateCard(String cardNumber) {
		return getCardService().getCreditCardNumbers().contains(cardNumber);
	}

}
