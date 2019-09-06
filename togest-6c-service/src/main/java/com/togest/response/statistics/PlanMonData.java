package com.togest.response.statistics;

import java.io.Serializable;

public class PlanMonData implements Serializable {
	
	protected Integer num = 0;
	protected String SystemId;
	protected Integer planDate;
	protected String lineName ="";
	protected String sectionName ="";
	protected String lineId;
	protected String sectionId;
	protected Integer cancelNum = 0;
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getSystemId() {
		return SystemId;
	}
	public void setSystemId(String systemId) {
		SystemId = systemId;
	}
	public Integer getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Integer planDate) {
		this.planDate = planDate;
	}
	public Integer getCancelNum() {
		return cancelNum;
	}
	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
}
