package com.togest.response.statistics;

import java.io.Serializable;

public class DefectInfo implements Serializable {
	
	protected String cName;
	protected Double detectMileage;
	protected String systemId;
	protected Integer totalNum;//缺陷总数
	protected Integer untreatedNum;//已确认未整改
	protected Integer rectificationNum;//已经整改的(含未销号)
	protected Integer highSpeedNum;//高速缺陷总数
	protected Integer generalSpeedNum;//普速缺陷总数
	protected Integer highSpeedRectificationNum;//已经整改的高速缺陷数量(含未销号)
	protected Integer generalSpeedRectificationNum;//已经整改的普速缺陷数量(含未销号)
	
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public Double getDetectMileage() {
		return detectMileage;
	}
	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getUntreatedNum() {
		return untreatedNum;
	}
	public void setUntreatedNum(Integer untreatedNum) {
		this.untreatedNum = untreatedNum;
	}
	public Integer getRectificationNum() {
		return rectificationNum;
	}
	public void setRectificationNum(Integer rectificationNum) {
		this.rectificationNum = rectificationNum;
	}
	public Integer getHighSpeedNum() {
		return highSpeedNum;
	}
	public void setHighSpeedNum(Integer highSpeedNum) {
		this.highSpeedNum = highSpeedNum;
	}
	public Integer getGeneralSpeedNum() {
		return generalSpeedNum;
	}
	public void setGeneralSpeedNum(Integer generalSpeedNum) {
		this.generalSpeedNum = generalSpeedNum;
	}
	public Integer getHighSpeedRectificationNum() {
		return highSpeedRectificationNum;
	}
	public void setHighSpeedRectificationNum(Integer highSpeedRectificationNum) {
		this.highSpeedRectificationNum = highSpeedRectificationNum;
	}
	public Integer getGeneralSpeedRectificationNum() {
		return generalSpeedRectificationNum;
	}
	public void setGeneralSpeedRectificationNum(Integer generalSpeedRectificationNum) {
		this.generalSpeedRectificationNum = generalSpeedRectificationNum;
	}
}
