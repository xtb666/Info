package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CPicIndex<T> extends BaseEntity<T> {

	protected String id;

	protected String station;

	protected String infoId;

	protected String pillarNum;

	protected Double km;

	protected String speed;

	protected String indexStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date defectDate;
	protected Integer sort;
	protected short delFlag = 0;
	@JsonIgnore
	protected String createIp;
	@JsonIgnore
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getPillarNum() {
		return pillarNum;
	}

	public void setPillarNum(String pillarNum) {
		this.pillarNum = pillarNum;
	}

	public Double getKm() {
		return km;
	}

	public void setKm(Double km) {
		this.km = km;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getIndexStatus() {
		return indexStatus;
	}

	public void setIndexStatus(String indexStatus) {
		this.indexStatus = indexStatus;
	}

	public Date getDefectDate() {
		return defectDate;
	}

	public void setDefectDate(Date defectDate) {
		this.defectDate = defectDate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

}
