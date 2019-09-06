package com.togest.response.statistics;

import java.io.Serializable;
import java.util.List;

public class PlanStatisticsByMon implements Serializable {
	
	protected String name;
	protected String id;
	protected List<PlanMonData> pmdList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PlanMonData> getPmdList() {
		return pmdList;
	}
	public void setPmdList(List<PlanMonData> pmdList) {
		this.pmdList = pmdList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
