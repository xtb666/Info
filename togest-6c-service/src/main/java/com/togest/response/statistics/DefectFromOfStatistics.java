package com.togest.response.statistics;

import java.io.Serializable;

public class DefectFromOfStatistics implements Serializable {
	
	
	protected String workShopName = ""; 
	protected String workShopId;
	protected Integer numNow = 0 ;
	protected Integer numAll = 0 ;
	protected Integer numDLevel1 = 0 ;
	protected Integer numDLevel2 = 0 ;
	protected Integer numDToday = 0 ;
	protected Integer numDFindToday = 0 ;
	protected Integer numDProcessing = 0 ;
	protected Integer numDCanceled = 0 ;
	protected String defectTopThree="";
	
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public Integer getNumNow() {
		return numNow;
	}
	public void setNumNow(Integer numNow) {
		this.numNow = numNow;
	}
	public Integer getNumDLevel1() {
		return numDLevel1;
	}
	public void setNumDLevel1(Integer numDLevel1) {
		this.numDLevel1 = numDLevel1;
	}
	public Integer getNumDLevel2() {
		return numDLevel2;
	}
	public void setNumDLevel2(Integer numDLevel2) {
		this.numDLevel2 = numDLevel2;
	}
	public Integer getNumDToday() {
		return numDToday;
	}
	public void setNumDToday(Integer numDToday) {
		this.numDToday = numDToday;
	}
	public Integer getNumDFindToday() {
		return numDFindToday;
	}
	public void setNumDFindToday(Integer numDFindToday) {
		this.numDFindToday = numDFindToday;
	}
	public Integer getNumDProcessing() {
		return numDProcessing;
	}
	public void setNumDProcessing(Integer numDProcessing) {
		this.numDProcessing = numDProcessing;
	}
	public Integer getNumDCanceled() {
		return numDCanceled;
	}
	public void setNumDCanceled(Integer numDCanceled) {
		this.numDCanceled = numDCanceled;
	}
	public String getDefectTopThree() {
		return defectTopThree;
	}
	public void setDefectTopThree(String defectTopThree) {
		this.defectTopThree = defectTopThree;
	}
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	public Integer getNumAll() {
		return numAll;
	}
	public void setNumAll(Integer numAll) {
		this.numAll = numAll;
	}
}
