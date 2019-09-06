package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.PlanDetailDTO;
import com.togest.request.PlanQueryFilter;

public interface PlanDetailDao extends CrudDao<PlanDetailDTO> {

	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);

	public int deleteFalsesByPlanBaseId(@Param("planBaseIds") List<String> planBaseIds,
			@Param("deleteBy") String deleteBy, @Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);
	
	public List<PlanDetailDTO> findLists(PlanQueryFilter entity);

}
