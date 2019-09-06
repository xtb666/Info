package com.togest.domain;

import java.util.Date;

import com.togest.dict.annotation.DictMark;

public class DefectAssortment<T> extends BaseEntity<T>{
	protected String id;

	protected String equId;

	protected String defectTypeId;

	protected String defectDescribe;

	protected String dataVariable;

	@DictMark(dictName="busy_defect_data_level",primaryKey="code")
	protected String defectDataLevel;
	
	@DictMark(dictName = "speed_type")
	protected String speedType;
	
	protected String systemId;

	protected Integer sort;

	protected Short delFlag = 0;

	protected String createIp;

	protected String createBy;

	protected Date createDate;

	protected String updateIp;

	protected String updateBy;

	protected Date updateDate;

	protected String deleteIp;

	protected String deleteBy;

	protected Date deleteDate;
	
	protected String defectDataCategory;
	protected String expression;
	protected Double minSpeed;
	protected Double maxSpeed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEquId() {
		return equId;
	}

	public void setEquId(String equId) {
		this.equId = equId;
	}

	public String getDefectTypeId() {
		return defectTypeId;
	}

	public void setDefectTypeId(String defectTypeId) {
		this.defectTypeId = defectTypeId;
	}

	public String getDefectDescribe() {
		return defectDescribe;
	}

	public void setDefectDescribe(String defectDescribe) {
		this.defectDescribe = defectDescribe;
	}

	public String getDataVariable() {
		return dataVariable;
	}

	public void setDataVariable(String dataVariable) {
		this.dataVariable = dataVariable;
	}

	public String getDefectDataLevel() {
		return defectDataLevel;
	}

	public void setDefectDataLevel(String defectDataLevel) {
		this.defectDataLevel = defectDataLevel;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
		this.createIp = createIp;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
		this.updateIp = updateIp;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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
		this.deleteIp = deleteIp;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getDefectDataCategory() {
		return defectDataCategory;
	}

	public void setDefectDataCategory(String defectDataCategory) {
		this.defectDataCategory = defectDataCategory;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Double getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(Double minSpeed) {
		this.minSpeed = minSpeed;
	}

	public Double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getSpeedType() {
		return speedType;
	}

	public void setSpeedType(String speedType) {
		this.speedType = speedType;
	}

}