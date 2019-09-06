package com.togest.service;

import java.util.List;

import com.togest.domain.DefectReformHandle;

public interface DefectReformHandleService {

	public DefectReformHandle getReformByKey(String id);

	public List<DefectReformHandle> findAllReformList();
	
	public List<DefectReformHandle> findReformList();
	
	public int insertReform(DefectReformHandle entity);
	
	public int updateReform(DefectReformHandle entity);
	
	public int deleteReformByIds(List<String> ids);
}
