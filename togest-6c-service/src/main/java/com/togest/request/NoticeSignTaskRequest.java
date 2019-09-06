package com.togest.request;

public class NoticeSignTaskRequest extends TaskRequest{

	private String sectionId;
	private String flowKey;
	private String flowNode;
	private String flowAuditCode;
	
	private String defectKey;
	private String defectNode;
	private String defectCode;
	
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
	public String getDefectKey() {
		return defectKey;
	}
	public void setDefectKey(String defectKey) {
		this.defectKey = defectKey;
	}
	public String getDefectNode() {
		return defectNode;
	}
	public void setDefectNode(String defectNode) {
		this.defectNode = defectNode;
	}
	public String getDefectCode() {
		return defectCode;
	}
	public void setDefectCode(String defectCode) {
		this.defectCode = defectCode;
	}
	
	
}
