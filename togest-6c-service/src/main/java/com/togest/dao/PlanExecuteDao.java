package com.togest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.PlanExecuteDTO;
import com.togest.domain.TaskFlowResponse;
import com.togest.request.PlanQueryFilter;
import com.togest.response.PlanTaskFlowResponse;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.PlanMonData;
import com.togest.response.statistics.PlanMonthAccumulativeData;
import com.togest.response.statistics.PlanStatisticsData;
import com.togest.response.statistics.TopPlanRectifyRate;

public interface PlanExecuteDao extends CrudDao<PlanExecuteDTO> {

	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);

	public int deleteFalsesByPlanBaseId(@Param("planBaseIds") List<String> planBaseIds,
			@Param("deleteBy") String deleteBy, @Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);
	
	public int deleteFalsesByPlanDetailId(@Param("planDetailIds") List<String> planDetailIds,
			@Param("deleteBy") String deleteBy, @Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);

	public PlanExecuteDTO getByPlanBaseId(@Param("planBaseId") String planBaseId);
	
	public List<PlanExecuteDTO> getByPlanBaseIds(@Param("ids") List<String> ids);
	
	public PlanExecuteDTO findPlanExecuteById(@Param("id") String id);

	public List<PlanExecuteDTO> findPlanExecuteByPlanBaseId(@Param("planBaseId") String planBaseId);

	public List<PlanExecuteDTO> findLists(PlanQueryFilter entity);
	public int findListsCounts(PlanQueryFilter entity);
	
	public int updateExecuteStatus(@Param("ids") List<String> ids, @Param("executeStatus") String executeStatus);
	
	public List<PlanTaskFlowResponse> findPlanTaskFlow(@Param("ids") List<String> ids);
	
	public int updateFlowTag(@Param("ids") List<String> ids, @Param("flowTag") String flowTag);

	public int updateAuditStatus(@Param("ids") List<String> ids, @Param("auditStatus") String auditStatus);
	
	public int findRepeat(PlanExecuteDTO entity);
	
	public PlanStatisticsData findPlanStatisticsData(PlanQueryFilter entity);

	public List<PlanMonData> planDataFromByMon(PlanQueryFilter entity);
	
	public List<PlanMonData> planDataFromByLine(PlanQueryFilter entity);

	public List<FlowCountData> planFlowCount(PlanQueryFilter entity);
	
	public List<Map<String,String>> planFlowCountNew(PlanQueryFilter entity);
	
	public TopPlanRectifyRate planTopRefromCount(PlanQueryFilter entity);

	public List<PlanExecuteDTO> findPlanExecuteByIds(@Param("ids") String ids);

	public void updateAuditData(@Param("ids") String ids,@Param("auditPerson")String auditPerson,@Param("auditDate")Date auditDate);
	
	public void updateAddData(@Param("ids") String ids,@Param("patcher")String patcher,@Param("addDate")Date addDate);
	
	public void updateConfirmationData(@Param("ids") String ids,@Param("confirmPerson")String confirmPerson,@Param("confirmDate")Date confirmDate);

	public List<PlanMonthAccumulativeData> findMonthAndAccumulativeFrom();
	
	public PlanExecuteDTO checkPlanExecuteRP(PlanExecuteDTO pe);
	
	public List<PlanMonData> planDataFromBySection(PlanQueryFilter entity);

	public List<Map<String,String>> planFlowCount1C(PlanQueryFilter entity);
	
	public List<Map<String,String>> planFlowCount1CSection(PlanQueryFilter entity);

	public List<TaskFlowResponse> getTaskFlowResponseByIds(@Param("ids") List<String> ids);

	public List<Map<String, String>> findPlanFormList(PlanQueryFilter entity);
}