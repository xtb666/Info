package com.togest.request;

import java.io.Serializable;

public class DefectRepeatRequest implements Serializable {

	private String id;
	private String checkDateYear;
	private String lineId;
	private String direction;
	private String defectType;
	private String defectTypeDetail;
	private String defectLevel;
	private String systemId;
	private String pillarId;

	// 判重扩充
	private String equName;// 2C判重字段
	private String psaId;
	private String tunnelId;
	private Double defectGlb;

	private String defectDataCategory;
	private String defectDataLevel;

	private Integer isShow;

	public Double getDefectGlb() {
		return defectGlb;
	}

	public void setDefectGlb(Double defectGlb) {
		this.defectGlb = defectGlb;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckDateYear() {
		return checkDateYear;
	}

	public void setCheckDateYear(String checkDateYear) {
		this.checkDateYear = checkDateYear;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDefectType() {
		return defectType;
	}

	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}

	public String getDefectTypeDetail() {
		return defectTypeDetail;
	}

	public void setDefectTypeDetail(String defectTypeDetail) {
		this.defectTypeDetail = defectTypeDetail;
	}

	public String getDefectLevel() {
		return defectLevel;
	}

	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getPillarId() {
		return pillarId;
	}

	public void setPillarId(String pillarId) {
		this.pillarId = pillarId;
	}

	public String getEquName() {
		return equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	public String getPsaId() {
		return psaId;
	}

	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}

	public String getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getDefectDataCategory() {
		return defectDataCategory;
	}

	public void setDefectDataCategory(String defectDataCategory) {
		this.defectDataCategory = defectDataCategory;
	}

	public String getDefectDataLevel() {
		return defectDataLevel;
	}

	public void setDefectDataLevel(String defectDataLevel) {
		this.defectDataLevel = defectDataLevel;
	}

}
