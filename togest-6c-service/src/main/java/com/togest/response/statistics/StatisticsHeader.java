package com.togest.response.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StatisticsHeader implements Serializable {

	protected String line = "线名";
	protected String section = "单位";
	protected String detectMileage = "检测里程";
	protected String subtotal = "小计";
	protected List<String> names = new ArrayList<>();
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDetectMileage() {
		return detectMileage;
	}
	public void setDetectMileage(String detectMileage) {
		this.detectMileage = detectMileage;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
}
