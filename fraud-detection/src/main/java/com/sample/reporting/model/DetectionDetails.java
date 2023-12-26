package com.sample.reporting.model;

public class DetectionDetails {
	
	private String stockId;
	private String detectionTime;
	
	public DetectionDetails(String stockId, String detectionTime) {
		this.stockId = stockId;
		this.detectionTime = detectionTime;
	}
	
	public String getStockId() {
		return stockId;
	}
	public String getDetectionTime() {
		return detectionTime;
	}

}
