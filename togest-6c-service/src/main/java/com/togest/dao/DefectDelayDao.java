package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectDelay;

public interface DefectDelayDao {
	
	public DefectDelay getByKey(String id);

	public List<DefectDelay> findAllList();
	
	public List<DefectDelay> findList();
	
	public int insert(DefectDelay entity);
	
	public int update(DefectDelay entity);
	
	public int deleteByKey(String id);
	
	public int updateDefectStatusBatch(@Param("ids") List<String> ids,
			@Param("delayStatus") String delayStatus);
}
