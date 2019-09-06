package com.togest.util;

public enum DeptType {

	Gdc("1"), Section("2"), Shop("3"), Area("4");
	private final String status;

	DeptType(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
