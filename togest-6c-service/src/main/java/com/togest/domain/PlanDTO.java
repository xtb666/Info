package com.togest.domain;

import java.util.List;

public class PlanDTO extends Plan<PlanDTO> {

	protected String trainName;
	protected String startPsaName;
	protected String endPsaName;
	protected String deptName;
	protected String planStatusName;
	protected List<PlanLineDTO> planLineList;
	private String auditStatusName;
	private String flowId;
	private String systemName;
	private String deptIds;
	private List<Naming> deptList;

	public List<Naming> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Naming> deptList) {
		this.deptList = deptList;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getStartPsaName() {
		return startPsaName;
	}

	public void setStartPsaName(String startPsaName) {
		this.startPsaName = startPsaName;
	}

	public String getEndPsaName() {
		return endPsaName;
	}

	public void setEndPsaName(String endPsaName) {
		this.endPsaName = endPsaName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPlanStatusName() {
		return planStatusName;
	}

	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}

	public List<PlanLineDTO> getPlanLineList() {
		return planLineList;
	}

	public void setPlanLineList(List<PlanLineDTO> planLineList) {
		this.planLineList = planLineList;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
