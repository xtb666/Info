package com.togest.request;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class RulesRegulationRequest implements Serializable {

	private String sectionId; //段id
	private Boolean isChild = false;
	private String authCode; //权限编码
	private String name; //规章制度名称
	private String pavilionId; //
	private String psaId;
	private String code; //编码
	private String type;
	private String assortmentId; //分类id
	private String assortmentName; //分类名称
	private String version; //版本
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uploadDate; //上传时间
	private String remark; //备注
	private String fileId; //文件id
	private String fileName; //文件名称

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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

	public String getAssortmentName() {
		return assortmentName;
	}

	public void setAssortmentName(String assortmentName) {
		this.assortmentName = assortmentName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
