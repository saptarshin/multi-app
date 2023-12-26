package com.sample.reporting;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sample.reporting.model.DetectionDetails;
import com.sample.reporting.model.ReportContent;
import com.sample.reporting.model.TraderDetails;


@ExtendWith(MockitoExtension.class)
public class SendToRegulatorTest {
	
	@Mock
	RegulatorClient regulatorClient;
	
	@InjectMocks
	SendToRegulator sendToRegulator;
	
	
    @Test
    public void testProcessFraudulentTransactions()
    {
    	String serviceUrl = "serviceUrl";
    	ReportContent reportContent = new ReportContent(any(TraderDetails.class), any(DetectionDetails.class));
    	sendToRegulator.sendRecordToRegulator(serviceUrl, reportContent);
    	verify(regulatorClient,times(1)).sendRecord(serviceUrl, reportContent);
    }

}
