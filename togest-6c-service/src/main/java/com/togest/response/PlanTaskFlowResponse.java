package com.togest.response;

import com.togest.domain.DefectFlow;

public class PlanTaskFlowResponse extends DefectFlow{

	private String deptId;
	private String patcher;
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getPatcher() {
		return patcher;
	}
	public void setPatcher(String patcher) {
		this.patcher = patcher;
	}	
}
