package com.togest.config;

public enum DefectAuditStatus {

	DefectAudit("1"), DefectAuditNotPass("2"), ShopReception("3"), ShopNotReception(
			"4"), RectificationVerification("5"), RectificationVerificationNotPass(
			"6"), DELAY("7"), DelayNotPass("8"), DefectReject("9");

	private final String status;

	DefectAuditStatus(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}

}
