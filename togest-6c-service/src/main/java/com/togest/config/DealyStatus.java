package com.togest.config;

public enum DealyStatus {
	
	DealyRegister("1"), DealyPass("2"), DealyFail("3");

	private final String status;
	
	DealyStatus(String status){
		this.status=status;	
	}

	public String getStatus() {
		return status;
	}
}
