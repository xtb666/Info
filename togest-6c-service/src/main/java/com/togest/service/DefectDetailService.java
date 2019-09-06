package com.togest.service;

import java.util.List;

import com.togest.domain.DefectDetail;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;

public interface DefectDetailService {

	public DefectDetail getByKey(String id);
	
	public List<DefectDetail> findAllList();
	
	public List<DefectDetail> getListRegisterDefect(CQueryFilter entity);
	
	public Page<DefectDetail> getPageRegisterDefect(Page page,CQueryFilter entity);
}
