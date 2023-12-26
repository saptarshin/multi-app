package com.sample.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;

import com.sample.model.DetectionDetails;
import com.sample.model.ReportContent;
import com.sample.model.TraderDetails;


@ExtendWith(MockitoExtension.class)
public class RegulatoryControllerTest {
	
	@Mock
	JmsTemplate jmsTemplate;
	
	@InjectMocks
	RegulatoryController regulatoryController;
	
	private ReportContent reportContent;
	
	@BeforeEach
	public void setUp() {
		TraderDetails traderDetails = new TraderDetails("123", "ABC", "XYZ", "IJ", "JI", null);
        DetectionDetails detectionDetails = new DetectionDetails("MNOP", "890");
        reportContent = new ReportContent(traderDetails, detectionDetails);
	}
	
    @Test
    public void testReceiveProblemTransactionForFirst()
    {
        ResponseEntity<Object> responseEntity = regulatoryController.receiveProblemTransactionForFirst(reportContent);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Success");
    }
    
    @Test
    public void testReceiveProblemTransactionForSecond()
    {
        ResponseEntity<Object> responseEntity = regulatoryController.receiveProblemTransactionForSecond(reportContent);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Success");
    }


}
