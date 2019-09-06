package com.togest.response.statistics;

import java.io.Serializable;
import java.util.List;

public class StatisticsRow implements Serializable {
	
	protected String name;
	protected Double detectMileage;
	protected List<Integer> nums;
	protected Integer subtotal = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getDetectMileage() {
		return detectMileage;
	}
	public void setDetectMileage(Double detectMileage) {
		this.detectMileage = detectMileage;
	}
	public List<Integer> getNums() {
		return nums;
	}
	public void setNums(List<Integer> nums) {
		this.nums = nums;
	}
	public Integer getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}
}
