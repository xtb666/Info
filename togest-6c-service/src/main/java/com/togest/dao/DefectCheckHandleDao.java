package com.togest.dao;

import java.util.List;

import com.togest.domain.DefectCheckHandle;

public interface DefectCheckHandleDao {

	public DefectCheckHandle getByKey(String id);

	public List<DefectCheckHandle> findAllList();
	
	public List<DefectCheckHandle> findList();
	
	public int insert(DefectCheckHandle entity);
	
	public int update(DefectCheckHandle entity);
	
	public int updateAfter(DefectCheckHandle entity);
	
	public int updateArchiveRest(DefectCheckHandle entity);
	
}
