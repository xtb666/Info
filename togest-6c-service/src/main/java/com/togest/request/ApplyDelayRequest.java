package com.togest.request;

import com.togest.domain.DefectDelay;

public class ApplyDelayRequest extends DefectDelay{

	private String userId;
	private String UserName;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	
}
