package com.sample.reporting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sample.reporting.model.ReportContent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegulatorClient {
	
	/* This class sends a Json request to Regulatory services 
	 * Structure of Json message is as below - 
	 * {
		  "traderDetails": {
		    "traderId": "",
		    "firstName": "",
		    "lastName": "",
		    "nationality": "",
		    "countrOfResidence": "",
		    "dateOfBirth": ""
		  },
		  "detectionDetails": {
		    "stockId": "",
		    "detectionTime": ""
		  }
		}
	 * */

	private final RestTemplate restTemplate;

	@Autowired
	public RegulatorClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Retryable(value = Exception.class, maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
	public void sendRecord(String serviceUrl, ReportContent reportContent) {
		restTemplate.postForEntity(serviceUrl, reportContent, String.class);
		log.info("Sent details about suspect trade with TraderID={}, StockID={} to service: {}",
				reportContent.getTraderDetails().getTraderId(), reportContent.getDetectionDetails().getStockId(),
				serviceUrl);
	}

	@Recover
	private void recoverForFailure(Exception ex, String serviceUrl, ReportContent reportContent) {
		log.error("Failed to send record with TraderID={}, StockID={} to service: {} due to an exception: {}",
				reportContent.getTraderDetails().getTraderId(), reportContent.getDetectionDetails().getStockId(),
				serviceUrl, ex.getMessage());
	}

}
