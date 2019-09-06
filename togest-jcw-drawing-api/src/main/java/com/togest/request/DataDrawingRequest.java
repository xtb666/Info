package com.togest.request;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.domain.upgrade.DataCommonEntity;

public class DataDrawingRequest extends DataCommonEntity implements Serializable {

	private String sectionId;
	private String deptId;
	private String lineId;
	private String lineName;
	private Boolean isChild = false;
	private String authCode;
	private String name; //图纸名称
	private String pavilionId;
	private String psaId;
	private String code;
	private String type;
	private String assortmentId;
	private String assortmentName; //分类名称
	private String version;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uploadDate;
	private String remark;
	private String fileId; //文件id
	private String fileName; //文件名称

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Boolean getIsChild() {
		return isChild;
	}

	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
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

	public String getPsaId() {
		return psaId;
	}

	public void setPsaId(String psaId) {
		this.psaId = psaId;
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

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
}
