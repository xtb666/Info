package com.togest.domain;

import com.togest.common.util.string.StringUtil;
import com.togest.dict.annotation.DictMark;

public class DefectDTO extends Defect<DefectDTO>{
	private String sectionName;
	private String lineName;
	private String directionName;
	private String speedTypeName;
	private String workShopName;
	private String workAreaName;
	private String psaName;
	private String tunnelName;
	private String trainName;
	private String trackName;
	private String defectTypeName;
	private String defectTypeDetailName;
	private String defectLevelName;
	private String defectStatusName;
	private String systemName;
	@DictMark(dictName = "defect_audit_status",primaryKey="code")
	private String auditStatus;
	private String auditStatusName;
	private String comment;
	private String equNameName;
	private String defectDataLevelName;

	
	public String getDefectDataLevelName() {
		return defectDataLevelName;
	}
	public void setDefectDataLevelName(String defectDataLevelName) {
		this.defectDataLevelName = defectDataLevelName;
	}
	public String getEquNameName() {
		return equNameName;
	}
	public void setEquNameName(String equNameName) {
		this.equNameName = equNameName;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditStatusName() {
		return auditStatusName;
	}
	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
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
	public String getSpeedTypeName() {
		return speedTypeName;
	}
	public void setSpeedTypeName(String speedTypeName) {
		this.speedTypeName = speedTypeName;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public String getWorkAreaName() {
		return workAreaName;
	}
	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	public String getTunnelName() {
		return tunnelName;
	}
	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getDefectTypeName() {
		return defectTypeName;
	}
	public void setDefectTypeName(String defectTypeName) {
		this.defectTypeName = defectTypeName;
	}
	public String getDefectLevelName() {
		if (StringUtil.isNotBlank(this.defectDataLevelName) && StringUtil.isNotBlank(this.defectDataCategory)) {
			return this.defectDataLevelName + this.defectDataCategory;
		}
		return defectLevelName;
	}
	public void setDefectLevelName(String defectLevelName) {
		this.defectLevelName = defectLevelName;
	}
	public String getDefectStatusName() {
		return defectStatusName;
	}
	public void setDefectStatusName(String defectStatusName) {
		this.defectStatusName = defectStatusName;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDefectTypeDetailName() {
		return defectTypeDetailName;
	}
	public void setDefectTypeDetailName(String defectTypeDetailName) {
		this.defectTypeDetailName = defectTypeDetailName;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
}
