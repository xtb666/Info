package com.togest.domain.dto;

import com.togest.domain.BdTripVideoMenu;

public class BdTripVideoMenuDTO extends BdTripVideoMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String lineName; //线路名称
	private Boolean flag; //
	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
