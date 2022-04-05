package com.fintech.ordersource.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fintech.ordersource.util.OrderSourceConstants;


@Service
public class KafKaProducerService 
{
	private static final Logger logger = 
			LoggerFactory.getLogger(KafKaProducerService.class);
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void publishMessage(String message,String topic) {
		logger.info(String.format("Publishing Item order created for topic :"+OrderSourceConstants.ORDER_TOPIC+"-> %s", message));
		kafkaTemplate.send(topic, message);
	}
}
