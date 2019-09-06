package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.dict.annotation.DictMark;

public class C1InfoDTO extends C1Info{

	protected String lineName;
	protected String rawdataStatusName;
	protected String analyStatusName;
	protected String defectDataStatusName;
	protected String directionName;
	
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
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	public String getRawdataStatusName() {
		return rawdataStatusName;
	}
	public void setRawdataStatusName(String rawdataStatusName) {
		this.rawdataStatusName = rawdataStatusName;
	}
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
}
