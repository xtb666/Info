package com.togest.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class C1TaskRequest extends TaskRequest{

	 private String startStation;

	 private String endStation;
	 
	 private String patcher;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	 private Date  addDate;
	 
	 private String deptId;
	 
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public String getEndStation() {
		return endStation;
	}

	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	public String getPatcher() {
		return patcher;
	}

	public void setPatcher(String patcher) {
		this.patcher = patcher;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	 
	 
}
