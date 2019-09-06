package com.togest.domain.dto;

import com.togest.domain.BdCorresStand;

public class BdCorresStandDTO extends BdCorresStand {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//线路ID
    private String lineName;
    //变电所ID
    private String pavilionName;
    //供电臂ID
    private String psPdName;
    
    
	public BdCorresStandDTO() {
	}
	
	public BdCorresStandDTO(String lineId, String pavilionId, String psPdId) {
		this.lineId = lineId;
		this.pavilionId = pavilionId;
		this.psPdId = psPdId;
	}
	
	 public BdCorresStandDTO(String lineId, String pavilionId, String psPdId, String lineName, String pavilionName, String pspdName, String breakerNumber, String feederNumber,
				String currentRatio, String voltageRatio, String armLength, String lineLength, String networkLength,
				String unitPowerLine, String unitNetworkLine, String totalReactance1, String totalReactance2,
				String totalReactance3) {
			super();
			this.lineId = lineId;
			this.pavilionId = pavilionId;
			this.psPdId = psPdId;
			this.lineName = lineName;
			this.pavilionName = pavilionName;
			this.psPdName = pspdName;
			this.breakerNumber = breakerNumber;
			this.feederNumber = feederNumber;
			this.currentRatio = currentRatio;
			this.voltageRatio = voltageRatio;
			this.armLength = armLength;
			this.lineLength = lineLength;
			this.networkLength = networkLength;
			this.unitPowerLine = unitPowerLine;
			this.unitNetworkLine = unitNetworkLine;
			this.totalReactance1 = totalReactance1;
			this.totalReactance2 = totalReactance2;
			this.totalReactance3 = totalReactance3;
		}

	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getPavilionName() {
		return pavilionName;
	}
	public void setPavilionName(String pavilionName) {
		this.pavilionName = pavilionName;
	}
	public String getPsPdName() {
		return psPdName;
	}
	public void setPsPdName(String psPdName) {
		this.psPdName = psPdName;
	}
}
