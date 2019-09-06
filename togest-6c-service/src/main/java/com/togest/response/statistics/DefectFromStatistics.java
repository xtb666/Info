package com.togest.response.statistics;

import java.io.Serializable;

public class DefectFromStatistics implements Serializable {
	
	protected Integer num = 0 ;
	protected Integer numToday = 0 ;
	private Integer numCancel = 0 ;
	protected String workShopName; 
	protected String workShopId; 
	protected String defectStatus; 
	protected String defectLevel;
	protected String defectType;
	
	
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getNumToday() {
		return numToday;
	}
	public void setNumToday(Integer numToday) {
		this.numToday = numToday;
	}
	public String getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}
	public String getDefectLevel() {
		return defectLevel;
	}
	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
	}
	public String getDefectType() {
		return defectType;
	}
	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}
	public Integer getNumCancel() {
		return numCancel;
	}
	public void setNumCancel(Integer numCancel) {
		this.numCancel = numCancel;
	}
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	} 
	
}
