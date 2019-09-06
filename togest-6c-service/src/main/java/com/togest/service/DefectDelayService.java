package com.togest.service;

import java.util.List;

import com.togest.domain.DefectDelay;
import com.togest.domain.DefectHistoryDelay;

public interface DefectDelayService {

	public DefectDelay getByKey(String id);

	public List<DefectDelay> findAllList();

	public List<DefectDelay> findList();

	public int insert(DefectDelay entity);

	public int update(DefectDelay entity);

	public DefectHistoryDelay getByKeyHistory(String id);

	public List<DefectHistoryDelay> findAllListHistory();

	public List<DefectHistoryDelay> findListHistory();

	public int insertHistory(DefectHistoryDelay entity);

	public int updateHistory(DefectHistoryDelay entity);

	public int delete(String id);

	public int updateDefectStatusBatch(List<String> ids, String defectStatus);
	
	public int insertHistoryByCopy(List<String> defectId);
}
