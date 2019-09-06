package com.togest.response.statistics;

import java.io.Serializable;

public class StatisticNum implements Serializable {

	protected String id;
	protected String name;
	protected Integer untreated = 0; //未处理
	protected Integer processed = 0; //已处理
	protected Integer total = 0; //全部
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getUntreated() {
		return untreated;
	}
	public void setUntreated(Integer untreated) {
		this.untreated = untreated;
	}
	public Integer getProcessed() {
		return processed;
	}
	public void setProcessed(Integer processed) {
		this.processed = processed;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
