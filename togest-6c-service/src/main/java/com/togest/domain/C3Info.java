package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class C3Info extends CInfo {

	protected Double km;
	protected String infoStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endTime;
	protected String startPoleno;
	protected String endPoleno;
	

	public String getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}

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

	public Double getKm() {
		return km;
	}

	public void setKm(Double km) {
		this.km = km;
	}
}
