package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.dict.annotation.DictMark;

public class C1Info extends CInfo {
	protected String id;
	protected String infoStatus;
	protected Double km;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}


	public Double getKm() {
		return km;
	}

	public void setKm(Double km) {
		this.km = km;
	}
	
}
