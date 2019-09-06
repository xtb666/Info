package com.togest.dao;

import com.togest.domain.CInfo;
import com.togest.request.InfoQueryFilter;
import com.togest.response.statistics.InfoCAndSize;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;


public interface CInfoDao extends CrudCommonDao<CInfo> {

	public List<CInfo> findLists(InfoQueryFilter entity);
	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteFalsesByPlanBaseId(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteFalsesByPlanExecuteId(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public List<InfoCAndSize> findCAndSize(InfoQueryFilter entity);
	public List<InfoCAndSize> findYAndMon(InfoQueryFilter entity);
}
