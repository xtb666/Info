package com.togest.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.annotation.Shift;
import com.togest.dao.DefectDelayDao;
import com.togest.dao.DefectHistoryDelayDao;
import com.togest.domain.DefectDelay;
import com.togest.domain.DefectHistoryDelay;
import com.togest.service.DefectDelayService;

@Service
public class DefectDelayServiceImpl implements DefectDelayService {

	@Autowired
	private DefectDelayDao dao;
	@Autowired
	private DefectHistoryDelayDao historyDao;

	@Override
	@Transactional
	public int insertHistoryByCopy(List<String> defectIds) {
		
		for (String defectId : defectIds) {
			DefectDelay delay = dao.getByKey(defectId);
			DefectHistoryDelay historyDelay = new DefectHistoryDelay();
			try {
				BeanUtils.copyProperties(historyDelay, delay);
			} catch (Exception e) {
				Shift.fatal(StatusCode.FAIL);
			}
			historyDelay.preInsert();
			historyDao.insert(historyDelay);
		}
		return defectIds.size();
	}

	@Override
	public DefectDelay getByKey(String id) {
		return dao.getByKey(id);
	}

	@Override
	public List<DefectDelay> findAllList() {
		return dao.findAllList();
	}

	@Override
	public List<DefectDelay> findList() {
		return dao.findList();
	}

	@Override
	public int insert(DefectDelay entity) {
		return dao.insert(entity);
	}

	@Override
	public int update(DefectDelay entity) {
		return dao.update(entity);
	}

	@Override
	public DefectHistoryDelay getByKeyHistory(String id) {
		return historyDao.getByKey(id);
	}

	@Override
	public List<DefectHistoryDelay> findAllListHistory() {
		return historyDao.findAllList();
	}

	@Override
	public List<DefectHistoryDelay> findListHistory() {
		return historyDao.findList();
	}

	@Override
	public int insertHistory(DefectHistoryDelay entity) {
		return historyDao.insert(entity);
	}

	@Override
	public int updateHistory(DefectHistoryDelay entity) {
		return historyDao.update(entity);
	}

	@Override
	public int delete(String id) {
		return dao.deleteByKey(id);
	}

	@Override
	public int updateDefectStatusBatch(List<String> ids, String defectStatus) {
		return dao.updateDefectStatusBatch(ids, defectStatus);
	}

}
