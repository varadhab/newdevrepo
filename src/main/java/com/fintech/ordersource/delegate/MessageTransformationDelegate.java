package com.fintech.ordersource.delegate;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
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
		poData.setPurchaseOrderNbr(order.getPoNbr());
		poData.setOrderStatus("ORDER TRANSFORMED");
		var orderItems = new ArrayList<OrderItem>();
		order.getLineItems().forEach(item -> {
			var orderItem = new OrderItem();
			orderItem.setOrderQuantity(item.getOrderQuantity());
			orderItem.setProductName(item.getProductName());
			orderItem.setVendorProductNbr(item.getVendorProductNbr());
			orderItem.setUnitOfMeasure(item.getUnitOfMeasure());
			orderItem.setUnitPrice(item.getUnitPrice());
			orderItem.setInvoiceTotal(Double.valueOf(item.getOrderQuantity()) * Double.valueOf(item.getUnitPrice()));
			if(orderItem.getInvoiceTotal() == 0.0) {
				try {
					execution.setVariable(OrderSourceConstants.FAILED_ORDER,  mapper.writeValueAsString(message));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				throw new BpmnError("MI_200", "Invoice total is 0");
			}
			orderItems.add(orderItem);
		});
		poData.setOrderItems(orderItems);
		logger.info("Order transformation is complete -> {}", poData);
		logger.info("Exiting MessageTransformationAdapter");
		var outputMessage = mapper.writeValueAsString(poData);
		producerService.publishMessage(outputMessage,OrderSourceConstants.PO_DATA_TOPIC);
        
    }
}
