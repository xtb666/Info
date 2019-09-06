package com.togest.response.statistics;

import java.io.Serializable;

public class DefectTypeStatistics implements Serializable{

	protected String defectTypeName;
	protected Integer num;
	protected String systemId;
	
	public String getDefectTypeName() {
		return defectTypeName;
	}
	public void setDefectTypeName(String defectTypeName) {
		this.defectTypeName = defectTypeName;
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
