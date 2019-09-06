package com.togest.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.togest.dict.annotation.DictMark;

public class PlanExecuteDTO extends PlanExecute {

	private String planBaseTrainNumber;
	 
	private String planBaseTrainId;
	
	private String planBaseTrainName;
	
	private String planBaseContacts;
	
	private String planBaseRemark;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date planBasePlanDate;
	
	private String planDetailLineId;
	
	@DictMark(dictName = "direction")
	private String planDetailDirection;
	
	private String planDetailLineName;
	
	private String planDetailDirectionName;
	
	private String planDetailCheckRegion;
	
	private String planDetailStartStationName;

    private String planDetailEndStationName;
    
    private String planDetailStartStation;

    private String planDetailEndStation;

    private Double planDetailDetectMileage;
	
	private String deptName;
	
	private String systemName;
	
	private String executeStatusName;
	
	private String responsibleDeptName;
	
	private String flowId;
	
	private String formKey;
	
	private String auditStatusName;
	
	private List<PlanExecuteRecordDTO> planExecuteRecords;
	private List<Naming> deptList;

	public String getPlanDetailStartStationName() {
		return planDetailStartStationName;
	}

	public void setPlanDetailStartStationName(String planDetailStartStationName) {
		this.planDetailStartStationName = planDetailStartStationName;
	}

	public String getPlanDetailEndStationName() {
		return planDetailEndStationName;
	}

	public void setPlanDetailEndStationName(String planDetailEndStationName) {
		this.planDetailEndStationName = planDetailEndStationName;
	}

	public String getPlanDetailStartStation() {
		return planDetailStartStation;
	}

	public void setPlanDetailStartStation(String planDetailStartStation) {
		this.planDetailStartStation = planDetailStartStation;
	}

	public String getPlanDetailEndStation() {
		return planDetailEndStation;
	}

	public void setPlanDetailEndStation(String planDetailEndStation) {
		this.planDetailEndStation = planDetailEndStation;
	}

	public Double getPlanDetailDetectMileage() {
		return planDetailDetectMileage;
	}

	public void setPlanDetailDetectMileage(Double planDetailDetectMileage) {
		this.planDetailDetectMileage = planDetailDetectMileage;
	}

	public List<Naming> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Naming> deptList) {
		this.deptList = deptList;
	}

	public List<PlanExecuteRecordDTO> getPlanExecuteRecords() {
		return planExecuteRecords;
	}

	public void setPlanExecuteRecords(List<PlanExecuteRecordDTO> planExecuteRecords) {
		this.planExecuteRecords = planExecuteRecords;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getPlanBaseTrainNumber() {
		return planBaseTrainNumber;
	}

	public void setPlanBaseTrainNumber(String planBaseTrainNumber) {
		this.planBaseTrainNumber = planBaseTrainNumber;
	}

	public String getPlanBaseTrainId() {
		return planBaseTrainId;
	}

	public void setPlanBaseTrainId(String planBaseTrainId) {
		this.planBaseTrainId = planBaseTrainId;
	}

	public String getPlanBaseTrainName() {
		return planBaseTrainName;
	}

	public void setPlanBaseTrainName(String planBaseTrainName) {
		this.planBaseTrainName = planBaseTrainName;
	}

	public String getPlanBaseContacts() {
		return planBaseContacts;
	}

	public void setPlanBaseContacts(String planBaseContacts) {
		this.planBaseContacts = planBaseContacts;
	}

	public String getPlanBaseRemark() {
		return planBaseRemark;
	}

	public void setPlanBaseRemark(String planBaseRemark) {
		this.planBaseRemark = planBaseRemark;
	}

	public Date getPlanBasePlanDate() {
		return planBasePlanDate;
	}

	public void setPlanBasePlanDate(Date planBasePlanDate) {
		this.planBasePlanDate = planBasePlanDate;
	}
	public String getPlanDetailLineId() {
		return planDetailLineId;
	}

	public void setPlanDetailLineId(String planDetailLineId) {
		this.planDetailLineId = planDetailLineId;
	}

	public String getPlanDetailDirection() {
		return planDetailDirection;
	}

	public void setPlanDetailDirection(String planDetailDirection) {
		this.planDetailDirection = planDetailDirection;
	}

	public String getPlanDetailLineName() {
		return planDetailLineName;
	}

	public void setPlanDetailLineName(String planDetailLineName) {
		this.planDetailLineName = planDetailLineName;
	}

	public String getPlanDetailDirectionName() {
		return planDetailDirectionName;
	}

	public void setPlanDetailDirectionName(String planDetailDirectionName) {
		this.planDetailDirectionName = planDetailDirectionName;
	}

	
	public String getPlanDetailCheckRegion() {
		return planDetailCheckRegion;
	}

	public void setPlanDetailCheckRegion(String planDetailCheckRegion) {
		this.planDetailCheckRegion = planDetailCheckRegion;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getExecuteStatusName() {
		return executeStatusName;
	}

	public void setExecuteStatusName(String executeStatusName) {
		this.executeStatusName = executeStatusName;
	}

	public String getResponsibleDeptName() {
		return responsibleDeptName;
	}

	public void setResponsibleDeptName(String responsibleDeptName) {
		this.responsibleDeptName = responsibleDeptName;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}