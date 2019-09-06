package com.togest.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectFlow;

public interface DefectFlowService {

	public int insert(DefectFlow entity);
	
	public DefectFlow getByKey(String id);
	
	public int update(DefectFlow entity);
	
	public List<DefectFlow> getBykeys(List<String> ids);
	
	public List<DefectFlow> getByProcessInstanceIds(List<String> processInstanceIds);
	
	public void insert(List<Map<String, String>> flowLists);
	
	public void update(List<Map<String, String>> flowLists);
}
