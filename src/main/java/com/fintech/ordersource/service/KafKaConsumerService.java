package com.fintech.ordersource.service;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.ordersource.model.Message;
import com.fintech.ordersource.model.PurchaseOrder;
import com.fintech.ordersource.util.OrderSourceConstants;
import com.google.gson.Gson;

@Service
public class KafKaConsumerService 
{

	private final Logger logger = LoggerFactory.getLogger(KafKaConsumerService.class);
	@Autowired
	ObjectMapper mapper;
	
	private final RuntimeService runtimeService;
	@Autowired
	public KafKaConsumerService(RuntimeService runtimeService) {
	        this.runtimeService = runtimeService;
	}
	
	
	@KafkaListener(topics = OrderSourceConstants.ORDER_TOPIC, groupId = OrderSourceConstants.GROUP_ID)
	public void consumeFromOrderTopic(String messageInput) throws JsonMappingException, JsonProcessingException {
		
		logger.info("consumeFromOrderTopic:Message received -> %s"+OrderSourceConstants.ORDER_TOPIC+":" + messageInput);
		Message<PurchaseOrder> message = mapper.readValue(messageInput,new TypeReference<Message<PurchaseOrder>>() {});
        List<EventSubscription> eventSubscriptions = runtimeService.createEventSubscriptionQuery().list();

        // then
        for(int i=0;i<eventSubscriptions.size();i++) {
	        EventSubscriptionEntity conditionalEventSubscription = (EventSubscriptionEntity) eventSubscriptions.get(i);
	        System.out.println("EVENt Subscription consumetem():Event name:"+conditionalEventSubscription.getEventName()+":EVENT type:"+conditionalEventSubscription.getEventType());
        }

        String messageString = new Gson().toJson(message, Message.class);
        runtimeService.createMessageCorrelation(message.getType())
        .setVariable(OrderSourceConstants.DESCRIPTION, message.getData().getDescription())
        .setVariable(OrderSourceConstants.PRODUCT_NAME, message.getData().getProductName())
        .setVariable(OrderSourceConstants.ORDER_ID, message.getData().getOrderId())
        .setVariable(OrderSourceConstants.CORRELATION_ID, message.getCorrelationId())
        .setVariable(OrderSourceConstants.PURCHASE_ORDER, messageString)
        .correlate();
   	}
	
	@KafkaListener(topics = OrderSourceConstants.VALID_ORDER_TOPIC, groupId = OrderSourceConstants.GROUP_ID)
	public void consumeFromValidOrderTopic(String messageInput) throws JsonMappingException, JsonProcessingException {
		
		
		logger.info("consumeFromValidOrderTopic received -> %s"+OrderSourceConstants.VALID_ORDER_TOPIC+":" + messageInput);
		Message<PurchaseOrder> message = mapper.readValue(messageInput,new TypeReference<Message<PurchaseOrder>>() {});
         
		logger.info("================Item:"+OrderSourceConstants.ORDER_ID+":"+message.getData().getOrderId());
        List<EventSubscription> eventSubscriptions = runtimeService.createEventSubscriptionQuery().list();

        // then
        for(int i=0;i<eventSubscriptions.size();i++) {
	        EventSubscriptionEntity conditionalEventSubscription = (EventSubscriptionEntity) eventSubscriptions.get(i);
	        System.out.println("EVENt Subscription consumetem2():Event name:"+conditionalEventSubscription.getEventName()+":EVENT type:"+conditionalEventSubscription.getEventType());
        }
        String messageString = new Gson().toJson(message, Message.class);
        runtimeService.createMessageCorrelation(message.getType())
        .processInstanceVariableEquals(OrderSourceConstants.CORRELATION_ID, message.getCorrelationId())
        .setVariable(OrderSourceConstants.DESCRIPTION, message.getData().getDescription())
        .setVariable(OrderSourceConstants.PRODUCT_NAME, message.getData().getProductName())
        .setVariable(OrderSourceConstants.ORDER_ID, message.getData().getOrderId())
        .setVariable("validatedOrder", messageString)
        .correlateWithResult();
    }
}
