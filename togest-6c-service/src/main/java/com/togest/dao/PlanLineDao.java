package com.togest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.PlanLine;
import com.togest.domain.PlanLineDTO;
import com.togest.request.PlanQueryFilter;

public interface PlanLineDao {
	
	public String getPlanId(String id);

	public PlanLine getByKey(String id);
	
	public List<PlanLine> getByPlanId(String planId);
	
	public List<PlanLineDTO> getPlanLineDTOByPlanId(String planId);
	
	public List<String> getIdsByPlanIds(@Param("planIds")List<String> planIds);

	public List<PlanLineDTO> findPlanLineDTOList(PlanQueryFilter entity);
	
	public List<String> getIds(String planId);
	
	public PlanLine checkRepeat(PlanLine entity);
	
	public int insert(PlanLine entity);
	
	public int update(PlanLine entity);
	
	public int deleteFalsesByLineId(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);
	
	public int deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);
	
	public int insertPlanLineDept(@Param("planLineId") String planLineId,@Param("deptId") String deptId);
	public int deletePlanLineDept(@Param("planLineId") String planLineId,@Param("deptId") String deptId);
	public List<Map<String,String>> getPlanLineDeptList(@Param("planLineId") String planLineId,@Param("deptId") String deptId);
}
