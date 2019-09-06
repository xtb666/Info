package com.togest.domain;

public class TechnicalDataDTO extends TechnicalData<TechnicalDataDTO> {

	private String fileName;
	private String assortmentName;
	private String pavilionName;

	public String getPavilionName() {
		return pavilionName;
	}

	public void setPavilionName(String pavilionName) {
		this.pavilionName = pavilionName;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAssortmentName() {
		return assortmentName;
	}

	public void setAssortmentName(String assortmentName) {
		this.assortmentName = assortmentName;
	}

}
