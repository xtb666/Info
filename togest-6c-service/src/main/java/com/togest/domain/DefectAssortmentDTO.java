package com.togest.domain;

public class DefectAssortmentDTO extends DefectAssortment<DefectAssortmentDTO>{

	private String equName;
	private String defectTypeName;
	private String defectDataLevelName;
	private String systemName;
	private String speedTypeName;
	
	public String getEquName() {
		return equName;
	}
	public void setEquName(String equName) {
		this.equName = equName;
	}
	public String getDefectTypeName() {
		return defectTypeName;
	}
	public void setDefectTypeName(String defectTypeName) {
		this.defectTypeName = defectTypeName;
	}
	public String getDefectDataLevelName() {
		return defectDataLevelName;
	}
	public void setDefectDataLevelName(String defectDataLevelName) {
		this.defectDataLevelName = defectDataLevelName;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSpeedTypeName() {
		return speedTypeName;
	}
	public void setSpeedTypeName(String speedTypeName) {
		this.speedTypeName = speedTypeName;
	}
	
}
