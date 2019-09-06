package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.common.util.string.StringUtil;

public class DefectHandleInfo extends BaseEntity<DefectHandleInfo> {

	protected String id;
	protected String confirmPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date confirmDate;
	protected String cancelPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date cancelDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date planCompleteDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date completeDate;
	protected Integer isReformed;
	protected Integer isCanceled;
	protected Integer isConfirmed;
	protected Integer isTimeouted;
	protected Integer isNeedreform;
	protected Integer isDelayed;
	protected Integer confirmStatus;
	protected Integer delayCount;
	protected Integer checkTimeouted;
	protected String confirmRemark;
	protected String auditStatus;
	
	private String comment;

	private Boolean fg = true;
	@JsonIgnore
	public Boolean getFg() {
		return fg;
	}

	public void setFg(Boolean fg) {
		this.fg = fg;
	}

	public DefectHandleInfo() {
	}

	public DefectHandleInfo(String confirmPerson, Date confirmDate,
			Integer isConfirmed, String auditStatus, Date planCompleteDate,Integer confirmStatus) {
		super();
		this.confirmPerson = confirmPerson;
		this.confirmDate = confirmDate;
		this.planCompleteDate = planCompleteDate;
		this.isConfirmed = isConfirmed;
		this.auditStatus = auditStatus;
		this.confirmStatus = confirmStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConfirmPerson() {
		if (fg && StringUtil.isNotBlank(this.confirmPerson)) {
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

	public Date getPlanCompleteDate() {
		return planCompleteDate;
	}

	public void setPlanCompleteDate(Date planCompleteDate) {
		this.planCompleteDate = planCompleteDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

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

	public Integer getDelayCount() {
		return delayCount;
	}

	public void setDelayCount(Integer delayCount) {
		this.delayCount = delayCount;
	}

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Integer getCheckTimeouted() {
		return checkTimeouted;
	}

	public void setCheckTimeouted(Integer checkTimeouted) {
		this.checkTimeouted = checkTimeouted;
	}

}
