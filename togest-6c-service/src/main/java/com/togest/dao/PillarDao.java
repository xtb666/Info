package com.togest.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.client.response.Pillar;

public interface PillarDao {
	public Pillar findPillarByGlb(Pillar entityList);
	public List<Pillar> findPillarByName(@Param("entityList") List<Pillar> entityList);
	
	public List<Pillar> findPillarDataByGlb(@Param("entity")Pillar entity, @Param("changeFlag")String changeFlag);
	
	public List<Map<String,String>> findPillarMaxAndMinNumByIds(@Param("pillarIdList") List<String> pillarList);
}
