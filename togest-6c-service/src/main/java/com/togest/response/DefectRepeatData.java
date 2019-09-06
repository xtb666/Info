package com.togest.response;

import java.io.Serializable;
import java.util.Date;

public class DefectRepeatData implements Serializable{

	private String id;
	private Date checkDate;
	private Double defectGlb;
	private Integer repeatCount;
	private String systemId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Double getDefectGlb() {
		return defectGlb;
	}
	public void setDefectGlb(Double defectGlb) {
		this.defectGlb = defectGlb;
	}
	public Integer getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
}
