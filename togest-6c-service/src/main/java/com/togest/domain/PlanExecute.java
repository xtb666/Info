package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.common.util.string.StringUtil;
import com.togest.dict.annotation.DictMark;

public class PlanExecute extends BaseEntity<PlanExecute> {
	
    protected String id;

    protected String planBaseId;

    protected String planDetailId;

    protected String startStation;
    
    protected String endStation;
    
    protected String responsibleDeptId;

    protected String deptId;

    protected String auditPerson;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date auditDate;
    
    protected String patcher;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date addDate;

    protected String confirmPerson;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date confirmDate;
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
    //设备运行情况
    protected String equOperation;
    
    protected String implementation;

    protected String sectionId;

    protected String systemId;
    @DictMark(dictName = "plan_status", primaryKey = "code")
    protected String executeStatus;

    protected Short delFlag = 0;

    protected String createIp;

    protected String createBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date createDate;

    protected String updateIp;

    protected String updateBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date updateDate;

    protected String deleteIp;

    protected String deleteBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date deleteDate;

    protected String remark;
    
    @DictMark(dictName = "plan_audit_status", primaryKey = "code")
    protected String auditStatus;
	
    protected Integer flowTag = 0;

    
    
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

	public String getEquOperation() {
		return equOperation;
	}

	public void setEquOperation(String equOperation) {
		this.equOperation = equOperation;
	}

	public String getAuditPerson() {
		if (StringUtil.isNotBlank(this.auditPerson)) {
			return this.auditPerson.substring(
					this.auditPerson.lastIndexOf("_") + 1,
					this.auditPerson.length());
		}
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getConfirmPerson() {
		if (StringUtil.isNotBlank(this.confirmPerson)) {
			return this.confirmPerson.substring(
					this.confirmPerson.lastIndexOf("_") + 1,
					this.confirmPerson.length());
		}
		return confirmPerson;
	}

	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getPlanBaseId() {
		return planBaseId;
	}

	public void setPlanBaseId(String planBaseId) {
		this.planBaseId = planBaseId;
	}

	public String getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(String planDetailId) {
		this.planDetailId = planDetailId;
	}

	public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation == null ? null : startStation.trim();
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation == null ? null : endStation.trim();
    }

    public String getResponsibleDeptId() {
        return responsibleDeptId;
    }

    public void setResponsibleDeptId(String responsibleDeptId) {
        this.responsibleDeptId = responsibleDeptId == null ? null : responsibleDeptId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getPatcher() {
        return patcher;
    }

    public void setPatcher(String patcher) {
        this.patcher = patcher == null ? null : patcher.trim();
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation == null ? null : implementation.trim();
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId == null ? null : sectionId.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus == null ? null : executeStatus.trim();
    }

    public Short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDeleteIp() {
        return deleteIp;
    }

    public void setDeleteIp(String deleteIp) {
        this.deleteIp = deleteIp == null ? null : deleteIp.trim();
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy == null ? null : deleteBy.trim();
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getFlowTag() {
		return flowTag;
	}

	public void setFlowTag(Integer flowTag) {
		this.flowTag = flowTag;
	}

}