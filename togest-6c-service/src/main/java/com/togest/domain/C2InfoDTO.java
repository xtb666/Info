package com.togest.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class C2InfoDTO extends C2Info{
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	protected String lineName;
	protected String directionName;
	protected String deptName;
	protected String trainName;
	protected Double startKm;
	protected Double endKm;
	protected String rawdataStatusName;
	protected String analyStatusName;
	protected String defectDataStatusName;
	protected String planDetailCheckRegion;
	
	protected List<Naming> deptNameList;
	
	public String getAnalyStatusName() {
		return analyStatusName;
	}
	public void setAnalyStatusName(String analyStatusName) {
		this.analyStatusName = analyStatusName;
	}
	public String getDefectDataStatusName() {
		return defectDataStatusName;
	}
	public void setDefectDataStatusName(String defectDataStatusName) {
		this.defectDataStatusName = defectDataStatusName;
	}
	public List<Naming> getDeptNameList() {
		return deptNameList;
	}
	public void setDeptNameList(List<Naming> deptNameList) {
		this.deptNameList = deptNameList;
	}
	public String getPlanDetailCheckRegion() {
		return planDetailCheckRegion;
	}
	public void setPlanDetailCheckRegion(String planDetailCheckRegion) {
		this.planDetailCheckRegion = planDetailCheckRegion;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public Double getStartKm() {
		return startKm;
	}
	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}
	public Double getEndKm() {
		return endKm;
	}
	public void setEndKm(Double endKm) {
		this.endKm = endKm;
	}
	public String getRawdataStatusName() {
		return rawdataStatusName;
	}
	public void setRawdataStatusName(String rawdataStatusName) {
		this.rawdataStatusName = rawdataStatusName;
	}
}
