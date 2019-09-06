package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class C4InfoDTO extends C4Info {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	protected String lineName;
	protected String directionName;
	protected String deptName;
	protected String trainName;
	protected String startStationName;
	protected String endStationName;
	protected Double startKm;
	protected Double endKm;
	protected String rawdataStatusName;
	protected String analyStatusName;
	protected String defectDataStatusName;
	private String stationName;
	private String pillarName;
	private String planDetailCheckRegion;
	
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getPillarName() {
		return pillarName;
	}

	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
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
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
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

	public String getPlanDetailCheckRegion() {
		return planDetailCheckRegion;
	}

	public void setPlanDetailCheckRegion(String planDetailCheckRegion) {
		this.planDetailCheckRegion = planDetailCheckRegion;
	}
}
