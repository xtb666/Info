package com.togest.domain;

public class WireHeightControlHistory extends WireHeightControl {

	private static final long serialVersionUID = 1L;
	
	//control id
	protected String controlId;
    //版本 从1开始
    protected Integer version;
    //是否最新 1最新 0历史
    protected Integer isNew;
    
	public String getControlId() {
		return controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

    
}
