package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.dao.DefectCheckHandleDao;
import com.togest.dao.DefectReformHandleDao;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;
import com.togest.service.DefectCheckHandleService;
import com.togest.service.DefectReformHandleService;

@Service
public class DefectCheckReformHandleServiceImpl implements DefectCheckHandleService,DefectReformHandleService{

	@Autowired
	private DefectCheckHandleDao checkleDao;
	@Autowired
	private DefectReformHandleDao reformDao;
	
	@Override
	public DefectReformHandle getReformByKey(String id) {
		return reformDao.getByKey(id);
	}
	@Override
	public List<DefectReformHandle> findAllReformList() {
		return reformDao.findAllList();
	}
	@Override
	public List<DefectReformHandle> findReformList() {
		return reformDao.findList();
	}
	@Override
	public int insertReform(DefectReformHandle entity) {
		return reformDao.insert(entity);
	}
	@Override
	public int updateReform(DefectReformHandle entity) {
		return reformDao.update(entity);
	}
	@Override
	public DefectCheckHandle getCheckByKey(String id) {
		return checkleDao.getByKey(id);
	}
	@Override
	public List<DefectCheckHandle> findAllCheckList() {
		return checkleDao.findAllList();
	}
	@Override
	public List<DefectCheckHandle> findCheckList() {
		return checkleDao.findList();
	}
	@Override
	public int insertCheck(DefectCheckHandle entity) {
		return checkleDao.insert(entity);
	}
	@Override
	public int updateCheck(DefectCheckHandle entity) {
		return checkleDao.update(entity);
	}
	@Override
	public int deleteReformByIds(List<String> ids) {
		return reformDao.deleteReformByIds(ids);
	}
}
