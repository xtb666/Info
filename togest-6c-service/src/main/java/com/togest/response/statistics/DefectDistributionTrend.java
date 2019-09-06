package com.togest.response.statistics;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.togest.common.util.string.StringUtil;

public class DefectDistributionTrend implements Serializable {

	private String id;
	private String name;
	private List<DefectDay> data;
	private Long num = 0l;
	private Long numLastTime = 0l;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DefectDay> getData() {
		return data;
	}
	public void setData(List<DefectDay> data) {
		this.data = data;
	}
	public Long getNum() {
		return StringUtil.isEmpty(data)?0:data.stream()
                .map(num -> num.getFindNum())
                .collect(Collectors.summarizingInt(value -> value)).getSum();
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Long getNumLastTime() {
		return numLastTime;
	}
	public void setNumLastTime(Long numLastTime) {
		this.numLastTime = numLastTime;
	}
	
}
