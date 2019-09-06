package com.togest.response;

import com.togest.common.util.string.StringUtil;
import com.togest.dict.annotation.DictMark;
import com.togest.domain.Defect;
import com.togest.domain.DefectHandleInfo;

public class DefectForm<T> extends Defect<T> {

	protected DefectHandleInfo defectHandleInfo;
	@DictMark(dictName = "defect_audit_status", primaryKey = "code")
	protected String auditStatus;
	protected String auditStatusName;
	protected Integer count  = 0;
	
	protected String sectionName;
	protected String lineName;
	protected String directionName;
	protected String speedTypeName;
	protected String workShopName;
	protected String workAreaName;
	protected String psaName;
	protected String tunnelName;
	protected String trackName;
	protected String pillarName;
	protected String defectTypeName;
	protected String defectTypeDetailName;
	protected String defectLevelName;
	protected String defectStatusName;
	protected String trainName;
	protected String equNameName;
	protected String defectDataLevelName;
	
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public DefectHandleInfo getDefectHandleInfo() {
		return defectHandleInfo;
	}

	public void setDefectHandleInfo(DefectHandleInfo defectHandleInfo) {
		this.defectHandleInfo = defectHandleInfo;
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

	public String getPillarName() {
		return pillarName;
	}

	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
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

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
	public String getDefectTypeDetailName() {
		return defectTypeDetailName;
	}
	public void setDefectTypeDetailName(String defectTypeDetailName) {
		this.defectTypeDetailName = defectTypeDetailName;
	}
}
