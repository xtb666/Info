package com.togest.request;

import java.io.Serializable;


public class ProcessInstanceRequest implements Serializable{

	private String id;
	private String processDefKey;
	private String processName;
	private String businessKey;
	private String userId;
	private String taskUser;
	private String taskGroups;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessDefKey() {
		return processDefKey;
	}
	public void setProcessDefKey(String processDefKey) {
		this.processDefKey = processDefKey;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTaskUser() {
		return taskUser;
	}
	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}
	public String getTaskGroups() {
		return taskGroups;
	}
	public void setTaskGroups(String taskGroups) {
		this.taskGroups = taskGroups;
	}

	
	
}
