package com.togest.request;

import java.io.Serializable;


public class FileForm implements Serializable{

	private String originalName;
	private byte[] data;

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	

}