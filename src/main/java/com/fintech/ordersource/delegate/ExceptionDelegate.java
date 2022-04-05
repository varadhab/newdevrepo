package com.fintech.ordersource.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fintech.ordersource.service.KafKaProducerService;
import com.fintech.ordersource.util.OrderSourceConstants;


@Component("exceptionDelegate")
public class ExceptionDelegate implements JavaDelegate {
	
	private final Logger logger = LoggerFactory.getLogger(ExceptionDelegate.class);
	private final KafKaProducerService producerService;
	
	@Autowired
	public ExceptionDelegate(KafKaProducerService producerService) {
		this.producerService = producerService;
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		logger.info("Executing Validation Failed Orders for activity: {0}, Error code:{1}, Error message:{2}",
				execution.getCurrentActivityName(), execution.getVariable("errCode"), execution.getVariable("errMessage"));
	    producerService.publishMessage((String) execution.getVariable(OrderSourceConstants.FAILED_ORDER),OrderSourceConstants.FAIL_ORDER_TOPIC);
		
	}

}
