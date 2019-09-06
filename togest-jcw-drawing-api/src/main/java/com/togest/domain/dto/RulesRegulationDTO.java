package com.togest.domain.dto;

import com.togest.domain.RulesRegulation;

public class RulesRegulationDTO extends RulesRegulation<RulesRegulationDTO>{

	private String fileName;
	private String assortmentName;
	private String pavilionName;
	private String psaName;
	
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
	public String getPavilionName() {
		return pavilionName;
	}
	public void setPavilionName(String pavilionName) {
		this.pavilionName = pavilionName;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
}
