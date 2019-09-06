package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class C2Info extends CInfo {

	protected Integer panoramisPixel;
	protected Integer partPixel;
	protected Integer dataType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endTime;
	protected String startPoleno;
	protected String endPoleno;
	
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStartPoleno() {
		return startPoleno;
	}
	public void setStartPoleno(String startPoleno) {
		this.startPoleno = startPoleno;
	}
	public String getEndPoleno() {
		return endPoleno;
	}
	public void setEndPoleno(String endPoleno) {
		this.endPoleno = endPoleno;
	}
	public Integer getPanoramisPixel() {
		return panoramisPixel;
	}
	public void setPanoramisPixel(Integer panoramisPixel) {
		this.panoramisPixel = panoramisPixel;
	}
	public Integer getPartPixel() {
		return partPixel;
	}
	public void setPartPixel(Integer partPixel) {
		this.partPixel = partPixel;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	
	
}
