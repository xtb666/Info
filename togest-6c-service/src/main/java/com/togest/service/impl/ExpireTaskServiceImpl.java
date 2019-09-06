package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.dao.ExpireTaskDao;
import com.togest.domain.ExpireTask;
import com.togest.service.ExpireTaskService;

@Service
public class ExpireTaskServiceImpl implements ExpireTaskService{

	@Autowired
	private ExpireTaskDao dao;
	
	@Override
	public ExpireTask getByKey(String id) {
		return dao.getByKey(id);
	}

	@Override
	public List<ExpireTask> findAllList() {
		return dao.findAllList();
	}

	@Override
	public List<ExpireTask> findList(ExpireTask entity) {
		return dao.findList(entity);
	}

	@Override
	public int insert(ExpireTask entity) {
		return dao.insert(entity);
	}

	@Override
	public int update(ExpireTask entity) {
		return dao.update(entity);
	}

	@Override
	public int deleteByKey(String id) {
		return dao.deleteByKey(id);
	}

	@Override
	public int deleteByKeys(List<String> ids) {
		return dao.deleteByKeys(ids);
	}

	@Override
	public int deleteByDataIds(List<String> dataIds) {
		return dao.deleteByDataIds(dataIds);
	}
	
	
	
}
