package com.sample.reporting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sample.reporting.model.ReportContent;

@Component
public class SendToRegulator {

	private final RegulatorClient regulatorClient;

	@Autowired
	public SendToRegulator(RegulatorClient regulatorClient) {
		this.regulatorClient = regulatorClient;
	}

	@Async("taskExecutor")
	public void sendRecordToRegulator(String serviceUrl, ReportContent reportContent) {
		regulatorClient.sendRecord(serviceUrl, reportContent);
	}

}
