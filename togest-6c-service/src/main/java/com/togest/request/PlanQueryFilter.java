package com.togest.request;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import com.togest.common.util.string.StringUtil;
import com.togest.domain.SimplePage;
import com.togest.util.Data;

public class PlanQueryFilter extends Data {
	
	private SimplePage page;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date planDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endCheckDate;
	protected String lineName;
	protected String lineId;
	protected String direction;
	protected String planStatus;
	protected String executeStatus;
	protected String planBaseId;
	protected String planDetailId;
	protected String systemId;
	protected String addDeptId;
	protected String defectdataStatus;
	
	protected String auditPerson;
	protected String patcher;
	protected String implementation;
	protected String startStation;
	protected String endStation;
	protected Double detectMileage ;
	
	
	
	 public Double getDetectMileage() {
		return detectMileage;
	}
	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}
	//实际添乘日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date actualAddDate;
    //实际添乘人
    protected String actualPatcher;
    //实际检测区间
    protected String actualCheckRegion;
    //实际添乘车次
    protected String actualAddTrainNumber;
    //实际添乘机车号
    protected String actualAddTrafficNumber;
    //设备编号
    protected String equNo;
    //设备情况
    protected String equOperation;
    
	protected String flowTag;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	@JsonIgnore
	private List<String> planStatusList;
	@JsonIgnore
	private List<String> executeStatusList;
	
	
	public String getAuditPerson() {
		return auditPerson;
	}
	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	public String getPatcher() {
		return patcher;
	}
	public void setPatcher(String patcher) {
		this.patcher = patcher;
	}
	public String getImplementation() {
		return implementation;
	}
	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}
	public Date getActualAddDate() {
		return actualAddDate;
	}
	public void setActualAddDate(Date actualAddDate) {
		this.actualAddDate = actualAddDate;
	}
	public String getActualPatcher() {
		return actualPatcher;
	}
	public void setActualPatcher(String actualPatcher) {
		this.actualPatcher = actualPatcher;
	}
	public String getActualCheckRegion() {
		return actualCheckRegion;
	}
	public void setActualCheckRegion(String actualCheckRegion) {
		this.actualCheckRegion = actualCheckRegion;
	}
	public String getActualAddTrainNumber() {
		return actualAddTrainNumber;
	}
	public void setActualAddTrainNumber(String actualAddTrainNumber) {
		this.actualAddTrainNumber = actualAddTrainNumber;
	}
	public String getActualAddTrafficNumber() {
		return actualAddTrafficNumber;
	}
	public void setActualAddTrafficNumber(String actualAddTrafficNumber) {
		this.actualAddTrafficNumber = actualAddTrafficNumber;
	}
	public String getEquNo() {
		return equNo;
	}
	public void setEquNo(String equNo) {
		this.equNo = equNo;
	}
	public String getPlanDetailId() {
		return planDetailId;
	}
	public void setPlanDetailId(String planDetailId) {
		this.planDetailId = planDetailId;
	}
	public String getPlanBaseId() {
		return planBaseId;
	}
	public void setPlanBaseId(String planBaseId) {
		this.planBaseId = planBaseId;
	}
	public String getExecuteStatus() {
		return executeStatus;
	}
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
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
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
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
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public List<String> getPlanStatusList() {
		if (StringUtil.isNotBlank(this.getPlanStatus())) {
			return Arrays.asList(this.getPlanStatus().split(","));
		}
		return planStatusList;
	}
	public void setPlanStatusList(List<String> planStatusList) {
		this.planStatusList = planStatusList;
	}
	public List<String> getExecuteStatusList() {
		if (StringUtil.isNotBlank(this.getExecuteStatus())) {
			return Arrays.asList(this.getExecuteStatus().split(","));
		}
		return executeStatusList;
	}
	public void setExecuteStatusList(List<String> executeStatusList) {
		this.executeStatusList = executeStatusList;
	}
	public String getFlowTag() {
		return flowTag;
	}
	public void setFlowTag(String flowTag) {
		this.flowTag = flowTag;
	}
	public String getDefectdataStatus() {
		return defectdataStatus;
	}
	public void setDefectdataStatus(String defectdataStatus) {
		this.defectdataStatus = defectdataStatus;
	}
	public String getEquOperation() {
		return equOperation;
	}
	public void setEquOperation(String equOperation) {
		this.equOperation = equOperation;
	}
	public String getAddDeptId() {
		return addDeptId;
	}
	public void setAddDeptId(String addDeptId) {
		this.addDeptId = addDeptId;
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
	
	
}
