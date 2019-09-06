package com.togest.dao;

import java.util.List;

import com.togest.domain.DefectHistoryDelay;

public interface DefectHistoryDelayDao {

	public DefectHistoryDelay getByKey(String id);

	public List<DefectHistoryDelay> findAllList();
	
	public List<DefectHistoryDelay> findList();
	
	public int insert(DefectHistoryDelay entity);
	
	public int update(DefectHistoryDelay entity);
}
