package com.togest.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CheckSystemDTO extends CheckSystem<CheckSystemDTO>{
	
	private String paramIds;
	
	private String defectTypeIds;
	
	private List<CheckParamDTO> params;
	
	private List<DefectTypeDTO> defectTypes;
	
	@JsonIgnore
	public String getParamIds() {
		return paramIds;
	}

	public void setParamIds(String paramIds) {
		this.paramIds = paramIds;
	}
	@JsonIgnore
	public String getDefectTypeIds() {
		return defectTypeIds;
	}

	public void setDefectTypeIds(String defectTypeIds) {
		this.defectTypeIds = defectTypeIds;
	}

	public List<CheckParamDTO> getParams() {
		return params;
	}

	public void setParams(List<CheckParamDTO> params) {
		this.params = params;
	}

	public List<DefectTypeDTO> getDefectTypes() {
		return defectTypes;
	}

	public void setDefectTypes(List<DefectTypeDTO> defectTypes) {
		this.defectTypes = defectTypes;
	}

}
