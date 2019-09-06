package com.togest.config;

public enum BussinessType {
	
	Defect("1"), Plan("2"),Notice("3"),DefectTask("defect"),DefectCheck("4");
	private final String status;

	BussinessType(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
