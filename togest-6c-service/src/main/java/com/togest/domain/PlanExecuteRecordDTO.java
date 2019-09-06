package com.togest.domain;

public class PlanExecuteRecordDTO  extends PlanExecuteRecord{

	private String deptName;
	
	private String recordStatusName;
	
	private PlanExecuteDTO planExecute;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRecordStatusName() {
		return recordStatusName;
	}

	public void setRecordStatusName(String recordStatusName) {
		this.recordStatusName = recordStatusName;
	}

	public PlanExecuteDTO getPlanExecute() {
		return planExecute;
	}

	public void setPlanExecute(PlanExecuteDTO planExecute) {
		this.planExecute = planExecute;
	}
	
	
}
