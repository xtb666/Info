package com.togest.response.statistics;

import java.io.Serializable;
import java.util.List;

public class DefectDistributionYearOnYearData implements Serializable{

	
	protected String numNow;
	// 环比 数据
	protected String numLastRR;
	// 同比 数据
	protected String numLastYY;
	
	protected List<DefectDistributionTrend> ddbList;
	
	public List<DefectDistributionTrend> getDdbList() {
		return ddbList;
	}
	public void setDdbList(List<DefectDistributionTrend> ddbList) {
		this.ddbList = ddbList;
	}
	public String getNumLastRR() {
		return numLastRR;
	}
	public void setNumLastRR(String numLastRR) {
		this.numLastRR = numLastRR;
	}
	public String getNumLastYY() {
		return numLastYY;
	}
	public void setNumLastYY(String numLastYY) {
		this.numLastYY = numLastYY;
	}
	public String getNumNow() {
		return numNow;
	}
	public void setNumNow(String numNow) {
		this.numNow = numNow;
	}
	
}
