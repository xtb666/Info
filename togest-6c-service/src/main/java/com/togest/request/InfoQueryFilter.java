package com.togest.request;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import com.togest.dict.annotation.DictMark;
import com.togest.domain.SimplePage;
import com.togest.util.Data;

public class InfoQueryFilter extends Data {
	
	protected SimplePage page;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date planDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endCheckDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	private Date checkDate;
	
	private String source;
	
	private String planId;
    //线路
    private String lineId;
    //行别
	@DictMark(dictName = "direction", itemName = "directionName")
    private String direction;
    //起始站
    private String startStation;
    //结束站
    private String endStation;
    //分析人
    private String analyst;
    //分析时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")    private Date analyDate;
    //上传人
    private String uploadPerson;
    //上传时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")    private Date uploadDate;
    //容量大小
    private String dataSize;
    //包名
    private String packageName;
    //包路径
    private String packagePath;
    //分析状态
	@DictMark(dictName="analy_status",primaryKey="code")
    private String analyStatus ;
    //缺陷数据状态
	@DictMark(dictName="defectdata_status",primaryKey="code")
    private String defectDataStatus ;
    //原始数据状态
	@DictMark(dictName="rawdata_status",primaryKey="code")
    private String rawdataStatus ;
    //所属类型
    private String systemId;
    //段别
    private String sectionId;
	
	protected String flowTag;
	private Integer delFlag;
	
	protected String id;
	protected String infoStatus;
	protected Double km;
	
	protected Integer panoramisPixel;
	protected Integer partPixel;
	protected Integer dataType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endTime;
	protected String startPoleno;
	protected String endPoleno;
	
	protected Integer cameraNumber;
	protected Integer supportCameraNumber;
	protected Integer droppersCameraNumber;
	protected Integer appendCameraNumber;
	protected Integer davitCameraNumber;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfoStatus() {
		return infoStatus;
	}
	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}
	public Double getKm() {
		return km;
	}
	public void setKm(Double km) {
		this.km = km;
	}
	public Integer getPanoramisPixel() {
		return panoramisPixel;
	}
	public void setPanoramisPixel(Integer panoramisPixel) {
		this.panoramisPixel = panoramisPixel;
	}
	public Integer getPartPixel() {
		return partPixel;
	}
	public void setPartPixel(Integer partPixel) {
		this.partPixel = partPixel;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStartPoleno() {
		return startPoleno;
	}
	public void setStartPoleno(String startPoleno) {
		this.startPoleno = startPoleno;
	}
	public String getEndPoleno() {
		return endPoleno;
	}
	public void setEndPoleno(String endPoleno) {
		this.endPoleno = endPoleno;
	}
	public Integer getCameraNumber() {
		return cameraNumber;
	}
	public void setCameraNumber(Integer cameraNumber) {
		this.cameraNumber = cameraNumber;
	}
	public Integer getSupportCameraNumber() {
		return supportCameraNumber;
	}
	public void setSupportCameraNumber(Integer supportCameraNumber) {
		this.supportCameraNumber = supportCameraNumber;
	}
	public Integer getDroppersCameraNumber() {
		return droppersCameraNumber;
	}
	public void setDroppersCameraNumber(Integer droppersCameraNumber) {
		this.droppersCameraNumber = droppersCameraNumber;
	}
	public Integer getAppendCameraNumber() {
		return appendCameraNumber;
	}
	public void setAppendCameraNumber(Integer appendCameraNumber) {
		this.appendCameraNumber = appendCameraNumber;
	}
	public Integer getDavitCameraNumber() {
		return davitCameraNumber;
	}
	public void setDavitCameraNumber(Integer davitCameraNumber) {
		this.davitCameraNumber = davitCameraNumber;
	}
	@JsonIgnore
	protected List<String> analyStatusList;
	@JsonIgnore
	protected List<String> defectDataStatusList;
	@JsonIgnore
	protected List<String> rawdataStatusList;
	public SimplePage getPage() {
		return page;
	}
	public void setPage(SimplePage page) {
		this.page = page;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Date getBeginCheckDate() {
		return beginCheckDate;
	}
	public void setBeginCheckDate(Date beginCheckDate) {
		this.beginCheckDate = beginCheckDate;
	}
	public Date getEndCheckDate() {
		return endCheckDate;
	}
	public void setEndCheckDate(Date endCheckDate) {
		this.endCheckDate = endCheckDate;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
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
	public String getStartStation() {
		return startStation;
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
	public String getDataSize() {
		return dataSize;
	}
	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
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
	public String getAnalyStatus() {
		return analyStatus;
	}
	public void setAnalyStatus(String analyStatus) {
		this.analyStatus = analyStatus;
	}
	public String getDefectDataStatus() {
		return defectDataStatus;
	}
	public void setDefectDataStatus(String defectDataStatus) {
		this.defectDataStatus = defectDataStatus;
	}
	public String getRawdataStatus() {
		return rawdataStatus;
	}
	public void setRawdataStatus(String rawdataStatus) {
		this.rawdataStatus = rawdataStatus;
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
	public String getFlowTag() {
		return flowTag;
	}
	public void setFlowTag(String flowTag) {
		this.flowTag = flowTag;
	}
	public List<String> getAnalyStatusList() {
		return analyStatusList;
	}
	public void setAnalyStatusList(List<String> analyStatusList) {
		this.analyStatusList = analyStatusList;
	}
	public List<String> getDefectDataStatusList() {
		return defectDataStatusList;
	}
	public void setDefectDataStatusList(List<String> defectDataStatusList) {
		this.defectDataStatusList = defectDataStatusList;
	}
	public List<String> getRawdataStatusList() {
		return rawdataStatusList;
	}
	public void setRawdataStatusList(List<String> rawdataStatusList) {
		this.rawdataStatusList = rawdataStatusList;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	
}