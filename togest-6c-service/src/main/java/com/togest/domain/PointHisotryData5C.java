package com.togest.domain;

import java.util.Date;

public class PointHisotryData5C<T> extends BaseEntity<T> {
	protected String id;

	protected Date checkTime;

	protected String pantographPhoto;

	protected String skatePhoto;

	protected String locomotivePhoto;

	protected String pointId;
	
	protected String cameraNo;
	
    protected String trainNo;
    
    protected String primitiveDataFile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getPantographPhoto() {
		return pantographPhoto;
	}

	public void setPantographPhoto(String pantographPhoto) {
		this.pantographPhoto = pantographPhoto;
	}

	public String getSkatePhoto() {
		return skatePhoto;
	}

	public void setSkatePhoto(String skatePhoto) {
		this.skatePhoto = skatePhoto;
	}

	public String getLocomotivePhoto() {
		return locomotivePhoto;
	}

	public void setLocomotivePhoto(String locomotivePhoto) {
		this.locomotivePhoto = locomotivePhoto;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getCameraNo() {
		return cameraNo;
	}

	public void setCameraNo(String cameraNo) {
		this.cameraNo = cameraNo;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getPrimitiveDataFile() {
		return primitiveDataFile;
	}

	public void setPrimitiveDataFile(String primitiveDataFile) {
		this.primitiveDataFile = primitiveDataFile;
	}

}