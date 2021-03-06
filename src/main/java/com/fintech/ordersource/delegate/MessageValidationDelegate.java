package com.fintech.ordersource.delegate;

import java.util.ArrayList;
import java.util.Objects;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.ordersource.model.Message;
import com.fintech.ordersource.model.PurchaseOrder;
import com.fintech.ordersource.service.KafKaProducerService;
import com.fintech.ordersource.util.OrderSourceConstants;

@Component("messageValidationDelegate")
public class MessageValidationDelegate  implements JavaDelegate{
	
	private final Logger logger = LoggerFactory.getLogger(MessageValidationDelegate.class);
	
	@Autowired
    private ObjectMapper mapper;
	
    private final KafKaProducerService producerService;

	@Autowired
	public MessageValidationDelegate(KafKaProducerService producerService, RestTemplateBuilder restTemplateBuilder) {
		this.producerService = producerService;
	}

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Starting MessageValidationAdapter");
    	Message<PurchaseOrder> message = mapper.readValue((String) execution.getVariable(OrderSourceConstants.PURCHASE_ORDER),new TypeReference<Message<PurchaseOrder>>() {});
        PurchaseOrder order = message.getData();
    	var errors = new ArrayList<String>();
    	if(Objects.isNull(order.getOrderId()) || order.getOrderId().isEmpty() || order.getOrderId().isEmpty()) {
    		errors.add("Order Id is missing");
    	}
    	if(Objects.isNull(order.getQuantity()) || order.getQuantity().isEmpty() || order.getQuantity().isEmpty()) {
    		errors.add("Order quantity is missing");
    	}
    	logger.info("Validation is complete");
    	String outputMessage = null;
    	if(errors.size() > 0) {
    		order.setOrderStatus("VALIDATION FAILED");
    		message.setType("ValidationFailEvent");
    		outputMessage = mapper.writeValueAsString(message);
    		execution.setVariable(OrderSourceConstants.FAILED_ORDER, outputMessage);
    		throw new BpmnError("MI_100", "Validation Failed");
    	}else {
    		order.setOrderStatus("VALIDATION SUCCESS");
    		message.setType("ValidationSuccessEvent");
    		outputMessage = mapper.writeValueAsString(message);
    		producerService.publishMessage(outputMessage,OrderSourceConstants.VALID_ORDER_TOPIC);
    	}
    }

}
