package com.togest.request;

import java.io.Serializable;

import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectDelay;
import com.togest.domain.DefectReformHandle;

public class CheckReformRequest implements Serializable {

	private DefectCheckHandle defectCheckHandle;

	private DefectDelay defectDelay;

	private DefectReformHandle defectReformHandle;

	private String userId;

	private String userName;
	
	private String taskUsers;

	public DefectCheckHandle getDefectCheckHandle() {
		return defectCheckHandle;
	}

	public void setDefectCheckHandle(DefectCheckHandle defectCheckHandle) {
		this.defectCheckHandle = defectCheckHandle;
	}

	public DefectDelay getDefectDelay() {
		return defectDelay;
	}

	public void setDefectDelay(DefectDelay defectDelay) {
		this.defectDelay = defectDelay;
	}

	public DefectReformHandle getDefectReformHandle() {
		return defectReformHandle;
	}

	public void setDefectReformHandle(DefectReformHandle defectReformHandle) {
		this.defectReformHandle = defectReformHandle;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTaskUsers() {
		return taskUsers;
	}

	public void setTaskUsers(String taskUsers) {
		this.taskUsers = taskUsers;
	}

}
