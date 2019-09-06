package com.togest.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Plan;
import com.togest.domain.PlanDTO;
import com.togest.request.PlanQueryFilter;
import com.togest.response.PlanTaskFlowResponse;

public interface PlanDao {
	
	public PlanDTO getByKey(String id);
	
	public List<PlanDTO> getByKeys(@Param("ids") List<String> ids);
	
	public List<PlanDTO> findPlanDTOList(PlanQueryFilter entity);
	
	public List<PlanDTO> findLists(PlanQueryFilter entity);
	
	public List<PlanDTO> findNoticePlanList(PlanQueryFilter entity);
	
	public Plan checkRepeat(Plan entity);
	
	public int counts(PlanQueryFilter entity);
	
	public int insert(Plan entity);
	
	public int update(Plan entity);
	
	public int deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp);
	
	public int updateAuditStatus(@Param("ids") List<String> ids, @Param("auditStatus") String auditStatus);
	
	public int updatePlanStatus(@Param("ids") List<String> ids, @Param("planStatus") String planStatus);
	
	public List<PlanTaskFlowResponse> findPlanTaskFlow(@Param("ids") List<String> ids);
	
	public int insertPlanDept(@Param("planId") String planId,@Param("deptId") String deptId);
	public int deletePlanDept(@Param("planId") String planId,@Param("deptId") String deptId);
	public List<Map<String,String>> getPlanDeptList(@Param("planId") String planId,@Param("deptId") String deptId);
}
