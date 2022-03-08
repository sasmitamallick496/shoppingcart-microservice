package com.sasmita.musings.paymentservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sasmita.musings.paymentservice.Model.CreditCard;

@Component
public class CardService {
	
	private static List<CreditCard> cardList;
	static {
		CreditCard card1 = new CreditCard("1111111111111111", LocalDate.of(2022, 12, 25), "111", new BigDecimal(100000));
		CreditCard card2 = new CreditCard("2222222222222222", LocalDate.of(2022, 11, 25), "222", new BigDecimal(200000));
		CreditCard card3 = new CreditCard("3333333333333333", LocalDate.of(2022, 10, 25), "333", new BigDecimal(300000));
		CreditCard card4 = new CreditCard("4444444444444444", LocalDate.of(2022, 9, 25), "444", new BigDecimal(400000));
		CreditCard card5 = new CreditCard("5555555555555555", LocalDate.of(2022, 8, 25), "555", new BigDecimal(500000));
		
		cardList = new ArrayList<>(Arrays.asList(card1,card2,card3,card4,card5));
	}
	
	public List<CreditCard> getCardList(){
		return cardList;
	}
	
	public List<String> getCreditCardNumbers(){
		return cardList.stream().map(creditCard -> creditCard.getCardNumber()).collect(Collectors.toList());
	}

}
