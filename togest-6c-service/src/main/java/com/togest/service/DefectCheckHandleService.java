package com.togest.service;

import java.util.List;

import com.togest.domain.DefectCheckHandle;

public interface DefectCheckHandleService {

	public DefectCheckHandle getCheckByKey(String id);

	public List<DefectCheckHandle> findAllCheckList();
	
	public List<DefectCheckHandle> findCheckList();
	
	public int insertCheck(DefectCheckHandle entity);
	
	public int updateCheck(DefectCheckHandle entity);
}
