package com.togest.response.statistics;

import java.io.Serializable;

public class DefectLevelStatistics implements Serializable{
	
	protected String defectLevelName;
	protected Integer num;
	protected String systemId;
	
	public String getDefectLevelName() {
		return defectLevelName;
	}
	public void setDefectLevelName(String defectLevelName) {
		this.defectLevelName = defectLevelName;
	}
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
	
}
