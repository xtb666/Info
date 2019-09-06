package com.togest.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DefectAddField {

	private String id;
	// 检测人员
	private String testingPerson;
	// 分析人
	private String analyticalPerson;
	// 分析日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date analyticalDate;
	// 缺陷值
	private Double defectValue;
	// 扣分数
	private Integer points;
	// 报警状态
	private String alarmStatus;
	// 燃弧时间
	private String arcingTime;
	// 复核日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reviewDate;
	// 整改日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date handleDate;
	// 确认人
	private String confirmPerson;
	// 确认日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date confirmDate;
	// 销号人
	private String cancelPerson;
	// 销号时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date cancelDate;
	// 复核超时
	private Integer checkTimeouted;
	// 下步办理人
	private String taskUser;
	// 流程实例ID
	private String processInstanceId;
	// 流程业务ID
	private String taskId;
	// 驳回意见
	private String comment;
	// 是否已超时
	private Integer isTimeouted;
	// 重复次数
	private Integer count1;
	private Integer repeatCount;
	// 审核状态
	private String auditStatus;
	// 复核结论
	private String reviewConclusion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTaskUser() {
		return taskUser;
	}

	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getIsTimeouted() {
		return isTimeouted;
	}

	public void setIsTimeouted(Integer isTimeouted) {
		this.isTimeouted = isTimeouted;
	}

	public Integer getCount1() {
		return count1;
	}

	public void setCount1(Integer count1) {
		this.count1 = count1;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getReviewConclusion() {
		return reviewConclusion;
	}

	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}

}
