package com.togest.domain;

import java.io.Serializable;

import com.togest.dict.annotation.DictMark;

public class PillarInfoSelectorV2DTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Double startKm;
	private String deptId;
	private String deptName;
	private String lineId;
	private String lineName;
	private String psaId;
	private String psaName;
	@DictMark(dictName = "direction", itemName = "directionName")
	private String direction;
	private String directionName;
	private String tunnelId; //
	private String tunnelName;
	private String trackId;
	private String trackName;
	private String speed;
	//上月检测导高
	private Integer upMonthAnchorPoint;
	//导高标准值
    protected Integer standarLineHeight;
    //年度复核轨面高差
    protected Integer yearReviewRailHeightValue;
    //年度复核值
    protected Integer yearReviewValue;
    //上限值
    protected Integer standarLineHeightUp;
    //下限值
    protected Integer standarLineHeightDown;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getStartKm() {
		return startKm;
	}
	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getPsaId() {
		return psaId;
	}
	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public String getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}
	public String getTunnelName() {
		return tunnelName;
	}
	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}
	public String getTrackId() {
		return trackId;
	}
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public Integer getUpMonthAnchorPoint() {
		return upMonthAnchorPoint;
	}
	public void setUpMonthAnchorPoint(Integer upMonthAnchorPoint) {
		this.upMonthAnchorPoint = upMonthAnchorPoint;
	}
	public Integer getStandarLineHeight() {
		return standarLineHeight;
	}
	public void setStandarLineHeight(Integer standarLineHeight) {
		this.standarLineHeight = standarLineHeight;
	}
	public Integer getYearReviewRailHeightValue() {
		return yearReviewRailHeightValue;
	}
	public void setYearReviewRailHeightValue(Integer yearReviewRailHeightValue) {
		this.yearReviewRailHeightValue = yearReviewRailHeightValue;
	}
	public Integer getYearReviewValue() {
		return yearReviewValue;
	}
	public void setYearReviewValue(Integer yearReviewValue) {
		this.yearReviewValue = yearReviewValue;
	}
	public Integer getStandarLineHeightUp() {
		return standarLineHeightUp;
	}
	public void setStandarLineHeightUp(Integer standarLineHeightUp) {
		this.standarLineHeightUp = standarLineHeightUp;
	}
	public Integer getStandarLineHeightDown() {
		return standarLineHeightDown;
	}
	public void setStandarLineHeightDown(Integer standarLineHeightDown) {
		this.standarLineHeightDown = standarLineHeightDown;
	}
}
