package com.sample.reporting;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sample.poller.InputContent;
import com.sample.reporting.model.ReportContent;


@ExtendWith(MockitoExtension.class)
public class PrepareReportTest {
	
	@Mock
	SendToRegulator sendToRegulator;
	
	@Mock
	ContentToSaveRepository contentToSaveRepository;
	
	@InjectMocks
	PrepareReport prepareReport;
	
	
    @Test
    public void testProcessFraudulentTransactions()
    {
    	InputContent inputContent = new InputContent("ABC;XYZ;IJ;JI;01-01-2010;12345;100;IJJI;ABCD;BUY");
    	prepareReport.processFraudulentTransactions(new Date(), inputContent);
    	verify(contentToSaveRepository,times(1)).save(any());
    	verify(sendToRegulator,times(2)).sendRecordToRegulator(any(), any(ReportContent.class));
    }

}
