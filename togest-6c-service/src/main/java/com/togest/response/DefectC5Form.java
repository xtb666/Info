package com.togest.response;

import com.togest.domain.Defect5CDTO;

public class DefectC5Form extends DefectForm<Defect5CDTO> {

	protected String pantographPhoto;

	protected String skatePhoto;

	protected String locomotivePhoto;

	protected String pointId;
	
	protected String primitiveDataFile;

	 protected String pointName;


	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
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

	public String getPrimitiveDataFile() {
		return primitiveDataFile;
	}

	public void setPrimitiveDataFile(String primitiveDataFile) {
		this.primitiveDataFile = primitiveDataFile;
	}


}
