package com.togest.response;

import com.togest.domain.Defect1CDTO;

public class DefectC1Form extends DefectForm<Defect1CDTO> {

	protected Double defectValue;
	protected Integer points;
	protected String photo;
	public Double getDefectValue() {
		return defectValue;
	}
	public void setDefectValue(Double defectValue) {
		this.defectValue = defectValue;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
