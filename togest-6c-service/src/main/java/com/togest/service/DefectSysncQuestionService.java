package com.togest.service;

import java.util.List;

import com.togest.domain.Defect;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;

public interface DefectSysncQuestionService {

	public void sysncDefectData(List<String> list);
	
	public void sysncDefectReformHandleData(DefectCheckHandle defectCheckHandle, DefectReformHandle defectReformHandle);

	public void sysncDefectImportData(Defect entity);

	public <T extends Defect> void sysncDefectImportData(List<T> defectLists);
}
