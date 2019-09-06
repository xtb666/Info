package com.togest.domain;

public class WireHeightControlHistoryDTO extends WireHeightControlHistory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String workAreaName;
	// 线路名称
	private String lineName; 
	// 站区名称
	private String psaName;
	// 行别
	private String directionName;
	// 桥隧
	private String tunnelName;
	// 支柱号
	private String pillarNum;
	// 支柱名称
	private String pillarName;
	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public String getTunnelName() {
		return tunnelName;
	}
	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}
	public String getWorkAreaName() {
		return workAreaName;
	}
	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}
	public String getPillarNum() {
		return pillarNum;
	}
	public void setPillarNum(String pillarNum) {
		this.pillarNum = pillarNum;
	}
	public String getPillarName() {
		return pillarName;
	}
	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}
}
