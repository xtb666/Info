package com.togest.response.statistics;

import java.io.Serializable;
public class PlanMonthAccumulativeData implements Serializable {
	
	protected Integer num = 0;
	protected String systemId;
	protected String excuteStatus;
	protected String date;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getExcuteStatus() {
		return excuteStatus;
	}
	public void setExcuteStatus(String excuteStatus) {
		this.excuteStatus = excuteStatus;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
