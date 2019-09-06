package com.togest.response.statistics;

import java.util.List;

public class HomeStatistics {

	protected Integer untreatedDefect;
	protected Integer currentDefect;
	protected List<DefectLevelStatistics> defectLevels;
	protected List<CTypeStatistics> cTypes;
	
	public Integer getUntreatedDefect() {
		return untreatedDefect;
	}
	public void setUntreatedDefect(Integer untreatedDefect) {
		this.untreatedDefect = untreatedDefect;
	}
	public Integer getCurrentDefect() {
		return currentDefect;
	}
	public void setCurrentDefect(Integer currentDefect) {
		this.currentDefect = currentDefect;
	}
	public List<DefectLevelStatistics> getDefectLevels() {
		return defectLevels;
	}
	public void setDefectLevels(List<DefectLevelStatistics> defectLevels) {
		this.defectLevels = defectLevels;
	}
	public List<CTypeStatistics> getcTypes() {
		return cTypes;
	}
	public void setcTypes(List<CTypeStatistics> cTypes) {
		this.cTypes = cTypes;
	}
}
