package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Check1CSectionDTO extends Check1CSection<Check1CSectionDTO>{
	
	protected String deptName;
	protected String workShopName;;
	protected String sectionName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	protected String lineId;
	protected String lineName;
	protected String direction;
	protected String directionName;
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getWorkShopName() {
		return workShopName;
	}

	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDirectionName() {
		return directionName;
	}

	public void setDirectionName(String directionName) {
		this.directionName = directionName;
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
	
}
