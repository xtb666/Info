package com.togest.response.statistics;

import java.io.Serializable;

public class DefectStatusStatistics implements Serializable {
	private String status ;
	private String number ;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
