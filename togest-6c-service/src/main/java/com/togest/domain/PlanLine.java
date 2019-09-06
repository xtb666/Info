package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.dict.annotation.DictMark;

public class PlanLine<T> extends BaseEntity<T> {

	protected String id;
	protected String lineId;
	@DictMark(dictName = "direction", itemName = "directionName")
	protected String direction;
	protected Double startKm;
	protected Double endKm;
	protected Double detectMileage;
	protected String startStation;
	protected String endStation;
	protected String planId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Double getDetectMileage() {
		return detectMileage;
	}
	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
}
