package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.PlanBaseDTO;
import com.togest.request.PlanQueryFilter;
import com.togest.response.PlanTaskFlowResponse;

public interface PlanBaseDao extends CrudDao<PlanBaseDTO> {

	public List<PlanTaskFlowResponse> findPlanTaskFlow(@Param("ids") List<String> ids);

	public int updateAuditStatus(@Param("ids") List<String> ids, @Param("auditStatus") String auditStatus);

	public int updatePlanStatus(@Param("ids") List<String> ids, @Param("planStatus") String planStatus);

	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);

	public List<PlanBaseDTO> findC1Lists(PlanQueryFilter entity);
	public int findC1ListsCounts(PlanQueryFilter entity);

	public List<PlanBaseDTO> findLists(PlanQueryFilter entity);

}
