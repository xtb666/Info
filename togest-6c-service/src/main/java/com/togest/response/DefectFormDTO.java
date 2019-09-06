package com.togest.response;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.common.util.string.StringUtil;
import com.togest.dict.annotation.DictMark;
import com.togest.domain.DefectHandleInfo;

public class DefectFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String defectAssortmentId;
	private String defectDataCategory;
	@DictMark(dictName = "busy_defect_data_level", primaryKey = "code")
	private String defectDataLevel;

	private String defectTypeDetail;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkDate;

	private String checkTime;

	private String sectionId;

	private String lineId;
	@DictMark(dictName = "direction", itemName = "directionName")
	private String direction;
	@DictMark(dictName = "speed_type")
	private String speedType;

	private String workShopId;

	private String workAreaId;

	private String psaId;

	private String tunnelId;

	private String trackNo;

	private Double speed;

	private String pillarId;

	private String pillarName;

	private String defectName;

	private String defectType;

	@DictMark(dictName = "defect_grade", primaryKey = "code")
	private String defectLevel;

	private Double defectGlb;

	@DictMark(dictName = "defect_status", primaryKey = "code")
	private String defectStatus;

	private String description;

	private String flowId;

	private String trainId;

	private String infoId;

	private String planId;

	private Double longitude;

	private Double latitude;

	private String systemId;

	private Integer typicalDefect;

	@DictMark(dictName = "defect_2c_equ_name", primaryKey = "code")
	private String equName;
	// 设备位置
	private String equPosition;

	private Integer isShow;
	private Integer manualFlag;

	private String defectTypeName; // 缺陷类型名称

	private DefectHandleInfo defectHandleInfo;
	@DictMark(dictName = "defect_auditStatus", primaryKey = "code")
	private String auditStatus;
	private String auditStatusName;
	private Integer count = 0;
	private Integer repeatCount = 0;
	private String sectionName;
	private String lineName;
	private String directionName;
	private String speedTypeName;
	private String workShopName;
	private String workAreaName;
	private String psaName;
	private String tunnelName;
	private String trackName;
	private String defectTypeDetailName;
	private String defectLevelName;
	private String defectStatusName;
	private String trainName;
	private String equNameName;
	private String defectDataLevelName;
	private String defectRepeatCountId;
	private String createIp;
	
	private String createBy;
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	@JsonIgnore
	private String updateIp;
	
	private String updateBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;
	@JsonIgnore
	private String deleteIp;
	@JsonIgnore
	private String deleteBy;
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deleteDate;
	
	private String testingPerson;// '检测人员';
	private String analyticalPerson;// '分析人';
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date analyticalDate;// '分析日期';

	private Double defectValue;// '缺陷值';
	private Integer points;// '扣分数';

	private String alarmStatus;// '报警状态';
	private String arcingTime;// ' 燃弧时间';
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reviewDate;// '复核日期';
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date handleDate;// '整改日期';

	private String confirmPerson;// '确认人';
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date confirmDate;// '确认日期';
	private String cancelPerson;// '销号人';
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date cancelDate;// '销号时间';
	private Integer checkTimeouted;// '复核超时';
	private Integer isTimeouted;// '是否已超时',
	private String comment;// '驳回意见';
	private Integer delFlag = 0;
	private Integer sort;
	
	
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

	public String getDefectRepeatCountId() {
		return defectRepeatCountId;
	}

	public void setDefectRepeatCountId(String defectRepeatCountId) {
		this.defectRepeatCountId = defectRepeatCountId;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDefectDataLevel() {
		return defectDataLevel;
	}

	public void setDefectDataLevel(String defectDataLevel) {
		this.defectDataLevel = defectDataLevel;
	}

	public String getDefectTypeDetail() {
		return defectTypeDetail;
	}

	public void setDefectTypeDetail(String defectTypeDetail) {
		this.defectTypeDetail = defectTypeDetail;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getTypicalDefect() {
		return typicalDefect;
	}

	public void setTypicalDefect(Integer typicalDefect) {
		this.typicalDefect = typicalDefect;
	}

	public String getEquName() {
		return equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	public String getEquPosition() {
		return equPosition;
	}

	public void setEquPosition(String equPosition) {
		this.equPosition = equPosition;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getDefectTypeDetailName() {
		return defectTypeDetailName;
	}

	public void setDefectTypeDetailName(String defectTypeDetailName) {
		this.defectTypeDetailName = defectTypeDetailName;
	}

	public String getDefectLevelName() {
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

	public String getEquNameName() {
		return equNameName;
	}

	public void setEquNameName(String equNameName) {
		this.equNameName = equNameName;
	}

	public String getDefectDataLevelName() {
		return defectDataLevelName;
	}

	public void setDefectDataLevelName(String defectDataLevelName) {
		this.defectDataLevelName = defectDataLevelName;
	}

	public String getTestingPerson() {
		return testingPerson;
	}

	public void setTestingPerson(String testingPerson) {
		this.testingPerson = testingPerson;
	}

	public String getAnalyticalPerson() {
		return analyticalPerson;
	}

	public void setAnalyticalPerson(String analyticalPerson) {
		this.analyticalPerson = analyticalPerson;
	}

	public Date getAnalyticalDate() {
		return analyticalDate;
	}

	public void setAnalyticalDate(Date analyticalDate) {
		this.analyticalDate = analyticalDate;
	}

	public Double getDefectValue() {
		return defectValue;
	}

	public void setDefectValue(Double defectValue) {
		this.defectValue = defectValue;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getArcingTime() {
		return arcingTime;
	}

	public void setArcingTime(String arcingTime) {
		this.arcingTime = arcingTime;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public String getConfirmPerson() {
		if (StringUtil.isNotBlank(this.confirmPerson)) {
			return this.confirmPerson.substring(
					this.confirmPerson.lastIndexOf("_") + 1,
					this.confirmPerson.length());
		}
		return confirmPerson;
	}

	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getCancelPerson() {
		if (StringUtil.isNotBlank(this.cancelPerson)) {
			return this.cancelPerson.substring(
					this.cancelPerson.lastIndexOf("_") + 1,
					this.cancelPerson.length());
		}
		return cancelPerson;
	}

	public void setCancelPerson(String cancelPerson) {
		this.cancelPerson = cancelPerson;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Integer getCheckTimeouted() {
		return checkTimeouted;
	}

	public void setCheckTimeouted(Integer checkTimeouted) {
		this.checkTimeouted = checkTimeouted;
	}

	public Integer getIsTimeouted() {
		return isTimeouted;
	}

	public void setIsTimeouted(Integer isTimeouted) {
		this.isTimeouted = isTimeouted;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
