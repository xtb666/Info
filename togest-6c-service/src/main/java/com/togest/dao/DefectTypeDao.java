package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectTypeDTO;

public interface DefectTypeDao extends TreeDao<DefectTypeDTO> {

	public String getFalseId(String id);
	public List<String> getDefectTypeByRanHu();
	public List<String> getDefectTypeByJiHe();
	public List<String> getDefectTypeChildById(@Param("id")String id);
	
	public List<DefectTypeDTO> getListBySystemCode( @Param("systemCode") String systemCode);
	
	
}
