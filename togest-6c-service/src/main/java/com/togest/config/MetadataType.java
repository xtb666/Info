package com.togest.config;

public enum MetadataType {

	MonthReport("1"), WeekReport("2"), CheckData("3"), DefectData("4");
	private final String status;

	MetadataType(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
