package com.togest.client.resquest;

import java.io.Serializable;

/**
 * 流程启动
 * @author zxf
 *
 */
public class FlowStartRequest implements Serializable{
	
	
	private String businessKey;
	
	
	private String processDefKey;
	
	private String processName;
	
	
	private String userId;
	
	
	private String taskUsers;
	
	private String sectionId;


	public String getBusinessKey() {
		return businessKey;
	}


	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
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


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getTaskUsers() {
		return taskUsers;
	}


	public void setTaskUsers(String taskUsers) {
		this.taskUsers = taskUsers;
	}

	public String getSectionId() {
		return sectionId;
	}


	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	

}
