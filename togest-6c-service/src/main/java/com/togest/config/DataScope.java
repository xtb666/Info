package com.togest.config;

public enum DataScope {

	AllScope("0"), SectionScope("1"), DeptScope("2"), DeptChildScope("3");
	private final String status;

	DataScope(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
