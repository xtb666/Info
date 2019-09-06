package com.togest.domain;

public class C4PicIndex<T> extends CPicIndex<T> {
	private String cameraNo;
	private String path;

	public String getCameraNo() {
		return cameraNo;
	}

	public void setCameraNo(String cameraNo) {
		this.cameraNo = cameraNo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
