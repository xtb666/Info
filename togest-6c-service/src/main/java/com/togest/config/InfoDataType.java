package com.togest.config;

public enum InfoDataType {

	Pohto("1"), Video("2");
	private final String status;

	InfoDataType(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
