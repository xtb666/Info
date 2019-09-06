package com.togest.dao;

import java.util.List;

import com.togest.domain.DefectDetail;
import com.togest.request.CQueryFilter;

public interface DefectDetailDao {

	public DefectDetail getByKey(String id);
	
	public List<DefectDetail> findAllList();
	
	public List<DefectDetail> getListRegisterDefect(CQueryFilter entity);
}
