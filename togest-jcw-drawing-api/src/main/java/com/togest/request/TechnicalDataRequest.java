package com.togest.request;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.domain.upgrade.DataCommonEntity;

public class TechnicalDataRequest extends DataCommonEntity implements Serializable {

	private String name;

	private String pavilionId;

	private String code;

	private String type;

	private String assortmentId;
	
	private String sectionId;
	private String  authCode;
	private String version;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uploadDate;
	private String remark;
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPavilionId() {
		return pavilionId;
	}

	public void setPavilionId(String pavilionId) {
		this.pavilionId = pavilionId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAssortmentId() {
		return assortmentId;
	}

	public void setAssortmentId(String assortmentId) {
		this.assortmentId = assortmentId;
	}

}
