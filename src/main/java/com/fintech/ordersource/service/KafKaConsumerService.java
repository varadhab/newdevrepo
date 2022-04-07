package com.fintech.ordersource.service;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
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
	
	
	@KafkaListener(topics = OrderSourceConstants.ORDER_TOPIC, groupId = "test_group_one")
	public void consumeFromOrderTopic(String messageInput) throws JsonMappingException, JsonProcessingException {
		
		logger.info("consumeFromOrderTopic:Message received -> "+OrderSourceConstants.ORDER_TOPIC+":" + messageInput);
		Message<PurchaseOrder> message = mapper.readValue(messageInput,new TypeReference<Message<PurchaseOrder>>() {});
        List<EventSubscription> eventSubscriptions = runtimeService.createEventSubscriptionQuery().list();

        // then
        for(int i=0;i<eventSubscriptions.size();i++) {
	        //EventSubscriptionEntity conditionalEventSubscription = (EventSubscriptionEntity) eventSubscriptions.get(i);
	        //System.out.println("EVENt Subscription consumetem():Event name:"+conditionalEventSubscription.getEventName()+":EVENT type:"+conditionalEventSubscription.getEventType());
        }

        String messageString = new Gson().toJson(message, Message.class);
        runtimeService.createMessageCorrelation(message.getType())
        .setVariable(OrderSourceConstants.VENDOR_NBR, message.getData().getVendorNbr())
        .setVariable(OrderSourceConstants.DELIVERY_DATE, message.getData().getDeliveryDate())
        .setVariable(OrderSourceConstants.PO_NBR, message.getData().getPoNbr())
        .setVariable(OrderSourceConstants.CORRELATION_ID, message.getCorrelationId())
        .setVariable(OrderSourceConstants.PURCHASE_ORDER, messageString)
        .correlate();
   	}
	
	@KafkaListener(topics = OrderSourceConstants.VALID_ORDER_TOPIC, groupId = "test_group_two")
	public void consumeFromValidOrderTopic(String messageInput) throws JsonMappingException, JsonProcessingException {
		
		
		logger.info("consumeFromValidOrderTopic received -> "+OrderSourceConstants.VALID_ORDER_TOPIC+":" + messageInput);
		Message<PurchaseOrder> message = mapper.readValue(messageInput,new TypeReference<Message<PurchaseOrder>>() {});
         
		logger.info("================PO Nbr:"+OrderSourceConstants.ORDER_ID+":"+message.getData().getPoNbr());
        List<EventSubscription> eventSubscriptions = runtimeService.createEventSubscriptionQuery().list();

        // then
        for(int i=0;i<eventSubscriptions.size();i++) {
	        //EventSubscriptionEntity conditionalEventSubscription = (EventSubscriptionEntity) eventSubscriptions.get(i);
	        //System.out.println("EVENt Subscription consumetem2():Event name:"+conditionalEventSubscription.getEventName()+":EVENT type:"+conditionalEventSubscription.getEventType());
        }
        String messageString = new Gson().toJson(message, Message.class);
        runtimeService.createMessageCorrelation(message.getType())
        .processInstanceVariableEquals(OrderSourceConstants.CORRELATION_ID, message.getCorrelationId())
        .setVariable(OrderSourceConstants.VENDOR_NBR, message.getData().getVendorNbr())
        .setVariable(OrderSourceConstants.DELIVERY_DATE, message.getData().getDeliveryDate())
        .setVariable(OrderSourceConstants.PO_NBR, message.getData().getPoNbr())
        .setVariable("validatedOrder", messageString)
        .correlateWithResult();
    }
}
