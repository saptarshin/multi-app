package com.sample.process;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sample.poller.InputContent;
import com.sample.reporting.ContentToSaveRepository;
import com.sample.reporting.PrepareReport;


@ExtendWith(MockitoExtension.class)
public class ProcessTransactionsTest {
	
	@Mock
	TransactionsCache transactionsCache;
	
	@Mock
	PrepareReport prepareReport;
	
	@Mock
	ContentToSaveRepository contentToSaveRepository;
	
	@InjectMocks
	ProcessTransactions processTransactions;
	
	private List<InputContent> contentList;
	
	@BeforeEach
	public void setUp() {
		InputContent inputContent = new InputContent("ABC;XYZ;IJ;JI;01-01-2010;12345;100;IJJI;ABCD;BUY");
		contentList = List.of(inputContent);
	}
	
    @Test
    public void testProcessAllTransactions_True()
    {
    	when(transactionsCache.checkRepitions(any(), any(InputContent.class))).thenReturn(true);
    	processTransactions.processAllTransactions(contentList);
    	verify(prepareReport,times(1)).processFraudulentTransactions(any(Date.class), any(InputContent.class));
    }
    
    @Test
    public void testProcessAllTransactions_False()
    {
    	when(transactionsCache.checkRepitions(any(), any(InputContent.class))).thenReturn(false);
    	processTransactions.processAllTransactions(contentList);
    	verify(prepareReport,times(0)).processFraudulentTransactions(any(Date.class), any(InputContent.class));
    }

}
