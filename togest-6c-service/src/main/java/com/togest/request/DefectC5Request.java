package com.togest.request;

import com.togest.domain.Defect5CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;

public class DefectC5Request extends Defect5CDTO {
	
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
