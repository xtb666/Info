package com.togest.response.statistics;

import java.io.Serializable;

public class DetectMileageStatistics implements Serializable {

	protected String cName;
	protected Double detectMileage;
	protected String systemId;
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public Double getDetectMileage() {
		return detectMileage;
	}
	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}
}
