package com.togest.response.statistics;

import java.io.Serializable;

public class DefectTopDefect implements Serializable{
	
	protected Integer num = 0; 
	protected String workShopName; 
	protected String defectTypeName;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public String getDefectTypeName() {
		return defectTypeName;
	}
	public void setDefectTypeName(String defectTypeName) {
		this.defectTypeName = defectTypeName;
	}
}
