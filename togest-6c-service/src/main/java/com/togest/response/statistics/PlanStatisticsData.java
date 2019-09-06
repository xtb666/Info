package com.togest.response.statistics;

import java.io.Serializable;

public class PlanStatisticsData implements Serializable {
	
	protected Integer num = 0 ;
	protected String executeStatus;
	protected String defectDataStatus;
	protected Integer uploadDataNum = 0;
	protected Integer analysisDataNum = 0;
	protected Integer reformPENum = 0;
	protected Integer pENum = 0;
	
	
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getExecuteStatus() {
		return executeStatus;
	}
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	public String getDefectDataStatus() {
		return defectDataStatus;
	}
	public void setDefectDataStatus(String defectDataStatus) {
		this.defectDataStatus = defectDataStatus;
	}
	public Integer getUploadDataNum() {
		return uploadDataNum;
	}
	public void setUploadDataNum(Integer uploadDataNum) {
		this.uploadDataNum = uploadDataNum;
	}
	public Integer getReformPENum() {
		return reformPENum;
	}
	public void setReformPENum(Integer reformPENum) {
		this.reformPENum = reformPENum;
	}
	public Integer getpENum() {
		return pENum;
	}
	public void setpENum(Integer pENum) {
		this.pENum = pENum;
	}
	public Integer getAnalysisDataNum() {
		return analysisDataNum;
	}
	public void setAnalysisDataNum(Integer analysisDataNum) {
		this.analysisDataNum = analysisDataNum;
	}
}
