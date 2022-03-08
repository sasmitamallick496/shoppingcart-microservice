package com.sasmita.musings.emailconsumerservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailConsumer {
	
	@Autowired
	ObjectMapper mapper;
	
	@KafkaListener(topics = {"Email-Topic"})
	public void onMessage(ConsumerRecord<String, String> consumerRecord) {
		log.info("Consumer record : {}",consumerRecord);
		log.info("Order Id = {}",consumerRecord.key());
		
		
		
	}

}
