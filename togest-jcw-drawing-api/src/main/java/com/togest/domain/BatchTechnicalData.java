package com.togest.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BatchTechnicalData implements Serializable {

	private String name;
	private Integer startNum;
	private String assortmentId;
	private String fileIds;
	private String type;
	private String version;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uploadDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public String getAssortmentId() {
		return assortmentId;
	}

	public void setAssortmentId(String assortmentId) {
		this.assortmentId = assortmentId;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

}
