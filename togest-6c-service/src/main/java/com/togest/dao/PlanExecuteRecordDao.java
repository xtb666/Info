package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.PlanExecuteRecordDTO;
import com.togest.request.PlanQueryFilter;

public interface PlanExecuteRecordDao {

	public int insert(PlanExecuteRecordDTO entity);

	public int update(PlanExecuteRecordDTO entity);
	
	public int updateFlow(PlanExecuteRecordDTO entity);

	public List<PlanExecuteRecordDTO> getByKeys(@Param("ids") List<String> ids);
	public PlanExecuteRecordDTO getByKey(@Param("id") String id);

	public void updateRecordStatus(@Param("ids") List<String> ids, @Param("recordStatus") String recordStatus);
	
	public List<String> getByPlanExecuteIdAndRecordStatus(@Param("planExecuteId") String planExecuteId, @Param("recordStatus") String recordStatus);
	
	public List<String> getByPeAndPbIdAndRecordStatus(@Param("planExecuteId") String planExecuteId, @Param("recordStatus") String recordStatus);
	
	public void updateRecordStatusByPlanExecuteIds(@Param("planExecuteIds") List<String> planExecuteIds, @Param("recordStatus") String recordStatus);

	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);

	public int deleteFalsesByPlanExecuteId(@Param("planExecuteIds") List<String> planExecuteIds,
			@Param("deleteBy") String deleteBy, @Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);
	public int deleteFalsesByPlanBaseId(@Param("planBaseIds") List<String> planExecuteIds,
			@Param("deleteBy") String deleteBy, @Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);

	public List<PlanExecuteRecordDTO> findList(PlanExecuteRecordDTO entity);
	
	public List<PlanExecuteRecordDTO> findLists(PlanQueryFilter entity);

	public PlanExecuteRecordDTO getByEntity(PlanExecuteRecordDTO entity);
	
	public PlanExecuteRecordDTO getDetailByKey(String id);
	
	public List<PlanExecuteRecordDTO> getDetailByKeys(@Param("ids") List<String> ids);
	
	public List<PlanExecuteRecordDTO> getDetailByPeKeys(@Param("ids") List<String> ids);

	public List<PlanExecuteRecordDTO> getListByIdAndDeptId(@Param("list")List<String> list,@Param("deptId") String deptId);

	public int findNumberByPeId(String id);
}
