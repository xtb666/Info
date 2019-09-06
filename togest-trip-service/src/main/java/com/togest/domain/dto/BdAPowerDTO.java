package com.togest.domain.dto;

import com.togest.domain.BdAPower;

public class BdAPowerDTO extends BdAPower {

	private static final long serialVersionUID = 1L;

	private String lineName; //线路名称
	private String fileName; //附件名称
    private String pavilionName; //变电所名称
    private String psPdName; //供电臂名称
    
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
	public String getPavilionName() {
		return pavilionName;
	}
	public void setPavilionName(String pavilionName) {
		this.pavilionName = pavilionName;
	}
	public String getPsPdName() {
		return psPdName;
	}
	public void setPsPdName(String psPdName) {
		this.psPdName = psPdName;
	}
}
