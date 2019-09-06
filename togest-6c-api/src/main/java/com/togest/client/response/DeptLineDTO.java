package com.togest.client.response;

import java.io.Serializable;

public class DeptLineDTO implements Serializable {

	protected String id;
	protected String deptId;
	protected String plId;
	protected Double startKm1;
	protected Double endKm1;
	protected Double startKm;
	protected Double endKm;
	protected String sectionId;
	protected String deptName;
	protected String plName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getPlId() {
		return plId;
	}
	public void setPlId(String plId) {
		this.plId = plId;
	}
	public Double getStartKm1() {
		return startKm1;
	}
	public void setStartKm1(Double startKm1) {
		this.startKm1 = startKm1;
	}
	public Double getEndKm1() {
		return endKm1;
	}
	public void setEndKm1(Double endKm1) {
		this.endKm1 = endKm1;
	}
	public Double getStartKm() {
		return startKm;
	}
	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}
	public Double getEndKm() {
		return endKm;
	}
	public void setEndKm(Double endKm) {
		this.endKm = endKm;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPlName() {
		return plName;
	}
	public void setPlName(String plName) {
		this.plName = plName;
	}
}
