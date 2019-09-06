package com.togest.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Check1CDTO extends Check1C<Check1CDTO> {
	
	protected String mileage;
	protected Double speed;
	protected String lineName;
	protected String directionName;
	protected String trainName;
	protected List<Check1CSectionDTO> check1CSectionList;
	protected Map<String, Check1CSectionDTO> checkSectionMap = new HashMap<>();//key为段Id
	
	public void change(Check1CSectionDTO before, Check1CSectionDTO after) {
		Double beforeGood = before.getGoodMileage();
		Double beforeQualified = before.getQualifiedMileage();
		Double beforeUnqualified = before.getUnqualifiedMileage();
		Double beforePoints = before.getPoints();
		Double beforeGoodPoints = before.getGoodPoints();
		Double beforeQualifiedPoints = before.getQualifiedPoints();
		
		Double goodMileage = after.getGoodMileage();
		Double qualifiedMileage = after.getQualifiedMileage();
		Double unqualifiedMileage = after.getUnqualifiedMileage();
		Double points = after.getPoints();
		Double goodPoints = after.getGoodPoints();
		Double qualifiedPoints = after.getQualifiedPoints();
		
		this.points = this.points + points - beforePoints;
		this.goodPoints = this.goodPoints + goodPoints - beforeGoodPoints;
		this.qualifiedPoints = this.qualifiedPoints + qualifiedPoints - beforeQualifiedPoints;
		this.goodMileage = this.goodMileage + goodMileage - beforeGood;
		this.qualifiedMileage = this.qualifiedMileage + qualifiedMileage - beforeQualified;
		this.unqualifiedMileage = this.unqualifiedMileage + unqualifiedMileage - beforeUnqualified;
		this.detectMileage = this.qualifiedMileage + this.unqualifiedMileage;
	}
	
	public Map<String, Check1CSectionDTO> getCheckSectionMap() {
		return checkSectionMap;
	}
	public void setCheckSectionMap(Map<String, Check1CSectionDTO> checkSectionMap) {
		this.checkSectionMap = checkSectionMap;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public List<Check1CSectionDTO> getCheck1CSectionList() {
		return check1CSectionList;
	}
	public void setCheck1CSectionList(List<Check1CSectionDTO> check1cSectionList) {
		check1CSectionList = check1cSectionList;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
	
}
