package com.togest.domain;

import java.util.Date;

public class Defect3C  extends Defect<Defect3C> {

	protected String seat;
	
	protected Integer netT;
	
	protected Integer envT;
	
	protected Integer height;

	private String arcingTime;
	
	protected Integer stagger;
	protected Integer defectValue;
	
	protected String visibleLightPhoto;
	
	protected String infraRedPhoto;
	
	protected String tPhoto;
	protected String panoramaPhoto;
	protected String alarmStatus;
	
	public Integer getDefectValue() {
		return defectValue;
	}

	public void setDefectValue(Integer defectValue) {
		this.defectValue = defectValue;
	}

	public String getPanoramaPhoto() {
		return panoramaPhoto;
	}

	public void setPanoramaPhoto(String panoramaPhoto) {
		this.panoramaPhoto = panoramaPhoto;
	}

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public Integer getNetT() {
		return netT;
	}

	public void setNetT(Integer netT) {
		this.netT = netT;
	}

	public Integer getEnvT() {
		return envT;
	}

	public void setEnvT(Integer envT) {
		this.envT = envT;
	}

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

	public String getVisibleLightPhoto() {
		return visibleLightPhoto;
	}

	public void setVisibleLightPhoto(String visibleLightPhoto) {
		this.visibleLightPhoto = visibleLightPhoto;
	}

	public String getInfraRedPhoto() {
		return infraRedPhoto;
	}

	public void setInfraRedPhoto(String infraRedPhoto) {
		this.infraRedPhoto = infraRedPhoto;
	}

	public String gettPhoto() {
		return tPhoto;
	}

	public void settPhoto(String tPhoto) {
		this.tPhoto = tPhoto;
	}

	public String getArcingTime() {
		return arcingTime;
	}

	public void setArcingTime(String arcingTime) {
		this.arcingTime = arcingTime;
	}
	
	
}
