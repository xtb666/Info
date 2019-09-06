package com.togest.domain.dto;

import com.togest.domain.BdLineBridge;

public class BdLineBridgeDTO extends BdLineBridge {

	private static final long serialVersionUID = 1L;
	
	private String lineName; //线路名称
	private String fileName; //附件名称

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
