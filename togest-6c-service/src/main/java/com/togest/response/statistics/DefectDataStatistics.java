package com.togest.response.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefectDataStatistics implements Serializable {

	private String systemCode;

	private List<DefectLevelStatistics> defectLevelStatistics = new ArrayList<DefectLevelStatistics>();

	private List<DefectTypeStatistics> defectTypeStatistics = new ArrayList<DefectTypeStatistics>();

	private List<LineStatistics> lineStatistics = new ArrayList<LineStatistics>();

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public List<DefectLevelStatistics> getDefectLevelStatistics() {
		return defectLevelStatistics;
	}

	public void setDefectLevelStatistics(
			List<DefectLevelStatistics> defectLevelStatistics) {
		this.defectLevelStatistics = defectLevelStatistics;
	}

	public List<DefectTypeStatistics> getDefectTypeStatistics() {
		return defectTypeStatistics;
	}

	public void setDefectTypeStatistics(
			List<DefectTypeStatistics> defectTypeStatistics) {
		this.defectTypeStatistics = defectTypeStatistics;
	}

	public List<LineStatistics> getLineStatistics() {
		return lineStatistics;
	}

	public void setLineStatistics(List<LineStatistics> lineStatistics) {
		this.lineStatistics = lineStatistics;
	}

}
