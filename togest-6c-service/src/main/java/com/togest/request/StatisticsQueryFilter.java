package com.togest.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.util.Data;

public class StatisticsQueryFilter extends Data {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date thisStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date thisEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date momStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date momEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date yoyStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date yoyEnd;
	
	protected String systemId;
	protected String defectLevel;
	protected String workShopId;
	protected String workAreaId;
	private String lineId;
	private String psaId;
	private String type;
	private String DORY;
	
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	public String getWorkAreaId() {
		return workAreaId;
	}
	public void setWorkAreaId(String workAreaId) {
		this.workAreaId = workAreaId;
	}
	public Date getThisStart() {
		return thisStart;
	}
	public void setThisStart(Date thisStart) {
		this.thisStart = thisStart;
	}
	public Date getThisEnd() {
		return thisEnd;
	}
	public void setThisEnd(Date thisEnd) {
		this.thisEnd = thisEnd;
	}
	public Date getMomStart() {
		return momStart;
	}
	public void setMomStart(Date momStart) {
		this.momStart = momStart;
	}
	public Date getMomEnd() {
		return momEnd;
	}
	public void setMomEnd(Date momEnd) {
		this.momEnd = momEnd;
	}
	public Date getYoyStart() {
		return yoyStart;
	}
	public void setYoyStart(Date yoyStart) {
		this.yoyStart = yoyStart;
	}
	public Date getYoyEnd() {
		return yoyEnd;
	}
	public void setYoyEnd(Date yoyEnd) {
		this.yoyEnd = yoyEnd;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getDefectLevel() {
		return defectLevel;
	}
	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getPsaId() {
		return psaId;
	}
	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDORY() {
		return DORY;
	}
	public void setDORY(String dORY) {
		DORY = dORY;
	}
	
}
