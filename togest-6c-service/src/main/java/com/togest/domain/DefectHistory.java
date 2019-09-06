package com.togest.domain;

public class DefectHistory extends BaseEntity<DefectHistory>{
	
	protected String id;
	protected String pillarId;
	protected String defectType;
	protected String systemId;
	protected Integer count;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPillarId() {
		return pillarId;
	}
	public void setPillarId(String pillarId) {
		this.pillarId = pillarId;
	}
	public String getDefectType() {
		return defectType;
	}
	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
