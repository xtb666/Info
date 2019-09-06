package com.togest.domain.dto;

import com.togest.domain.BdSpeedLimit;

public class BdSpeedLimitDTO extends BdSpeedLimit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fileName; //附件名称

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
