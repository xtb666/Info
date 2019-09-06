package com.togest.service;

import java.io.InputStream;
import java.util.Map;

import com.togest.request.C1TaskRequest;
import com.togest.request.TaskRequest;

public interface C1PlanService extends PlanCommonService{

	// 解析处里计划Excel数据
	public Map<String, Object> analyzePlanExcelData(String originalFilename, InputStream inputStream, String createBy,
			String systemId, String sectionId); 
	
	public void fillRecordPlan(C1TaskRequest entity,String deptId);
	public int completionConfirmationPlan(TaskRequest entity,String deptId);
}
