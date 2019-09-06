package com.togest.config;

public enum DefectStatus {

	DefectRegister("1"), DefectAudit("2"), ShopReception("3"), ReviewRectification("4"), RectificationVerification(
			"5"), Cancel("6"), DELAY("7"), CHECKHANDLE("8"), NotCancel("9");

	private final String status;

	DefectStatus(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}

}
