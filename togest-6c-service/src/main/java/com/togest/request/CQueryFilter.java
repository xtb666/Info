package com.togest.request;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.SimplePage;
import com.togest.util.Data;

public class CQueryFilter extends Data {
	protected String id;
	protected String defectAssortmentId;
	private String defectDataCategory;
	private String defectDataLevel;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endCheckDate;
	@DateTimeFormat(pattern = "yyyy")
	protected Date checkDateYear;
	protected String lineId;
	protected String workShopId;
	protected String workAreaId;
	protected String psaId;
	protected String direction;
	protected String speedType;
	protected String pillarName;
	protected String pillarId;
	protected String tunnelId;
	protected String trackNo;
	protected String typicalDefect;
	protected String defectType;
	protected String defectTypeDetail;
	protected String defectLevel;
	protected Double defectGlb;
	protected String defectStatus;
	protected String systemId;
	protected Double startKm;
	protected Double endKm;
	protected boolean needCanceledIsNull = true;
	protected Integer isReformed;
	protected Integer isCanceled;
	protected Integer isConfirmed = 1;
	protected Integer isTimeouted;
	protected Integer isNeedreform;
	protected Integer isDelayed;
	protected boolean needlineId = true;
	protected String infoId;
	protected Integer kilometre;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date lastCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date lastBeginCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date lastEndCheckDate;
	protected Integer manualFlag;
	protected Integer delFlag;

	protected Integer count;
	protected Double glbDistance = 0.02;
	protected String planId;
	protected String defectRepeatCountId;
	protected boolean archiveFlag;
	protected String alarmStatus;
	protected String type;
	protected String description;
	protected String equPosition;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginReCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endReCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginReformDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endReformDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginCancelDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endCancelDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date confirmDate;
	
	protected String reviewConclusion;
	
	protected String equName;
	
	protected Integer isShow;
	
	
/*	
	if (StringUtil.isNotBlank(this.getDefectType())) {
		return Arrays.asList(this.getDefectType().split(","));
	}*/
	
	public String getEquPosition() {
		return equPosition;
	}

