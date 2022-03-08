package com.sasmita.musings.shoppingcartservice.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasmita.musings.shoppingcartservice.model.CartDetailsDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Setter
@Slf4j
public class EmailProducer {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	ObjectMapper mapper;

	@Value("${spring.kafka.template.default-topic}")
	private String topic;

	public void sendEmail(CartDetailsDTO order) throws JsonProcessingException {

		String key = order.getCartId().toString();
		String value = mapper.writeValueAsString(order);
		ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate
				.send(buildProducerRecord(topic, key, value));
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				handleSuccess(key, value, result);

			}

			@Override
			public void onFailure(Throwable ex) {
				haldleFailure(key, value, ex);

			}

		});

	}

	private void handleSuccess(String key, String value, SendResult<String, String> result) {
		log.info("Message Sent Successfully for the key : {} and the value is : {} , partition is {} ", key, value,
				result.getRecordMetadata().partition());

	}

	private void haldleFailure(String key, String value, Throwable ex) {
		log.info("Error in sending the message and the exception is {} ", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in onFailure: {}", throwable.getMessage());
		}
	}

	private ProducerRecord<String, String> buildProducerRecord(String topic, String key, String value) {
		return new ProducerRecord<String, String>(topic, key, value);
	}

}
