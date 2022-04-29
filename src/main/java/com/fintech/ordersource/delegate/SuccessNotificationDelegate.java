package com.fintech.ordersource.delegate;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Named
public class SuccessNotificationDelegate implements JavaDelegate{

	private final Logger logger = LoggerFactory.getLogger(SuccessNotificationDelegate.class);
	
	@Autowired
	RestTemplate restTemplate;
	
    @Value("${ordersource.camunda.properties.teams.webhook.url}")
    private  String URL;

    @Value("${ordersource.camunda.properties.teams.success.message}")
    private  String body;

    @Override

    public void execute(DelegateExecution execution) throws Exception {

    	logger.info("Executing SuccessNotificationDelegate for URL : {}, Message body: {}",URL, body);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(body, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
		logger.info("response=" + response);
		execution.setVariable("response", response.getStatusCode());
		logger.info("Exiting SuccessNotificationDelegate ");
        

    }


}
