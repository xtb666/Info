package com.togest.config;

public enum DataStatus {

	NotUpload("1"), AlreadyUploaded("2");
	
	private final String status;
	DataStatus(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
