package com.togest.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.togest.dict.annotation.DictMark;

public class DefectDetail implements Serializable{

	protected String id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	
	protected Date checkTime;
	
	protected String sectionId;
	protected String sectionName;
	
	protected String lineId;
	protected String lineName;
	
	@DictMark(dictName = "direction", itemName = "directionName")
	protected String direction;
	protected String directionName;
	
	@DictMark(dictName = "speed_type")
	protected String speedType;
	protected String speedTypeName;
	
	protected String workShopId;
	protected String workShopName;
	
	protected String workAreaId;
	protected String workAreaName;
	
	protected String psaId;
	protected String psaName;
	
	protected String tunnelId;
	protected String tunnelName;
	
	@DictMark(dictName = "track")
	protected String trackNo;
	protected String trackName;
	
	protected Integer speed;
	
	protected String pillarId;
	protected String pillarName;
	
	protected String defectName;
	
	protected String defectType;
	protected String defectTypeName;
	
	@DictMark(dictName = "defect_grade",primaryKey="code" )
	protected String defectLevel;
	protected String defectLevelName;
	
	protected Double defectGlb;
	
	@DictMark(dictName = "defect_status",primaryKey="code" )
	protected String defectStatus;
	
	protected String defectStatusName;
	
	protected String flowId;
	
	protected String trainId;
	protected String trainName;
	
	protected String infoId;
	
	protected String planId;
	
	protected Double longitude;
	
	protected Double latitude;
	
	protected String systemId;
	
	protected short delFlag;
	//////////////////////////
	protected String reviewPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date reviewDate;
	
	protected String reviewConclusion;
	
	protected String reviewPhoto;
	
	protected String reviewValue;
	
	protected Integer isReform;
	
	protected String reviewRemark;
	
	protected String reviewInformant;
	
	protected String reviewPillarPhoto;
	
	protected String defectDeviceAttributes;
	//////////////////////////
	protected String handlePerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date handleDate;
	
	protected String handleScheme;
	
	protected String handlePhoto;
	
	protected String handleValue;
	
	protected String handleInformant;
	
	protected String handleBeforePhoto;
	
	protected String handlePillarPhoto;
	
	protected String handleRemark;
	
	protected Integer handleStatus;
	@DictMark(dictName = "defect_audit_status",primaryKey="code" )
	protected String auditStatus;
	
	protected String auditStatusName;

	
	public short getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(short delFlag) {
		this.delFlag = delFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirectionName() {
		return directionName;
	}

	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}

	public String getSpeedType() {
		return speedType;
	}

	public void setSpeedType(String speedType) {
		this.speedType = speedType;
	}

	public String getSpeedTypeName() {
		return speedTypeName;
	}

	public void setSpeedTypeName(String speedTypeName) {
		this.speedTypeName = speedTypeName;
	}

	public String getWorkShopId() {
		return workShopId;
	}

	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}

	public String getWorkShopName() {
		return workShopName;
	}

	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}

	public String getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(String workAreaId) {
		this.workAreaId = workAreaId;
	}

	public String getWorkAreaName() {
		return workAreaName;
	}

	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}

	public String getPsaId() {
		return psaId;
	}

	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}

	public String getPsaName() {
		return psaName;
	}

	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}

	public String getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}

	public String getTunnelName() {
		return tunnelName;
	}

	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public String getPillarId() {
		return pillarId;
	}

	public void setPillarId(String pillarId) {
		this.pillarId = pillarId;
	}

	public String getPillarName() {
		return pillarName;
	}

	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}

	public String getDefectName() {
		return defectName;
	}

	public void setDefectName(String defectName) {
		this.defectName = defectName;
	}

	public String getDefectType() {
		return defectType;
	}

	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}

	public String getDefectTypeName() {
		return defectTypeName;
	}

	public void setDefectTypeName(String defectTypeName) {
		this.defectTypeName = defectTypeName;
	}

	public String getDefectLevel() {
		return defectLevel;
	}

	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
	}

	public String getDefectLevelName() {
		return defectLevelName;
	}

	public void setDefectLevelName(String defectLevelName) {
		this.defectLevelName = defectLevelName;
	}

	public Double getDefectGlb() {
		return defectGlb;
	}

	public void setDefectGlb(Double defectGlb) {
		this.defectGlb = defectGlb;
	}

	public String getDefectStatus() {
		return defectStatus;
	}

	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getReviewPerson() {
		return reviewPerson;
	}

	public void setReviewPerson(String reviewPerson) {
		this.reviewPerson = reviewPerson;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewConclusion() {
		return reviewConclusion;
	}

	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}

	public String getReviewPhoto() {
		return reviewPhoto;
	}

	public void setReviewPhoto(String reviewPhoto) {
		this.reviewPhoto = reviewPhoto;
	}

	public String getReviewValue() {
		return reviewValue;
	}

	public void setReviewValue(String reviewValue) {
		this.reviewValue = reviewValue;
	}

	public Integer getIsReform() {
		return isReform;
	}

	public void setIsReform(Integer isReform) {
		this.isReform = isReform;
	}

	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}

	public String getReviewInformant() {
		return reviewInformant;
	}

	public void setReviewInformant(String reviewInformant) {
		this.reviewInformant = reviewInformant;
	}

	public String getReviewPillarPhoto() {
		return reviewPillarPhoto;
	}

	public void setReviewPillarPhoto(String reviewPillarPhoto) {
		this.reviewPillarPhoto = reviewPillarPhoto;
	}

	public String getHandlePerson() {
		return handlePerson;
	}

	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public String getHandleScheme() {
		return handleScheme;
	}

	public void setHandleScheme(String handleScheme) {
		this.handleScheme = handleScheme;
	}

	public String getHandlePhoto() {
		return handlePhoto;
	}

	public void setHandlePhoto(String handlePhoto) {
		this.handlePhoto = handlePhoto;
	}

	public String getHandleValue() {
		return handleValue;
	}

	public void setHandleValue(String handleValue) {
		this.handleValue = handleValue;
	}

	public String getHandleInformant() {
		return handleInformant;
	}

	public void setHandleInformant(String handleInformant) {
		this.handleInformant = handleInformant;
	}

	public String getHandleBeforePhoto() {
		return handleBeforePhoto;
	}

	public void setHandleBeforePhoto(String handleBeforePhoto) {
		this.handleBeforePhoto = handleBeforePhoto;
	}

	public String getHandlePillarPhoto() {
		return handlePillarPhoto;
	}

	public void setHandlePillarPhoto(String handlePillarPhoto) {
		this.handlePillarPhoto = handlePillarPhoto;
	}

	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getDefectStatusName() {
		return defectStatusName;
	}

	public void setDefectStatusName(String defectStatusName) {
		this.defectStatusName = defectStatusName;
	}

	public String getDefectDeviceAttributes() {
		return defectDeviceAttributes;
	}

	public void setDefectDeviceAttributes(String defectDeviceAttributes) {
		this.defectDeviceAttributes = defectDeviceAttributes;
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
	
}
