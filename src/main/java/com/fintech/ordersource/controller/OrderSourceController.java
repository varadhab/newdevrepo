package com.fintech.ordersource.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.ordersource.model.Message;
import com.fintech.ordersource.model.PurchaseOrder;
import com.fintech.ordersource.service.KafKaProducerService;
import com.fintech.ordersource.util.OrderSourceConstants;

@RestController
@RequestMapping(value = "/ordersource")
public class OrderSourceController {

	private static final Logger logger = LoggerFactory.getLogger(OrderSourceController.class);
	private final KafKaProducerService producerService;
	
	@Autowired
    private ObjectMapper mapper;

	@Autowired
	public OrderSourceController(KafKaProducerService producerService) {
		this.producerService = producerService;
	}

	@PostMapping(value = "/postOrder")
    public void postJsonMessage(@RequestBody Message<PurchaseOrder> message) throws JsonProcessingException{
		logger.info("Starting postJsonMessage() -> {} "+ message);
		var outputMessage = mapper.writeValueAsString(message);
		producerService.publishMessage(outputMessage,OrderSourceConstants.ORDER_TOPIC);
    	
    }
}
