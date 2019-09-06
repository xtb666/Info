package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.dict.annotation.DictMark;

public class Defect<T> extends BaseEntity<T> {
	
	protected String id;
	protected String defectAssortmentId;
	
	protected String defectDataCategory;
	@DictMark(dictName="busy_defect_data_level",primaryKey="code")
	protected String defectDataLevel;
	
	protected String defectTypeDetail;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date confirmDate;
	
	protected String checkTime;
	
	protected String sectionId;
	
	protected String lineId;
	@DictMark(dictName = "direction", itemName = "directionName")
	protected String direction;
	@DictMark(dictName = "speed_type")
	protected String speedType;
	
	protected String workShopId;
	
	protected String workAreaId;
	
	protected String psaId;
	
	protected String tunnelId;
	
	protected String trackNo;
	
	protected Double speed;
	
	protected String pillarId;
	
	protected String pillarName;

	protected String defectName;
	
	protected String defectType;

	@DictMark(dictName = "defect_grade",primaryKey="code")
	protected String defectLevel;
	
	protected Double defectGlb;
	
	@DictMark(dictName = "defect_status",primaryKey="code" )
	protected String defectStatus;
	
	protected String description;
	
	protected String flowId;
	
	protected String trainId;
	
	protected String infoId;
	
	protected String planId;
	
	protected Double longitude;
	
	protected Double latitude;
	
	protected String systemId;
	
	protected Integer typicalDefect;
	
	@DictMark(dictName = "defect_2c_equ_name",primaryKey="code")
	protected String equName;
	//设备位置
	protected String equPosition;
	
	protected Integer isShow;
	
	protected Integer manualFlag;
	
	protected String defectTypeName; //缺陷类型名称

	protected short delFlag = 0;
	
	protected String createIp;
	
	protected String createBy;
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date createDate;
	@JsonIgnore
	protected String updateIp;
	
	protected String updateBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date updateDate;
	@JsonIgnore
	protected String deleteIp;
	@JsonIgnore
	protected String deleteBy;
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date deleteDate;
	
	protected Integer sort;
	protected Integer repeatCount = 0;
	
	protected String defectRepeatCountId;
	

	public String getDefectDataLevel() {
		return defectDataLevel;
	}

	public void setDefectDataLevel(String defectDataLevel) {
		this.defectDataLevel = defectDataLevel;
	}

	public String getEquPosition() {
		return equPosition;
	}

	public void setEquPosition(String equPosition) {
		this.equPosition = equPosition;
	}

	public String getEquName() {
		return equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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

	public String getSpeedType() {
		return speedType;
	}

	public void setSpeedType(String speedType) {
		this.speedType = speedType;
	}

	public String getWorkShopId() {
		return workShopId;
	}

	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}

	public String getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(String workAreaId) {
		this.workAreaId = workAreaId;
	}

	public String getPsaId() {
		return psaId;
	}

	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}

	public String getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getPillarId() {
		return pillarId;
	}

	public void setPillarId(String pillarId) {
		this.pillarId = pillarId;
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

	public String getDefectLevel() {
		return defectLevel;
	}

	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
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

	public short getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(short delFlag) {
		this.delFlag = delFlag;
	}


	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteIp() {
		return deleteIp;
	}

	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefectRepeatCountId() {
		return defectRepeatCountId;
	}

	public void setDefectRepeatCountId(String defectRepeatCountId) {
		this.defectRepeatCountId = defectRepeatCountId;
	}

	public Integer getTypicalDefect() {
		return typicalDefect;
	}

	public void setTypicalDefect(Integer typicalDefect) {
		this.typicalDefect = typicalDefect;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getDefectAssortmentId() {
		return defectAssortmentId;
	}

	public void setDefectAssortmentId(String defectAssortmentId) {
		this.defectAssortmentId = defectAssortmentId;
	}

	public String getDefectDataCategory() {
		return defectDataCategory;
	}

	public void setDefectDataCategory(String defectDataCategory) {
		this.defectDataCategory = defectDataCategory;
	}

	public String getDefectTypeDetail() {
		return defectTypeDetail;
	}

	public void setDefectTypeDetail(String defectTypeDetail) {
		this.defectTypeDetail = defectTypeDetail;
	}

	public String getPillarName() {
		return pillarName;
	}

	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}
	
	public Integer getManualFlag() {
		return manualFlag;
	}

	public void setManualFlag(Integer manualFlag) {
		this.manualFlag = manualFlag;
	}

	public String getDefectTypeName() {
		return defectTypeName;
	}

	public void setDefectTypeName(String defectTypeName) {
		this.defectTypeName = defectTypeName;
	}
}
