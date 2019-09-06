package com.togest.domain.dto;

import com.togest.domain.BdTLine;

public class BdTLineDTO extends BdTLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lineName; //线路名称
    private String pavilionName; //变电所名称
    private String psPdName; //供电臂名称
    
    public BdTLineDTO() {
    	
	}

	public BdTLineDTO(String lineId, String pavilionId, String psPdId) {
		this.lineId = lineId;
		this.pavilionId = pavilionId;
		this.psPdId = psPdId;
	}
	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
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
