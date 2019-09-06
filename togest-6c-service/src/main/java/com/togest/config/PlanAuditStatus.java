package com.togest.config;

public enum PlanAuditStatus {

	PlanAudit("1"), PlanAuditNotPass("2"), PlanComplete("3"), PlanCompleteNotPass("4");
	
	private final String status;
	PlanAuditStatus(String status){
		this.status=status;
	}
	public String getStatus() {
		return status;
	}
}
