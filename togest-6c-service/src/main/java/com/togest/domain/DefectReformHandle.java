package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.common.util.string.StringUtil;

public class DefectReformHandle extends BaseEntity<DefectReformHandle> {

	protected String id;
	
	protected String handlePerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date handleDate;
	
	protected String handleScheme;
	
	protected String handlePhoto;
	
	protected String handleValue;
	
	protected String informant;
	
	protected String handleBeforePhoto;
	
	protected String pillarPhoto;
	
	protected String remark;
	
	protected Integer handleStatus;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHandlePerson() {
		if (StringUtil.isNotBlank(this.handlePerson)) {
			return this.handlePerson.substring(
					this.handlePerson.lastIndexOf("_")+1,
					this.handlePerson.length());
		}
		return handlePerson;
	}

	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public String getHandleScheme() {
		return handleScheme;
	}

	public void setHandleScheme(String handleScheme) {
		this.handleScheme = handleScheme;
	}

	public String getHandlePhoto() {
		return handlePhoto;
	}

	public void setHandlePhoto(String handlePhoto) {
		this.handlePhoto = handlePhoto;
	}

	public String getHandleValue() {
		return handleValue;
	}

	public void setHandleValue(String handleValue) {
		this.handleValue = handleValue;
	}

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getHandleBeforePhoto() {
		return handleBeforePhoto;
	}

	public void setHandleBeforePhoto(String handleBeforePhoto) {
		this.handleBeforePhoto = handleBeforePhoto;
	}

	public String getPillarPhoto() {
		return pillarPhoto;
	}

	public void setPillarPhoto(String pillarPhoto) {
		this.pillarPhoto = pillarPhoto;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}
}
