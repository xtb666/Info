package com.togest.response.statistics;

import java.io.Serializable;

public class LineStatistics implements Serializable{
	
	protected String lineName;
	protected Integer total;
	protected Integer processing;
	protected String systemId;
	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getProcessing() {
		return processing;
	}
	public void setProcessing(Integer processing) {
		this.processing = processing;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
