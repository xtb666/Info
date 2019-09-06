package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class C4Info extends CInfo {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endTime;
	protected String startPoleno;
	protected String endPoleno;
	protected Integer cameraNumber;
	protected Integer supportCameraNumber;
	protected Integer droppersCameraNumber;
	protected Integer appendCameraNumber;
	protected Integer davitCameraNumber;
	
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
	public Integer getCameraNumber() {
		return cameraNumber;
	}
	public void setCameraNumber(Integer cameraNumber) {
		this.cameraNumber = cameraNumber;
	}
	public Integer getSupportCameraNumber() {
		return supportCameraNumber;
	}
	public void setSupportCameraNumber(Integer supportCameraNumber) {
		this.supportCameraNumber = supportCameraNumber;
	}
	public Integer getDroppersCameraNumber() {
		return droppersCameraNumber;
	}
	public void setDroppersCameraNumber(Integer droppersCameraNumber) {
		this.droppersCameraNumber = droppersCameraNumber;
	}
	public Integer getAppendCameraNumber() {
		return appendCameraNumber;
	}
	public void setAppendCameraNumber(Integer appendCameraNumber) {
		this.appendCameraNumber = appendCameraNumber;
	}
	public Integer getDavitCameraNumber() {
		return davitCameraNumber;
	}
	public void setDavitCameraNumber(Integer davitCameraNumber) {
		this.davitCameraNumber = davitCameraNumber;
	}
}
