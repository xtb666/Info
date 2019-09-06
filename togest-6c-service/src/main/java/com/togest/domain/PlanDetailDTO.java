package com.togest.domain;

import java.util.List;

public class PlanDetailDTO extends PlanDetail{

    private String lineName;

    private String directionName;
    
    private String sectionId;
    
    private List<Naming> depts;

    
	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDirectionName() {
		return directionName;
	}

	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	
	public List<Naming> getDepts() {
		return depts;
	}

	public void setDepts(List<Naming> depts) {
		this.depts = depts;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
    
}