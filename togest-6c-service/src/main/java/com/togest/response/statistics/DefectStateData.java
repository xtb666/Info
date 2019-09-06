package com.togest.response.statistics;

import java.io.Serializable;

public class DefectStateData implements Serializable {
	
	protected Integer num = 0 ;
	protected String typicalDefect;
	protected String defectStatus;
	protected Integer tdNum = 0;
	protected Integer newFindDSNum = 0;
	protected Integer reformDSNum = 0;
	protected Integer cancelDSNum = 0;
	protected Integer defectNum = 0;
	
	
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getTypicalDefect() {
		return typicalDefect;
	}
	public void setTypicalDefect(String typicalDefect) {
		this.typicalDefect = typicalDefect;
	}
	public String getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}
	public Integer getTdNum() {
		return tdNum;
	}
	public void setTdNum(Integer tdNum) {
		this.tdNum = tdNum;
	}
	public Integer getNewFindDSNum() {
		return newFindDSNum;
	}
	public void setNewFindDSNum(Integer newFindDSNum) {
		this.newFindDSNum = newFindDSNum;
	}
		public Integer getDefectNum() {
		return defectNum;
	}
	public void setDefectNum(Integer defectNum) {
		this.defectNum = defectNum;
	}
	public Integer getReformDSNum() {
		return reformDSNum;
	}
	public void setReformDSNum(Integer reformDSNum) {
		this.reformDSNum = reformDSNum;
	}
	public Integer getCancelDSNum() {
		return cancelDSNum;
	}
	public void setCancelDSNum(Integer cancelDSNum) {
		this.cancelDSNum = cancelDSNum;
	}
	
}
