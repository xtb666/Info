package com.togest.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Check1CSection<T> extends BaseEntity<T> {

	protected String id;
	protected Double detectMileage = 0d;
	protected Double averageSpeed = 0d;
	protected Double goodMileage = 0d;
	protected Double qualifiedMileage = 0d;
	protected Double unqualifiedMileage = 0d;
	protected String deptId;//工区
	protected String workShopId;//车间
	protected String sectionId;
	protected String checkId;
	protected Integer count;
	/**
	 * 扣分数 > 40 不合格
	 * 扣分数 <= 40 合格
	 * 扣分数 < 10 优良
	 * 合格扣分数包含优良扣分数
	 */
	protected Double points = 0d;//不合格里程扣分数之和
	protected Double goodPoints = 0d;//优良里程扣分数之和
	protected Double qualifiedPoints = 0d;//合格里程扣分数之和
	protected String unqualifiedDetail;
	protected List<String> unqualifiedList = new ArrayList<>();
	
	protected short delFlag = 0;
	protected String createIp;
	protected String createBy;
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date createDate;
	@JsonIgnore
	protected String updateIp;
	protected String updateBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date updateDate;
	@JsonIgnore
	protected String deleteIp;
	@JsonIgnore
	protected String deleteBy;
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date deleteDate;
	
	public String getUnqualifiedDetail() {
		return unqualifiedDetail;
	}
	public void setUnqualifiedDetail(String unqualifiedDetail) {
		this.unqualifiedDetail = unqualifiedDetail;
	}
	public List<String> getUnqualifiedList() {
		return unqualifiedList;
	}
	public void setUnqualifiedList(List<String> unqualifiedList) {
		this.unqualifiedList = unqualifiedList;
	}
	public Double getGoodPoints() {
		return goodPoints;
	}
	public void setGoodPoints(Double goodPoints) {
		this.goodPoints = goodPoints;
	}
	public Double getQualifiedPoints() {
		return qualifiedPoints;
	}
	public void setQualifiedPoints(Double qualifiedPoints) {
		this.qualifiedPoints = qualifiedPoints;
	}
	public short getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(short delFlag) {
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
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getDetectMileage() {
		return detectMileage;
	}
	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}
	public Double getGoodMileage() {
		return goodMileage;
	}
	public void setGoodMileage(Double goodMileage) {
		this.goodMileage = goodMileage;
	}
	public Double getQualifiedMileage() {
		return qualifiedMileage;
	}
	public void setQualifiedMileage(Double qualifiedMileage) {
		this.qualifiedMileage = qualifiedMileage;
	}
	public Double getUnqualifiedMileage() {
		return unqualifiedMileage;
	}
	public void setUnqualifiedMileage(Double unqualifiedMileage) {
		this.unqualifiedMileage = unqualifiedMileage;
	}
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getWorkShopId() {
		return workShopId;
	}
	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public Double getAverageSpeed() {
		return averageSpeed;
	}
	public void setAverageSpeed(Double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
}
