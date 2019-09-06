package com.togest.dao;

import java.util.Map;

import com.togest.domain.MileageStatisticsDTO;

public interface MileageStatisticsDao extends CrudDao<MileageStatisticsDTO>{
	public int deleteFalses(Map<String, Object> map);
}
