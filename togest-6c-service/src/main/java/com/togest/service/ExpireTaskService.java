package com.togest.service;

import java.util.List;

import com.togest.domain.ExpireTask;

public interface ExpireTaskService {
	
	public ExpireTask getByKey(String id);

	public List<ExpireTask> findAllList();
	
	public List<ExpireTask> findList(ExpireTask entity);
	
	public int insert(ExpireTask entity);
	
	public int update(ExpireTask entity);
	
	public int deleteByKey(String id);
	
	public int deleteByKeys(List<String> ids);
	
	public int deleteByDataIds(List<String> dataIds);
}
