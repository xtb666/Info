package com.togest.config;

public enum DelayStatus {
	PendingDelayAudit("1"), DelayPass("2"), DelayNoPass("3");
	private final String status;

	DelayStatus(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
