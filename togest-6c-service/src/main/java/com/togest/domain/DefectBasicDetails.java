package com.togest.domain;

public class DefectBasicDetails extends DefectDTO {

	protected DefectCheckHandle defectCheckHandle;

	protected DefectReformHandle defectReformHandle;
	
	protected DefectHandleInfo defectHandleInfo;
	
	protected DefectDelay defectDelay;

	public DefectCheckHandle getDefectCheckHandle() {
		return defectCheckHandle;
	}

	public void setDefectCheckHandle(DefectCheckHandle defectCheckHandle) {
		this.defectCheckHandle = defectCheckHandle;
	}

	public DefectReformHandle getDefectReformHandle() {
		return defectReformHandle;
	}

	public void setDefectReformHandle(DefectReformHandle defectReformHandle) {
		this.defectReformHandle = defectReformHandle;
	}

	public DefectHandleInfo getDefectHandleInfo() {
		return defectHandleInfo;
	}

	public void setDefectHandleInfo(DefectHandleInfo defectHandleInfo) {
		this.defectHandleInfo = defectHandleInfo;
	}

	public DefectDelay getDefectDelay() {
		return defectDelay;
	}

	public void setDefectDelay(DefectDelay defectDelay) {
		this.defectDelay = defectDelay;
	}
	
}
