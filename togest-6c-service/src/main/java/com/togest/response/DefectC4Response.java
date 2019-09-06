package com.togest.response;

import java.util.Date;

import com.togest.domain.Defect4CDTO;

public class DefectC4Response extends DefectPackageResponse<Defect4CDTO> {


	protected Integer height;
	
	protected Integer stagger;
	
	protected Integer heightDiff;
	
	protected Integer distance;
	
	protected String photo;
	
	protected String originalPhoto;
	
	protected String analyticalPerson;
	
	protected Date analyticalDate;
	
	private String testingPerson;
	
	protected String note;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getStagger() {
		return stagger;
	}

	public void setStagger(Integer stagger) {
		this.stagger = stagger;
	}

	public Integer getHeightDiff() {
		return heightDiff;
	}

	public void setHeightDiff(Integer heightDiff) {
		this.heightDiff = heightDiff;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAnalyticalPerson() {
		return analyticalPerson;
	}

	public void setAnalyticalPerson(String analyticalPerson) {
		this.analyticalPerson = analyticalPerson;
	}

	public Date getAnalyticalDate() {
		return analyticalDate;
	}

	public void setAnalyticalDate(Date analyticalDate) {
		this.analyticalDate = analyticalDate;
	}

	public String getOriginalPhoto() {
		return originalPhoto;
	}

	public void setOriginalPhoto(String originalPhoto) {
		this.originalPhoto = originalPhoto;
	}

	public String getTestingPerson() {
		return testingPerson;
	}

	public void setTestingPerson(String testingPerson) {
		this.testingPerson = testingPerson;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
