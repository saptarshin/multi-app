package com.sample.process;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sample.poller.InputContent;
import com.sample.reporting.ContentToSaveRepository;
import com.sample.reporting.PrepareReport;
import com.sample.reporting.model.ContentToSave;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessTransactions {
	
	private final TransactionsCache transactionsCache;
	private final PrepareReport prepareReport;
	private final ContentToSaveRepository repository;
	
	@Autowired
	public ProcessTransactions(TransactionsCache transactionsCache, PrepareReport prepareReport, ContentToSaveRepository repository) {
		this.transactionsCache = transactionsCache;
		this.prepareReport = prepareReport;
		this.repository = repository;
	}
	
	public void processAllTransactions(List<InputContent> allTransactions) {
		Long currentTime = System.currentTimeMillis();
		Date detectionDate = getDetectionDate(currentTime);
		for (InputContent transaction : allTransactions) {
			boolean isFradulent = transactionsCache.checkRepitions(currentTime, transaction);
			if (isFradulent) {
				prepareReport.processFraudulentTransactions(detectionDate, transaction); 
				log.warn("Trader with ID = {} might be attempting suspicious transactions", transaction.getTraderId());
			}
		}
	}
	
	private Date getDetectionDate(Long currentTimeInMillis) {
		return new Date(currentTimeInMillis);
	}

}
