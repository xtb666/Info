package com.togest.dao;

import com.togest.domain.DefectRepeat;

public interface DefectRepeatDao {

	public int insert(DefectRepeat entity);
	
	public int update(DefectRepeat entity);
	
	public DefectRepeat getByKey(String id);
}
