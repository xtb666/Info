package com.togest.request;



public class TaskRejectRequest extends TaskRequest{
	
	private String activityNodeId;
	
	public String getActivityNodeId() {
		return activityNodeId;
	}

	public void setActivityNodeId(String activityNodeId) {
		this.activityNodeId = activityNodeId;
	}
	
}
