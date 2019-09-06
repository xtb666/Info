package com.togest.domain.dto;

import com.togest.domain.BdTripInformation;

public class BdTripInformationDTO extends BdTripInformation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lineName; //线路名称
    private String pavilionName; //变电所名称
    private String psPdName; //供电臂名称
    private String faultTypeName; //故障类型
    private String localWeatherName; //当地天气
    private String coincidenceGateName; //成功 失败
    private String protectName; //保护名称
    private String flineInfluenceName; //F线重合成功后了解动车组是否有异常
    private String flineInfoName; //F线重合成功后影响行车信息
    private String directionName; //行别
    
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getPavilionName() {
		return pavilionName;
	}
	public void setPavilionName(String pavilionName) {
		this.pavilionName = pavilionName;
	}
	public String getPsPdName() {
		return psPdName;
	}
	public void setPsPdName(String psPdName) {
		this.psPdName = psPdName;
	}
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public String getFaultTypeName() {
		return faultTypeName;
	}
	public void setFaultTypeName(String faultTypeName) {
		this.faultTypeName = faultTypeName;
	}
	public String getLocalWeatherName() {
		return localWeatherName;
	}
	public void setLocalWeatherName(String localWeatherName) {
		this.localWeatherName = localWeatherName;
	}
	public String getCoincidenceGateName() {
		return coincidenceGateName;
	}
	public void setCoincidenceGateName(String coincidenceGateName) {
		this.coincidenceGateName = coincidenceGateName;
	}
	public String getProtectName() {
		return protectName;
	}
	public void setProtectName(String protectName) {
		this.protectName = protectName;
	}
	public String getFlineInfluenceName() {
		return flineInfluenceName;
	}
	public void setFlineInfluenceName(String flineInfluenceName) {
		this.flineInfluenceName = flineInfluenceName;
	}
	public String getFlineInfoName() {
		return flineInfoName;
	}
	public void setFlineInfoName(String flineInfoName) {
		this.flineInfoName = flineInfoName;
	}
}
