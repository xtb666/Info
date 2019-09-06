package com.togest.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.dict.annotation.DictMark;

public class PlanDetail implements Serializable{
    protected String id;

    protected String lineId;
    @DictMark(dictName = "direction")
    protected String direction;

    protected String patcher;

    protected String planBaseId;
    
    protected String startStation;

    protected String endStation;

    protected Double detectMileage;

    protected Short delFlag = 0;

    protected String createIp;

    protected String createBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date createDate;

    protected String updateIp;

    protected String updateBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date updateDate;

    protected String deleteIp;

    protected String deleteBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date deleteDate;

    protected String checkRegion;

    protected String startStationName;

    protected String endStationName;
    
    
    public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public String getEndStation() {
		return endStation;
	}

	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	public Double getDetectMileage() {
		return detectMileage;
	}

	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public String getPatcher() {
        return patcher;
    }

    public void setPatcher(String patcher) {
        this.patcher = patcher == null ? null : patcher.trim();
    }
    public String getPlanBaseId() {
		return planBaseId;
	}

	public void setPlanBaseId(String planBaseId) {
		this.planBaseId = planBaseId;
	}

	public Short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
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
        this.deleteIp = deleteIp == null ? null : deleteIp.trim();
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy == null ? null : deleteBy.trim();
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getCheckRegion() {
        return checkRegion;
    }

    public void setCheckRegion(String checkRegion) {
        this.checkRegion = checkRegion == null ? null : checkRegion.trim();
    }

	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
    
}