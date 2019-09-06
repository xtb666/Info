package com.togest.config;

public enum PlanStatus {

	PlanRegister("1"), PlanAudit("2"), PlanExecution("3"), PlanComplete("4"),Cannel("5"),PlanInExecution("6");
	
	private final String status;
	PlanStatus(String status){
		this.status=status;
	}
	public String getStatus() {
		return status;
	}
}
