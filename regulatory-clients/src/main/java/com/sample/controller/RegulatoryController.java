package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.model.ReportContent;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/regulatory/client")
public class RegulatoryController {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${regulatory.client.queue.first}")
	private String firstClientQueue;
    
    @Value("${regulatory.client.queue.second}")
	private String secondClientQueue;
   
    
    @PostMapping(path= "/first", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> receiveProblemTransactionForFirst(@RequestBody ReportContent reportContent) {
    	log.info("Processing request in First endpoint. Message has TraderId = {}", reportContent.getTraderDetails().getTraderId());
    	jmsTemplate.convertAndSend(firstClientQueue, reportContent);
    	return ResponseEntity.ok().body("Success");
    }
    
    @PostMapping(path= "/second", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> receiveProblemTransactionForSecond(@RequestBody ReportContent reportContent) {
    	log.info("Processing request in Second endpoint. Message has TraderId = {}", reportContent.getTraderDetails().getTraderId());
    	jmsTemplate.convertAndSend(secondClientQueue, reportContent);
    	return ResponseEntity.ok().body("Success");    		
    }
    
}
