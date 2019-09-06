package com.togest.domain;

public class DataDrawingDTO extends DataDrawing<DataDrawingDTO>{

	private String deptName;
	private String fileName;
	private String assortmentName;
	private String pavilionName;
	private String psaName;
	private String lineName;

	public String getPavilionName() {
		return pavilionName;
	}

	public void setPavilionName(String pavilionName) {
		this.pavilionName = pavilionName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getPsaName() {
		return psaName;
	}

	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	
	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
}
