package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.dict.annotation.DictMark;
import com.togest.domain.upgrade.DataCommonEntity;

public class CInfo  extends DataCommonEntity {
	
	protected String planId;
	  //数据源
    protected String source;
    //日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	protected Date checkDate;
    //线路
    protected String lineId;
    //行别
	@DictMark(dictName = "direction", itemName = "directionName")
    protected String direction;
	protected String directionName;
    //起始站
    protected String startStation;
    //结束站
    protected String endStation;
    protected String startStationName;
    protected String endStationName;
    //分析人
    protected String analyst;
    //分析时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")    protected Date analyDate;
    //上传人
    protected String uploadPerson;
    //上传时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")    protected Date uploadDate;
    //容量大小
    protected Double dataSize;
    //包名
    protected String packageName;
    //包路径
    protected String packagePath;
    //分析状态
	@DictMark(dictName="analy_status",primaryKey="code")
    protected String analyStatus ;
	protected String analyStatusName ;
    //缺陷数据状态
	@DictMark(dictName="defect_data_status",primaryKey="code")
    protected String defectDataStatus ;
	protected String defectDataStatusName ;
    //原始数据状态
	@DictMark(dictName="rawdata_status",primaryKey="code")
    protected String rawdataStatus ;
	protected String rawdataStatusName ;
    //所属类型
    protected String systemId;
    //段别
    protected String sectionId;
    
	
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public String getAnalyStatusName() {
		return analyStatusName;
	}
	public void setAnalyStatusName(String analyStatusName) {
		this.analyStatusName = analyStatusName;
	}
	public String getDefectDataStatusName() {
		return defectDataStatusName;
	}
	public void setDefectDataStatusName(String defectDataStatusName) {
		this.defectDataStatusName = defectDataStatusName;
	}
	public String getRawdataStatusName() {
		return rawdataStatusName;
	}
	public void setRawdataStatusName(String rawdataStatusName) {
		this.rawdataStatusName = rawdataStatusName;
	}
	public String getAnalyst() {
		return analyst;
	}
	public void setAnalyst(String analyst) {
		this.analyst = analyst;
	}
	public Date getAnalyDate() {
		return analyDate;
	}
	public void setAnalyDate(Date analyDate) {
		this.analyDate = analyDate;
	}
	public String getUploadPerson() {
		return uploadPerson;
	}
	public void setUploadPerson(String uploadPerson) {
		this.uploadPerson = uploadPerson;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Double getDataSize() {
		return dataSize;
	}
	public void setDataSize(Double dataSize) {
		this.dataSize = dataSize;
	}
	public String getDefectDataStatus() {
		return defectDataStatus;
	}
	public void setDefectDataStatus(String defectDataStatus) {
		this.defectDataStatus = defectDataStatus;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	public String getRawdataStatus() {
		return rawdataStatus;
	}
	public void setRawdataStatus(String rawdataStatus) {
		this.rawdataStatus = rawdataStatus;
	}
	
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getStartStation() {
		return startStation;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getEndStation() {
		return endStation;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getAnalyStatus() {
		return analyStatus;
	}
	public void setAnalyStatus(String analyStatus) {
		this.analyStatus = analyStatus;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
	
}
