package com.togest.response.statistics;

import java.io.Serializable;

public class StatisticRange implements Serializable {
	
	protected Double startKm;
	protected Double endKm;
	protected Integer num;
	
	public Double getStartKm() {
		return startKm;
	}
	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}
	public Double getEndKm() {
		return endKm;
	}
	public void setEndKm(Double endKm) {
		this.endKm = endKm;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}
