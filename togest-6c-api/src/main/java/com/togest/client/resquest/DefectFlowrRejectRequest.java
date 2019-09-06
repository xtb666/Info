package com.togest.client.resquest;

import java.io.Serializable;

public class DefectFlowrRejectRequest implements Serializable{

	private String taskId;
	private String activityNodeId;
	private String userId;
	private String processInstanceId;
	private String comment;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getActivityNodeId() {
		return activityNodeId;
	}
	public void setActivityNodeId(String activityNodeId) {
		this.activityNodeId = activityNodeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
