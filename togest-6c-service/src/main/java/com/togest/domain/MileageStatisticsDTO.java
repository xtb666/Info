package com.togest.domain;

public class MileageStatisticsDTO extends MileageStatistics<MileageStatisticsDTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lineName;
	
	private String sectionName;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
}
