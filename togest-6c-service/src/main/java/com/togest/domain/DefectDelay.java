package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.common.util.string.StringUtil;

public class DefectDelay extends BaseEntity<DefectDelay> {

	protected String id;

	protected String delayPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date delayDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date delayToDate;

	protected String remark;

	protected String delayStatus;
	private String defectId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDelayPerson() {
		if (StringUtil.isNotBlank(this.delayPerson)) {
			return this.delayPerson.substring(
					this.delayPerson.lastIndexOf("_") + 1,
					this.delayPerson.length());
		}
		return delayPerson;
	}

	public void setDelayPerson(String delayPerson) {
		this.delayPerson = delayPerson;
	}

	public Date getDelayDate() {
		return delayDate;
	}

	public void setDelayDate(Date delayDate) {
		this.delayDate = delayDate;
	}

	public Date getDelayToDate() {
		return delayToDate;
	}

	public void setDelayToDate(Date delayToDate) {
		this.delayToDate = delayToDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDelayStatus() {
		return delayStatus;
	}

	public void setDelayStatus(String delayStatus) {
		this.delayStatus = delayStatus;
	}

	public String getDefectId() {
		return defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

}
