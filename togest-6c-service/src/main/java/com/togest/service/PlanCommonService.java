package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.PlanBaseDTO;
import com.togest.domain.PlanDetailDTO;
import com.togest.domain.PlanExecuteDTO;
import com.togest.domain.PlanExecuteRecordDTO;
import com.togest.request.C1PlanRequest;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.PlanQueryFilter;
import com.togest.request.RePlanTaskRequest;
import com.togest.request.TaskRejectRequest;
import com.togest.request.TaskRequest;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.PlanStatisticsByMon;
import com.togest.response.statistics.PlanStatisticsData;
import com.togest.response.statistics.TopPlanRectifyRate;

public interface PlanCommonService {

	// 计划发起
	public int startPlan(FlowStartUserDataRequest entity);

	// 计划重新发起
	public int reStartPlan(RePlanTaskRequest entity);

	// 计划审核
	public int auditPlan(TaskRequest entity);

	// 添乘记录填写
	public void fillRecordPlan(TaskRequest entity);

	// 计划完成确认
	public int completionConfirmationPlan(TaskRequest entity);
	
	public int archivePlan(TaskRejectRequest entity, String taskId);

	// 解析计划Excel数据
	public Map<String, Object> analyzeExcelData(String originalFilename, InputStream inputStream, String createBy,
			String systemId, String sectionId);
	
	public List<PlanBaseDTO> findPlanBaseLists(PlanQueryFilter entity);
	
	public Page<PlanBaseDTO> findPlanBasePages(Page page,PlanQueryFilter entity);
	
	public PlanBaseDTO getPlanBase(String id);
	
	public PlanExecuteDTO getPlanExecute(String id);
	
	public PlanDetailDTO getPlanDetail(String id);
	
	public int deletePlanBaseFalses(String ids, String deleteBy,String deleteIp);
	
	public int deletePlanDetailFalses(String ids, String deleteBy,String deleteIp);
	
	public int savePlan(C1PlanRequest entity);
	
	public int savePlanBase(PlanBaseDTO entity);
	
	public int savePlanDetail(PlanDetailDTO entity);
	
	public int deletePlanExecuteFalses(String ids, String deleteBy,String deleteIp);
	
	public List<PlanExecuteDTO>  findPlanExecuteLists(PlanQueryFilter entity);
	
	public List<PlanDetailDTO>  findPlanDetailLists(PlanQueryFilter entity);
	
	public Page<PlanExecuteDTO> findPlanExecutePages(Page page,PlanQueryFilter entity);
	
	public List<PlanExecuteRecordDTO> findPlanExecuteRecordLists(PlanQueryFilter entity);
	
	public Page<PlanExecuteRecordDTO> findPlanExecuteRecordPages(Page page,PlanQueryFilter entity);
	
	public PlanExecuteRecordDTO getDetailPlanExecuteRecord(String id);

	public PlanExecuteRecordDTO getPlanExecuteRecord(PlanExecuteRecordDTO entity);

	int savePlanExecute(PlanExecuteDTO entity);
	int savePlanExecute1(PlanExecuteDTO entity);
	
	public PlanStatisticsData findPlanStatisticsData(PlanQueryFilter entity);
	
	public List<PlanStatisticsByMon> planDataFromByMon(PlanQueryFilter entity);

	public List<PlanStatisticsByMon> planDataFromByLine(PlanQueryFilter entity);
	
	public List<FlowCountData> planFlowCount(PlanQueryFilter entity);
	
	public List<Map<String,Object>> planFlowCountNew(PlanQueryFilter entity);

	public TopPlanRectifyRate planTopRefromCount(PlanQueryFilter entity);

	Map<String, Object> importData(String fileName, InputStream inputStream, String systemId, String templetId, String createBy,
			String sectionId);

	List<PlanExecuteDTO> findPlanExecuteByIds(String ids);
	
	Map<String, Object> findMonthAndAccumulativeFrom();

	List<PlanStatisticsByMon> planDataFromBySection(PlanQueryFilter entity);
	
	List<Object> planFlowCount1C(PlanQueryFilter entity);

	Map<String, Object> importData1(String fileName, InputStream inputStream, String systemId, String templetId,
			String createBy, String sectionId);
	
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String systemId, String templetId,
			String createBy, String sectionId);

	int savePlanExecuteByConfig(PlanExecuteDTO entity);

	public void auditRegister(PlanQueryFilter entity, String string, String string2, String string3);

}
