package com.togest.response.statistics;

import java.io.Serializable;

public class DefectReform implements Serializable {

	protected String sectionName;
	protected String lineName;
	protected String directionName;
	protected Integer totalNum;//缺陷总数
	protected Integer reformNum;//整改数量
	protected Integer leftNum;//遗留整改数量
	protected Integer leftTotalNum;//总遗留量
	protected String monthPercent;//本月整改率
	protected String totalPercent;//总整改率
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getReformNum() {
		return reformNum;
	}
	public void setReformNum(Integer reformNum) {
		this.reformNum = reformNum;
	}
	public Integer getLeftNum() {
		return leftNum;
	}
	public void setLeftNum(Integer leftNum) {
		this.leftNum = leftNum;
	}
	public Integer getLeftTotalNum() {
		return leftTotalNum;
	}
	public void setLeftTotalNum(Integer leftTotalNum) {
		this.leftTotalNum = leftTotalNum;
	}
	public String getMonthPercent() {
		return monthPercent;
	}
	public void setMonthPercent(String monthPercent) {
		this.monthPercent = monthPercent;
	}
	public String getTotalPercent() {
		return totalPercent;
	}
	public void setTotalPercent(String totalPercent) {
		this.totalPercent = totalPercent;
	}
}
