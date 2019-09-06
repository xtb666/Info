package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectFlow;

public interface DefectFlowDao {

	public int insert(DefectFlow entity);
	
	public DefectFlow getByKey(String id);
	
	public int update(DefectFlow entity);
	
	public List<DefectFlow> getBykeys(@Param("ids") List<String> ids);
	
	public List<DefectFlow> getByProcessInstanceIds(@Param("processInstanceIds") List<String> processInstanceIds);
}
