package com.togest.util;

import java.io.Serializable;
import java.util.List;

public  class Data implements Serializable{
	protected String sectionId;
	protected String did;
	protected String authCode;
	protected Boolean isChild = false;
	protected List<String> dids;
	
	public List<String> getDids() {
		return dids;
	}
	public void setDids(List<String> dids) {
		this.dids = dids;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public Boolean getIsChild() {
		return isChild;
	}
	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
	}

}