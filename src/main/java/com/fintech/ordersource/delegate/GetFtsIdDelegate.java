package com.fintech.ordersource.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.ordersource.model.Message;
import com.fintech.ordersource.model.PurchaseOrder;
import com.fintech.ordersource.util.OrderSourceConstants;

@Component("getFtsIdDelegate")
public class GetFtsIdDelegate   implements JavaDelegate{

	private final Logger logger = LoggerFactory.getLogger(GetFtsIdDelegate.class);
	
	@Autowired
    private ObjectMapper mapper;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Starting GetFtsIdDelegate");
    	Message<PurchaseOrder> message = mapper.readValue((String) execution.getVariable(OrderSourceConstants.PURCHASE_ORDER),new TypeReference<Message<PurchaseOrder>>() {});
    	if(message.getFilename().isBlank() || message.getFilename().isEmpty())throw new BpmnError("MI_300", "FTS ID is missing");
    	var whereIsFtsId = message.getFilename().toUpperCase().indexOf("FTSID");
    	if(whereIsFtsId < 1 || message.getFilename().substring(0, whereIsFtsId).isEmpty()) {
    		execution.setVariable(OrderSourceConstants.FAILED_ORDER,  mapper.writeValueAsString(message));
    		throw new BpmnError("MI_300", "FTS ID is missing");
    	}
        message.getData().setFtsId(Integer.valueOf(message.getFilename().substring(0, whereIsFtsId)));
        execution.setVariable(OrderSourceConstants.PURCHASE_ORDER, mapper.writeValueAsString(message));
	}

}
