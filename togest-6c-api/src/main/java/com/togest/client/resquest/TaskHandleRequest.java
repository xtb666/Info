package com.togest.client.resquest;

import java.io.Serializable;
import java.util.List;

/**
 * 任务办理
 * @author zxf
 *
 */
public class TaskHandleRequest implements Serializable{

	private String processInstanceId;

	private String taskId;

	private String userId;

	private String leaderPass;
	
	private String taskUsers;

	private String comment;
	
	private String sectionId;
	
	private List<String> taskUsersList;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLeaderPass() {
		return leaderPass;
	}

	public void setLeaderPass(String leaderPass) {
		this.leaderPass = leaderPass;
	}

	public String getTaskUsers() {
		return taskUsers;
	}

	public void setTaskUsers(String taskUsers) {
		this.taskUsers = taskUsers;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public List<String> getTaskUsersList() {
		return taskUsersList;
	}

	public void setTaskUsersList(List<String> taskUsersList) {
		this.taskUsersList = taskUsersList;
	}

}
