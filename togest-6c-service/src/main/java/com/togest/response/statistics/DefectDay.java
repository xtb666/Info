package com.togest.response.statistics;

public class DefectDay implements Comparable<DefectDay> {

	protected Integer day;
	protected Integer findNum = 0;
	protected Integer canceledNum = 0;
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getFindNum() {
		return findNum;
	}
	public void setFindNum(Integer findNum) {
		this.findNum = findNum;
	}
	public Integer getCanceledNum() {
		return canceledNum;
	}
	public void setCanceledNum(Integer canceledNum) {
		this.canceledNum = canceledNum;
	}
	
	@Override
	public int compareTo(DefectDay o) {//按照日期排序
		return this.getDay() - o.getDay();
	}
	
}
