package com.togest.config;

public enum InfoStatus {

	UploadCompleted("1"), PretreatmentCompleted("2"), AnalysisCompleted("3");
	
	private final String status;
	InfoStatus(String status){
		this.status=status;
	}
	public String getStatus() {
		return status;
	}
}
