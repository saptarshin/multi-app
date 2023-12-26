package com.sample.model;

public class ReportContent {
	
	private TraderDetails traderDetails;
	private DetectionDetails detectionDetails;
	
	public ReportContent(TraderDetails traderDetails, DetectionDetails detectionDetails) {
		this.traderDetails = traderDetails;
		this.detectionDetails = detectionDetails;
	}
	
	public TraderDetails getTraderDetails() {
		return traderDetails;
	}
	
	public DetectionDetails getDetectionDetails() {
		return detectionDetails;
	}
	
	
	

}