	public void setEquPosition(String equPosition) {
		this.equPosition = equPosition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isArchiveFlag() {
		return archiveFlag;
	}

	public String getDefectDataLevel() {
		return defectDataLevel;
	}

	public void setDefectDataLevel(String defectDataLevel) {
		this.defectDataLevel = defectDataLevel;
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

	public Date getBeginReCheckDate() {
		return beginReCheckDate;
	}

	public void setBeginReCheckDate(Date beginReCheckDate) {
		this.beginReCheckDate = beginReCheckDate;
	}

	public Date getEndReCheckDate() {
		return endReCheckDate;
	}

	public void setEndReCheckDate(Date endReCheckDate) {
		this.endReCheckDate = endReCheckDate;
	}

	public Date getBeginReformDate() {
		return beginReformDate;
	}

	public void setBeginReformDate(Date beginReformDate) {
		this.beginReformDate = beginReformDate;
	}

	public Date getEndReformDate() {
		return endReformDate;
	}

	public void setEndReformDate(Date endReformDate) {
		this.endReformDate = endReformDate;
	}

	public Date getBeginCancelDate() {
		return beginCancelDate;
	}

	public void setBeginCancelDate(Date beginCancelDate) {
		this.beginCancelDate = beginCancelDate;
	}

	public Date getEndCancelDate() {
		return endCancelDate;
	}

	public void setEndCancelDate(Date endCancelDate) {
		this.endCancelDate = endCancelDate;
	}

	public String getReviewConclusion() {
		return reviewConclusion;
	}

	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}

	public void setArchiveFlag(boolean archiveFlag) {
		this.archiveFlag = archiveFlag;
	}

	public Date getCheckDateYear() {
		return checkDateYear;
	}

	public void setCheckDateYear(Date checkDateYear) {
		this.checkDateYear = checkDateYear;
	}

	public Double getGlbDistance() {
		return glbDistance;
	}

	public void setGlbDistance(Double glbDistance) {
		this.glbDistance = glbDistance;
	}

	public String getDefectRepeatCountId() {
		return defectRepeatCountId;
	}

	public void setDefectRepeatCountId(String defectRepeatCountId) {
		this.defectRepeatCountId = defectRepeatCountId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@JsonIgnore
	protected Page page;
	@JsonIgnore
	private SimplePage simplePage;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getKilometre() {
		return kilometre;
	}

	public void setKilometre(Integer kilometre) {
		this.kilometre = kilometre;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public boolean isNeedlineId() {
		return needlineId;
	}

	public void setNeedlineId(boolean needlineId) {
		this.needlineId = needlineId;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	protected String trainId;
	
	public Integer getIsReformed() {
		return isReformed;
	}

	public void setIsReformed(Integer isReformed) {
		this.isReformed = isReformed;
	}

	public Integer getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(Integer isCanceled) {
		this.isCanceled = isCanceled;
	}

	public Integer getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public Integer getIsTimeouted() {
		return isTimeouted;
	}

	public void setIsTimeouted(Integer isTimeouted) {
		this.isTimeouted = isTimeouted;
	}

	public Integer getIsNeedreform() {
		return isNeedreform;
	}

	public void setIsNeedreform(Integer isNeedreform) {
		this.isNeedreform = isNeedreform;
	}

	public Integer getIsDelayed() {
		return isDelayed;
	}

	public void setIsDelayed(Integer isDelayed) {
		this.isDelayed = isDelayed;
	}

	@JsonIgnore
	private List<String> defectStatusList;

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getEndCheckDate() {
		return endCheckDate;
	}

	public void setEndCheckDate(Date endCheckDate) {
		this.endCheckDate = endCheckDate;
	}

	public Date getBeginCheckDate() {
		return beginCheckDate;
	}

	public void setBeginCheckDate(Date beginCheckDate) {
		this.beginCheckDate = beginCheckDate;
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

	public String getPillarName() {
		return pillarName;
	}

	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
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

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public List<String> getDefectStatusList() {
		if (StringUtil.isNotBlank(this.getDefectStatus())) {
			return Arrays.asList(this.getDefectStatus().split(","));
		}
		return defectStatusList;
	}

	public void setDefectStatusList(List<String> defectStatusList) {
		this.defectStatusList = defectStatusList;
	}

	public String getPillarId() {
		return pillarId;
	}

	public void setPillarId(String pillarId) {
		this.pillarId = pillarId;
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

	public boolean isNeedCanceledIsNull() {
		return needCanceledIsNull;
	}

	public void setNeedCanceledIsNull(boolean needCanceledIsNull) {
		this.needCanceledIsNull = needCanceledIsNull;
	}

	public String getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(String workAreaId) {
		if(StringUtil.isNotBlank(workAreaId)){
			setDid(workAreaId);
		}
		this.workAreaId = workAreaId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Date getLastCheckDate() {
		return lastCheckDate;
	}

	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}

	public Date getLastBeginCheckDate() {
		return lastBeginCheckDate;
	}

	public void setLastBeginCheckDate(Date lastBeginCheckDate) {
		this.lastBeginCheckDate = lastBeginCheckDate;
	}

	public Date getLastEndCheckDate() {
		return lastEndCheckDate;
	}

	public void setLastEndCheckDate(Date lastEndCheckDate) {
		this.lastEndCheckDate = lastEndCheckDate;
	}

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getTypicalDefect() {
		return typicalDefect;
	}

	public void setTypicalDefect(String typicalDefect) {
		this.typicalDefect = typicalDefect;
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

	public SimplePage getSimplePage() {
		return simplePage;
	}

	public void setSimplePage(SimplePage simplePage) {
		this.simplePage = simplePage;
	}

	public Integer getManualFlag() {
		return manualFlag;
	}

	public void setManualFlag(Integer manualFlag) {
		this.manualFlag = manualFlag;
	}

}
