package com.togest.response.statistics;

public class PieChartInfo {

	protected String id;
	protected String name;
	protected Double num = 0d;
	protected String defectStatus;
	protected String defectLevel;
	protected Double numCancel = 0d;
	protected Double numNoCancel = 0d;
	protected Double numLevel1 = 0d;
	protected Double numLevel2 = 0d;
	protected Double numReformedLevel1 = 0d;
	protected Double numReformedLevel2 = 0d;
	

	
	public String getDefectLevel() {
		return defectLevel;
	}
	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
	}
	public Double getNumLevel1() {
		return numLevel1;
	}
	public void setNumLevel1(Double numLevel1) {
		this.numLevel1 = numLevel1;
	}
	public Double getNumLevel2() {
		return numLevel2;
	}
	public void setNumLevel2(Double numLevel2) {
		this.numLevel2 = numLevel2;
	}
	public Double getNumReformedLevel1() {
		return numReformedLevel1;
	}
	public void setNumReformedLevel1(Double numReformedLevel1) {
		this.numReformedLevel1 = numReformedLevel1;
	}
	public Double getNumReformedLevel2() {
		return numReformedLevel2;
	}
	public void setNumReformedLevel2(Double numReformedLevel2) {
		this.numReformedLevel2 = numReformedLevel2;
	}
	public Double getNumCancel() {
		return numCancel;
	}
	public void setNumCancel(Double numCancel) {
		this.numCancel = numCancel;
	}
	public Double getNumNoCancel() {
		return numNoCancel;
	}
	public void setNumNoCancel(Double numNoCancel) {
		this.numNoCancel = numNoCancel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public String getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}
}
