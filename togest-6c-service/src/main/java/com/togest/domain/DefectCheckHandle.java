package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DefectCheckHandle extends BaseEntity<DefectCheckHandle> {
	
	protected String id;
	
	protected String reviewPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date reviewDate;
	
	protected String reviewConclusion;
	
	protected String reviewPhoto;
	//静态测量值
	protected String staticMeasurement;
	protected String lastStaticMeasurement;
	//轨面标准值
	protected String railStandardValue;
	protected String lastRailStandardValue;
	//侧面限界值
	protected String sideCriticalValue;
	protected String lastSideCriticalValue;
	//外规超高
	protected String outerRailHeight;
	protected String lastOuterRailHeight;
	
	protected String reviewValue;
	
	protected Boolean isReform;
	
	protected String remark;
	
	protected String informant;
	
	protected String pillarPhoto;
	
	protected String defectDeviceAttributes;

	
	public String getLastStaticMeasurement() {
		return lastStaticMeasurement;
	}

	public void setLastStaticMeasurement(String lastStaticMeasurement) {
		this.lastStaticMeasurement = lastStaticMeasurement;
	}

	public String getRailStandardValue() {
		return railStandardValue;
	}

	public void setRailStandardValue(String railStandardValue) {
		this.railStandardValue = railStandardValue;
	}

	public String getSideCriticalValue() {
		return sideCriticalValue;
	}

	public void setSideCriticalValue(String sideCriticalValue) {
		this.sideCriticalValue = sideCriticalValue;
	}

	public String getDefectDeviceAttributes() {
		return defectDeviceAttributes;
	}

	public void setDefectDeviceAttributes(String defectDeviceAttributes) {
		this.defectDeviceAttributes = defectDeviceAttributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReviewPerson() {
		return reviewPerson;
	}

	public void setReviewPerson(String reviewPerson) {
		this.reviewPerson = reviewPerson;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewConclusion() {
		return reviewConclusion;
	}

	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}

	public String getReviewPhoto() {
		return reviewPhoto;
	}

	public void setReviewPhoto(String reviewPhoto) {
		this.reviewPhoto = reviewPhoto;
	}

	public String getReviewValue() {
		return reviewValue;
	}

	public void setReviewValue(String reviewValue) {
		this.reviewValue = reviewValue;
	}

	public Boolean getIsReform() {
		return isReform;
	}

	public void setIsReform(Boolean isReform) {
		this.isReform = isReform;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getPillarPhoto() {
		return pillarPhoto;
	}

	public void setPillarPhoto(String pillarPhoto) {
		this.pillarPhoto = pillarPhoto;
	}

	public String getStaticMeasurement() {
		return staticMeasurement;
	}

	public void setStaticMeasurement(String staticMeasurement) {
		this.staticMeasurement = staticMeasurement;
	}

	public String getLastRailStandardValue() {
		return lastRailStandardValue;
	}

	public void setLastRailStandardValue(String lastRailStandardValue) {
		this.lastRailStandardValue = lastRailStandardValue;
	}

	public String getLastSideCriticalValue() {
		return lastSideCriticalValue;
	}

	public void setLastSideCriticalValue(String lastSideCriticalValue) {
		this.lastSideCriticalValue = lastSideCriticalValue;
	}

	public String getOuterRailHeight() {
		return outerRailHeight;
	}

	public void setOuterRailHeight(String outerRailHeight) {
		this.outerRailHeight = outerRailHeight;
	}

	public String getLastOuterRailHeight() {
		return lastOuterRailHeight;
	}

	public void setLastOuterRailHeight(String lastOuterRailHeight) {
		this.lastOuterRailHeight = lastOuterRailHeight;
	}

}
