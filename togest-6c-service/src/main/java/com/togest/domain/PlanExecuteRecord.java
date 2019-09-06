package com.togest.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.dict.annotation.DictMark;

public class PlanExecuteRecord implements Serializable {
	protected String id;

	protected String planExecuteId;

	protected String deptId;

	protected String patcher;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date multiplicationDate;

	protected String startStation;

	protected String endStation;

	protected String implementation;
	@DictMark(dictName = "plan_status", primaryKey = "code")
	protected String recordStatus;

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

	protected String remark;
	
	protected String processInstanceId;
	protected String formKey;
	protected String taskName;
	protected String taskId;
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanExecuteId() {
		return planExecuteId;
	}

	public void setPlanExecuteId(String planExecuteId) {
		this.planExecuteId = planExecuteId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getPatcher() {
		return patcher;
	}

	public void setPatcher(String patcher) {
		this.patcher = patcher;
	}

	public Date getMultiplicationDate() {
		return multiplicationDate;
	}

	public void setMultiplicationDate(Date multiplicationDate) {
		this.multiplicationDate = multiplicationDate;
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

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}