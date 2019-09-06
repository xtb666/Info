package com.togest.domain;

import java.util.List;

public class PlanBaseDTO extends PlanBase{
	
	private String trainName;
    private String planStatusName;

    private String auditStatusName;
    
    private String systemName;
	
	private List<PlanDetailDTO> planDetails;
	
	private PlanExecuteDTO planExecute;

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public List<PlanDetailDTO> getPlanDetails() {
		return planDetails;
	}

	public void setPlanDetails(List<PlanDetailDTO> planDetails) {
		this.planDetails = planDetails;
	}

	public PlanExecuteDTO getPlanExecute() {
		return planExecute;
	}

	public void setPlanExecute(PlanExecuteDTO planExecute) {
		this.planExecute = planExecute;
	}

	public String getPlanStatusName() {
		return planStatusName;
	}

	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	
	
}