package com.togest.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.domain.SimplePage;
import com.togest.util.Data;

public class CheckQueryFilter extends Data {
	
	private SimplePage page;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	protected String lineId;
	protected String direction;
	protected String systemId;
	protected String rawdataStatus;
	protected Integer dataType;
	protected String checkId;
	protected String trainId;
	protected String speedType;
	
	public String getSpeedType() {
		return speedType;
	}
	public void setSpeedType(String speedType) {
		this.speedType = speedType;
	}
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public SimplePage getPage() {
		return page;
	}
	public void setPage(SimplePage page) {
		this.page = page;
	}
	public Date getBeginCheckDate() {
		return beginCheckDate;
	}
	public void setBeginCheckDate(Date beginCheckDate) {
		this.beginCheckDate = beginCheckDate;
	}
	public Date getEndCheckDate() {
		return endCheckDate;
	}
	public void setEndCheckDate(Date endCheckDate) {
		this.endCheckDate = endCheckDate;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getRawdataStatus() {
		return rawdataStatus;
	}
	public void setRawdataStatus(String rawdataStatus) {
		this.rawdataStatus = rawdataStatus;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	
}
