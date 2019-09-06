package com.togest.client.response;

import java.io.Serializable;

public class DetectResult implements Serializable {

	protected String sectionId;
	protected Double detectMileage;

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Double getDetectMileage() {
		return detectMileage;
	}

	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}
}
