package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ExpireTask extends BaseEntity<ExpireTask> {

	protected String id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date expireDate;
	
	protected String bussinessType;
	
	protected String payload;
	
	protected Integer lockVersion;
	
	protected String dataId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Integer getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Integer lockVersion) {
		this.lockVersion = lockVersion;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	
}
