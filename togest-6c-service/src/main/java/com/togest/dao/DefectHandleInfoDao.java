package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectHandleInfo;

public interface DefectHandleInfoDao {

	public DefectHandleInfo getByKey(String id);
	
	public List<DefectHandleInfo> getByKeys(@Param("ids")List<String> ids);

	public List<DefectHandleInfo> findAllList();
	
	public List<DefectHandleInfo> findList();
	
	public int insert(DefectHandleInfo entity);
	public int insertBatch(@Param("list") List<DefectHandleInfo> list);
	
	public int update(DefectHandleInfo entity);
	
	public int updateBatch(@Param("ids") List<String> ids, @Param("entity") DefectHandleInfo entity);
	
	public int updateConfirmBatch(@Param("ids") List<String> ids, @Param("confirmPerson") String confirmPerson,
			@Param("confirmDate") Date confirmDate, @Param("isConfirmed") int isConfirmed);
	
	public int updateCancelBatch(@Param("ids") List<String> ids, @Param("cancelPerson") String cancelPerson,
			@Param("cancelDate") Date cancelDate, @Param("isCanceled") int isCanceled);
	
	public int updateAuditStatuBatch(@Param("ids") List<String> ids, @Param("auditStatus") String auditStatus);
	
	public int updatePlanCompleteDateBatch(@Param("ids") List<String> ids, @Param("planCompleteDate") Date planCompleteDate);
	
	public int updateDelayCountBatch(@Param("ids") List<String> ids);

	public int updateArchiveRest(DefectHandleInfo entity);
	
	public void deleteByKeys(@Param("list")List<String> list);
	

	
	
	
	
}
