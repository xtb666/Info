package com.togest.dao;

import java.util.List;

import com.togest.domain.DefectHistory;

public interface DefectHistoryDao {

	public int insert(DefectHistory entity);
	
	public int update(DefectHistory entity);
	
	public int updateCount(String id);
	
	public DefectHistory getByKey(String id);
	
	public List<DefectHistory> getByEntity(DefectHistory entity);
}
