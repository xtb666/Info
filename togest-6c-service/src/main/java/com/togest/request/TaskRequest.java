package com.togest.request;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class TaskRequest implements Serializable{
	private String id;
	private String leaderPass;
	private String comment;
	private String userId;
	private String taskUsers;
	private String userName;
	private String sectionId;
	 //实际添乘日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date actualAddDate;
    //实际添乘人
    protected String actualPatcher;
    //实际检测区间
    protected String actualCheckRegion;
    //实际添乘车次
    protected String actualAddTrainNumber;
    //实际添乘机车号
    protected String actualAddTrafficNumber;
    //设备编号
    protected String equNo;
    //设备运行情况
    protected String equOperation;
    
    protected String implementation;
    
    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation == null ? null : implementation.trim();
    }
	
	
	public Date getActualAddDate() {
		return actualAddDate;
	}
	public void setActualAddDate(Date actualAddDate) {
		this.actualAddDate = actualAddDate;
	}
	public String getActualPatcher() {
		return actualPatcher;
	}
	public void setActualPatcher(String actualPatcher) {
		this.actualPatcher = actualPatcher;
	}
	public String getActualCheckRegion() {
		return actualCheckRegion;
	}
	public void setActualCheckRegion(String actualCheckRegion) {
		this.actualCheckRegion = actualCheckRegion;
	}
	public String getActualAddTrainNumber() {
		return actualAddTrainNumber;
	}
	public void setActualAddTrainNumber(String actualAddTrainNumber) {
		this.actualAddTrainNumber = actualAddTrainNumber;
	}
	public String getActualAddTrafficNumber() {
		return actualAddTrafficNumber;
	}
	public void setActualAddTrafficNumber(String actualAddTrafficNumber) {
		this.actualAddTrafficNumber = actualAddTrafficNumber;
	}
	public String getEquNo() {
		return equNo;
	}
	public void setEquNo(String equNo) {
		this.equNo = equNo;
	}
	public String getEquOperation() {
		return equOperation;
	}
	public void setEquOperation(String equOperation) {
		this.equOperation = equOperation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeaderPass() {
		return leaderPass;
	}
	public void setLeaderPass(String leaderPass) {
		this.leaderPass = leaderPass;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
}
