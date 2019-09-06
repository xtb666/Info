package com.togest.response.statistics;

import java.io.Serializable;

public class CTypeStatistics implements Serializable {
	
	protected String cName;
	protected Integer num;
	protected String systemId;
	
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}
