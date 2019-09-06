package com.togest.request;

import com.togest.domain.Defect4CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;

public class DefectC4Request extends Defect4CDTO {

	private DefectCheckHandle defectCheckHandle;

	private DefectReformHandle defectReformHandle;

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

}
