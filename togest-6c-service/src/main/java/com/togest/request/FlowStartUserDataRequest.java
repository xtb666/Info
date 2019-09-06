package com.togest.request;

import java.io.Serializable;

public class FlowStartUserDataRequest implements Serializable{
	
	private String id;
	private String userId;
	private String deptId;
	private String sectionId;
	
	private String flowKey;
	private String flowNode;
	private String flowAuditCode;
	
	private Integer flowDay = 7;
	
	public Integer getFlowDay() {
		return flowDay;
	}

	public void setFlowDay(Integer flowDay) {
		this.flowDay = flowDay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getFlowKey() {
		return flowKey;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	public String getFlowNode() {
		return flowNode;
	}

	public void setFlowNode(String flowNode) {
		this.flowNode = flowNode;
	}

	public String getFlowAuditCode() {
		return flowAuditCode;
	}

	public void setFlowAuditCode(String flowAuditCode) {
		this.flowAuditCode = flowAuditCode;
	}

}
