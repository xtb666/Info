package com.togest.service;

import java.util.List;

import com.togest.domain.DefectTypeDTO;

public interface DefectTypeService extends ICrudService<DefectTypeDTO> {

	public DefectTypeDTO findChildsByParent(String id);
	
	public List<DefectTypeDTO> getListBySystemCode(String systemCode);
	
	public DefectTypeDTO getTreeBySystemCode(String systemCode);
	
	public List<String> getDefectTypeByRanHu();
	public List<String> getDefectTypeByJiHe();
	
}
