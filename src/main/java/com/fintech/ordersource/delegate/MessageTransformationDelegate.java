package com.fintech.ordersource.delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.ordersource.model.Message;
import com.fintech.ordersource.model.OrderItem;
import com.fintech.ordersource.model.POData;
import com.fintech.ordersource.model.PurchaseOrder;
import com.fintech.ordersource.service.KafKaProducerService;
import com.fintech.ordersource.util.OrderSourceConstants;

@Component("messageTransformationDelegate")
public class MessageTransformationDelegate   implements JavaDelegate{
	
    private final Logger logger = LoggerFactory.getLogger(MessageTransformationDelegate.class);
	private final KafKaProducerService producerService;
	
	@Autowired
    private ObjectMapper mapper;

	@Autowired
	public MessageTransformationDelegate(KafKaProducerService producerService) {
		this.producerService = producerService;
	}

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        
        logger.info("Starting MessageTransformationAdapter");
		Message<PurchaseOrder> message = mapper.readValue((String)execution.getVariable("validatedOrder"),new TypeReference<Message<PurchaseOrder>>() {});
        PurchaseOrder order = message.getData();
		var poData = new POData();
		poData.setOrderId(order.getOrderId());
		poData.setOrderStatus("ORDER TRANSFORMED");
		poData.setOrderItems(
				List.of(new OrderItem(order.getProductName(), order.getDescription(), order.getQuantity())));
		logger.info("Order transformation is complete -> %s", poData);
		logger.info("Exiting MessageTransformationAdapter");
		var outputMessage = mapper.writeValueAsString(poData);
		producerService.publishMessage(outputMessage,OrderSourceConstants.PO_DATA_TOPIC);
        
    }
}
