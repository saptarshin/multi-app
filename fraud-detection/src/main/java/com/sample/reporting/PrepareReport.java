package com.sample.reporting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sample.poller.InputContent;
import com.sample.reporting.model.ContentToSave;
import com.sample.reporting.model.DetectionDetails;
import com.sample.reporting.model.ReportContent;
import com.sample.reporting.model.TraderDetails;

@Component
public class PrepareReport {

	private final ContentToSaveRepository repository;
	private final SendToRegulator sendToRegulator;
	private final DateFormat sdf;
	
	@Value("${regulatory.authority.first}")
	private String firstAuthorityUrl;
	
	@Value("${regulatory.authority.second}")
	private String secondAuthorityUrl;

	@Autowired
	public PrepareReport(ContentToSaveRepository repository, SendToRegulator sendToRegulator) {
		this.repository = repository;
		this.sendToRegulator = sendToRegulator;
		this.sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
	}

	public void processFraudulentTransactions(Date detectionDate, InputContent transaction) {
		// Save the suspect trader details to database
		ContentToSave contentToSave = new ContentToSave(transaction.getTraderId(), transaction.getFirstName(), 
				transaction.getLastName(), transaction.getNationality(), transaction.getCountrOfResidence(), 
				transaction.getDateOfBirth());
		repository.save(contentToSave);
		
		DetectionDetails detectionDetais = new DetectionDetails(transaction.getStockId(), sdf.format(detectionDate));
		TraderDetails traderDetails = new TraderDetails(transaction.getTraderId(), transaction.getFirstName(), 
				transaction.getLastName(), transaction.getNationality(), transaction.getCountrOfResidence(), 
				transaction.getDateOfBirth());
		ReportContent reportContent = new ReportContent(traderDetails, detectionDetais);
		
		// Send the transaction informaton to 2 regulatory service
		sendToRegulator.sendRecordToRegulator(firstAuthorityUrl, reportContent);
		sendToRegulator.sendRecordToRegulator(secondAuthorityUrl, reportContent);
		
	}

}
