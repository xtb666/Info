package com.togest.domain;

import java.util.Date;


public class Defect2C  extends Defect<Defect2C> {

	protected String photo;
	
	protected String originalPhoto;

	protected String testingPerson;

	protected String analyticalPerson;
	
	protected Date analyticalDate;
	

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

}
