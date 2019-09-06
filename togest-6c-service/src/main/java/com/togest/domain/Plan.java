package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.dict.annotation.DictMark;

public class Plan<T> extends BaseEntity<T> {
	
	protected String id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date planDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date completeDate;
	protected String trainNumber;
	protected String trainId;
	protected String startPsaId;
	protected String endPsaId;
	protected String patcher;
	protected Double detectMileage;
	protected String deptId;
	protected String sectionId;
	@DictMark(dictName="plan_status",primaryKey="code")
	protected String planStatus;
	@DictMark(dictName="plan_audit_status",primaryKey="code")
	protected String auditStatus;
	protected String remark;
	protected String implementation;
    protected String systemId;
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
	protected Integer sort;
	protected String contacts;
	
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
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
		this.id = id;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	public String getStartPsaId() {
		return startPsaId;
	}
	public void setStartPsaId(String startPsaId) {
		this.startPsaId = startPsaId;
	}
	public String getEndPsaId() {
		return endPsaId;
	}
	public void setEndPsaId(String endPsaId) {
		this.endPsaId = endPsaId;
	}
	public String getPatcher() {
		return patcher;
	}
	public void setPatcher(String patcher) {
		this.patcher = patcher;
	}
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getImplementation() {
		return implementation;
	}
	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}
}
