package com.togest.config;

public enum DefectSystem {

	defectC1("C1"), defectC2("C2"), defectC3("C3"), defectC4("C4"), defectC5("C5"), defectC6("C6");
	private final String status;

	DefectSystem(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
