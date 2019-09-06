package com.togest.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.DataControlPut;
import com.togest.code.client.ExportClient;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.function.OptionalUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.PlanBaseDTO;
import com.togest.domain.PlanExecuteDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.C1PlanRequest;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.PlanQueryFilter;
import com.togest.request.RePlanTaskRequest;
import com.togest.request.TaskRequest;
import com.togest.response.SystemResoucesResponse;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.PlanStatisticsByMon;
import com.togest.response.statistics.PlanStatisticsData;
import com.togest.response.statistics.TopPlanRectifyRate;
import com.togest.service.PlanService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

import io.swagger.annotations.ApiOperation;


@RestController
public class PlanResource {
	@Autowired
	private PlanService service;
	@Autowired
	private MetadataConfigClient metadataConfigClient;
	@Autowired
	private MetadataUtil metadataUtil;
	@Autowired
	protected ExportClient exportService;
	
	@RequestMapping(value = "plan/start", method = RequestMethod.POST)
	@ApiOperation(value = "计划发布")
	public RestfulResponse<Integer> startPlan(HttpServletRequest request,
			@RequestBody FlowStartUserDataRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setSectionId(info.getSectionId());
		int i = service.startPlan(entity);
		return new RestfulResponse<Integer>(i);
	}

	@RequestMapping(value = "plan/reStart", method = RequestMethod.POST)
	@ApiOperation(value = "计划重新发布")
	public RestfulResponse<Integer> reStartPlan(HttpServletRequest request, @RequestBody RePlanTaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int i = service.reStartPlan(entity);
		return new RestfulResponse<Integer>(i);
	}

	// 计划审核
	@RequestMapping(value = "plan/audit", method = RequestMethod.POST)
	@ApiOperation(value = "计划审核")
	public RestfulResponse<Integer> auditPlan(HttpServletRequest request, @RequestBody TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setUserId(info.getId());
		entity.setSectionId(info.getSectionId());
		int i = service.auditPlan(entity);
		return new RestfulResponse<Integer>(i);
	}

	// 添乘记录填写
	@RequestMapping(value = "plan/fillRecord", method = RequestMethod.POST)
	@ApiOperation(value = "添乘记录填写")
	public RestfulResponse<Integer> fillRecordPlan(HttpServletRequest request, @RequestBody TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		if(StringUtil.isNotEmpty(info)){
		entity.setUserName(info.getName());
		entity.setUserId(info.getId());
		entity.setSectionId(info.getSectionId());
		}
		service.fillRecordPlan(entity);
		return new RestfulResponse<Integer>(1);
	}

	// 计划完成确认
	@RequestMapping(value = "plan/completionConfirmation", method = RequestMethod.POST)
	@ApiOperation(value = "计划完成确认")
	public RestfulResponse<Integer> completionConfirmationPlan(HttpServletRequest request,
			@RequestBody TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		if(StringUtil.isNotEmpty(info)){
		entity.setUserName(info.getName());
		entity.setUserId(info.getId());
		entity.setSectionId(info.getSectionId());
		}
		int i = service.completionConfirmationPlan(entity);
		return new RestfulResponse<Integer>(i);
	}


	// 获取计划
	@RequestMapping(value = "plan", method = RequestMethod.GET)
	@ApiOperation(value = "获取计划数据")
	public RestfulResponse<PlanBaseDTO> getPlanLists(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		PlanBaseDTO entity = service.getPlanBase(id);
		return new RestfulResponse<PlanBaseDTO>(entity);
	}
	
	@RequestMapping(value = "plan", method = RequestMethod.POST)
	@ApiOperation(value = "保存计划")
	public RestfulResponse<Integer> savePlanDTO(HttpServletRequest request,
			C1PlanRequest entity ) {
		int i = service.savePlan(entity);
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "plan", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除计划")
	public RestfulResponse<Integer> deletePlanDTO(HttpServletRequest request, String id, String deleteBy) {
		int i = service.deletePlanBaseFalses(id, deleteBy, request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "plan/planExecute/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> planExecuteEnclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		PlanExecuteDTO pE = service.getPlanExecute(id);
		SystemResoucesResponse response = OptionalUtils.map(metadataConfigClient.getSystemResources(resouceCode));
		Map<String, Object> map =(Map<String, Object>) ObjectUtils.objectToHashMap(pE);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(metadataUtil.enclosure(map, response.getProps()));
	}
	@RequestMapping(value = "plan/planExecute", method = RequestMethod.GET)
	@ApiOperation(value = "获取计划")
	public RestfulResponse<PlanExecuteDTO> getPlanExecuteDTO(HttpServletRequest request, String id ) {
		PlanExecuteDTO pE = service.getPlanExecute(id);
		return new RestfulResponse<PlanExecuteDTO>(pE);
	}
	@RequestMapping(value = "plan/planExecute", method = RequestMethod.POST)
	@ApiOperation(value = "保存计划")
	public RestfulResponse<Integer> savePlanExecuteDTO(HttpServletRequest request, @RequestBody PlanExecuteDTO entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setCreateBy(info.getId());
		entity.setSectionId(info.getSectionId());
		int i = service.savePlanExecuteByConfig(entity);
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "plan/planExecute", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除计划")
	public RestfulResponse<Integer> deletePlanExecuteDTO(HttpServletRequest request, String id, String deleteBy) {
		int i = service.deletePlanExecuteFalses(id, deleteBy, request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}

	// 计划数据集合
	@DataControlPut(authCode="",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/lists", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据集合")
	public RestfulResponse<List<PlanBaseDTO>> findPlanLists(HttpServletRequest request, PlanQueryFilter entity) {
		List<PlanBaseDTO> list = service.findPlanBaseLists(entity);
		return new RestfulResponse<List<PlanBaseDTO>>(list);
	}

	// 计划数据分页
	@DataControlPut(authCode="",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/pages", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据分页")
	public RestfulResponse<Page<PlanBaseDTO>> findPlanPages(HttpServletRequest request, Page page,
			PlanQueryFilter entity) {
		Page<PlanBaseDTO> pg = service.findPlanBasePages(page, entity);
		return new RestfulResponse<Page<PlanBaseDTO>>(pg);
	}

	// 计划数据集合
	@DataControlPut(authCode="",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planExecute/lists", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据集合")
	public RestfulResponse<List<PlanExecuteDTO>> findPlanExecuteLists(HttpServletRequest request,
			PlanQueryFilter entity) {
		List<PlanExecuteDTO> list = service.findPlanExecuteLists(entity);
		return new RestfulResponse<List<PlanExecuteDTO>>(list);
	}

	// 计划数据分页
	@DataControlPut(authCode="",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planExecute/pages", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据分页")
	public RestfulResponse<Page<PlanExecuteDTO>> findPlanExecutePages(HttpServletRequest request, Page page,
			PlanQueryFilter entity) {
		Page<PlanExecuteDTO> pg = service.findPlanExecutePages(page, entity);
		return new RestfulResponse<Page<PlanExecuteDTO>>(pg);
	}

	@RequestMapping(value = "plan/import", method = RequestMethod.POST)
	@ApiOperation(value = "计划数据导入")
	public RestfulResponse<Map<String, Object>> importPlan(HttpServletRequest request, String fileName,
			MultipartFile file, String createBy, String systemId) {
		Map<String, Object> map = null;
		if (file != null) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = file.getOriginalFilename();
				}
				UserInfo info = TokenUtil.getUser(request);
				map = service.analyzeExcelData(fileName, file.getInputStream(), createBy, systemId, info.getSectionId());
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}

	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/findPlanStatisticsData", method = RequestMethod.GET)
	@ApiOperation(value = "计划统计数据")
	public RestfulResponse<PlanStatisticsData> findPlanStatisticsData(HttpServletRequest request,
			PlanQueryFilter entity) {
		PlanStatisticsData psd = service.findPlanStatisticsData(entity);
		return new RestfulResponse<PlanStatisticsData>(psd);
	}
	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planDataFromByMon", method = RequestMethod.GET)
	@ApiOperation(value = "计划统计月柱图")
	public RestfulResponse<List<PlanStatisticsByMon>> planDataFromByMon(HttpServletRequest request,
			PlanQueryFilter entity) {
		List<PlanStatisticsByMon> list = service.planDataFromByMon(entity);
		return new RestfulResponse<List<PlanStatisticsByMon>>(list);
	}
	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planDataFromByLine", method = RequestMethod.GET)
	@ApiOperation(value = "计划统计线柱图")
	public RestfulResponse<List<PlanStatisticsByMon>> planDataFromByLine(HttpServletRequest request,
			PlanQueryFilter entity) {
		List<PlanStatisticsByMon> list = service.planDataFromByLine(entity);
		return new RestfulResponse<List<PlanStatisticsByMon>>(list);
	}
	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planFlowCount", method = RequestMethod.GET)
	@ApiOperation(value = "计划流程统计")
	public RestfulResponse<List<FlowCountData>> planFlowCount(HttpServletRequest request,
			PlanQueryFilter entity) {
		List<FlowCountData> list = service.planFlowCount(entity);
		return new RestfulResponse<List<FlowCountData>>(list);
	}
	
	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planFlowCount/new", method = RequestMethod.GET)
	@ApiOperation(value = "计划流程统计new")
	public RestfulResponse<List<Map<String,Object>>> planFlowCountNew(PlanQueryFilter entity){
		List<Map<String, Object>> list = service.planFlowCountNew(entity);
		return new RestfulResponse<List<Map<String,Object>>>(list);
	}
	
	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planTopRefromCount", method = RequestMethod.GET)
	@ApiOperation(value = "计划整改率最高")
	public RestfulResponse<TopPlanRectifyRate> planTopRefromCount(HttpServletRequest request,
			PlanQueryFilter entity) {
		TopPlanRectifyRate list = service.planTopRefromCount(entity);
		return new RestfulResponse<TopPlanRectifyRate>(list);
	}
	
	@RequestMapping(value = "plan/templet/data", method = RequestMethod.POST)
	@ApiOperation(value = "导入计划数据")
	public RestfulResponse<Map<String, Object>> importPlanData(
			HttpServletRequest request, MultipartFile file,String systemId, String templetId,
			String fileName,String createBy) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file != null ) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = file.getOriginalFilename();
				}
				map = service.importDataByConfig(fileName, file
						.getInputStream(),systemId, templetId, createBy,
						TokenUtil.getUser(request).getSectionId());
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}
	
	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "plan/export/data", method = RequestMethod.GET)
	@ApiOperation(value = "导出计划(段)")
	public ResponseEntity exportPlanData(HttpServletRequest request, PlanQueryFilter entity, String templetId) {
			
		List<PlanExecuteDTO> list = service.findPlanExecuteLists(entity);
		ResponseEntity re = null;
		try {
			List<Map<String,Object>> listMap = new ArrayList<>();
			list.forEach(ped->
				listMap.add((Map<String, Object>) ObjectUtils.objectToHashMap(ped)));
			byte[] bt = exportService.exportDataByMapTemplet(listMap, templetId);
			re = FileDownload.fileDownload(bt, "检测计划导出数据(段).xls");
		} catch (Exception e) {
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	@RequestMapping(value = "plan/findMonthAndAccumulativeFrom", method = RequestMethod.GET)
	@ApiOperation(value = "计划表")
	public RestfulResponse<Map<String, Object>> findMonthAndAccumulativeFrom(HttpServletRequest request) {
		Map<String, Object> map = service.findMonthAndAccumulativeFrom();
		return new RestfulResponse<Map<String, Object>>(map);
	}
	
	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planDataFromBySection", method = RequestMethod.GET)
	@ApiOperation(value = "计划统计段柱图")
	public RestfulResponse<List<PlanStatisticsByMon>> planDataFromBySection(HttpServletRequest request,
			PlanQueryFilter entity) {
		List<PlanStatisticsByMon> list = service.planDataFromBySection(entity);
		return new RestfulResponse<List<PlanStatisticsByMon>>(list);
	}
	@DataControlPut(authCode="COMPREHENSIVE_ANALYSIS_6C_DETAIL",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planFlowCount1C", method = RequestMethod.GET)
	@ApiOperation(value = "计划流程统计")
	public RestfulResponse<List<Object>> planFlowCount1C(HttpServletRequest request,
			PlanQueryFilter entity) {
		List<Object> list = service.planFlowCount1C(entity);
		return new RestfulResponse<List<Object>>(list);
	}
	
	@DataControlPut(authCode = "LOOK_EILL_IN_THE_RECORD_2C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "plan/execute/export/data", method = RequestMethod.GET)
	@ApiOperation(value = "2C计划执行导出")
	public ResponseEntity export2CPlanExecuteData(HttpServletRequest request, PlanQueryFilter entity,@RequestParam("templateId") String templetId) {

		List<PlanExecuteDTO> list = service.findPlanExecuteLists(entity);
		ResponseEntity re = null;
		try {
			List<Map<String,Object>> listMap = new ArrayList<>();
			list.forEach(ped->
				listMap.add((Map<String, Object>) ObjectUtils.objectToHashMap(ped)));
			byte[] bt = exportService.exportDataByMapTemplet(listMap, templetId);
			re = FileDownload.fileDownload(bt, "2C计划执行导出数据.xls");
		} catch (Exception e) {
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	
	@DataControlPut(authCode = "LOOK_PLAN_INFORMATION_2C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "plan/information/export/data", method = RequestMethod.GET)
	@ApiOperation(value = "2C计划信息导出")
	public ResponseEntity export2CPlanInformationData(HttpServletRequest request, PlanQueryFilter entity,@RequestParam("templetId") String templetId) {

		List<PlanExecuteDTO> list = service.findPlanExecuteLists(entity);
		ResponseEntity re = null;
		try {
			List<Map<String,Object>> listMap = new ArrayList<>();
			list.forEach(ped->
				listMap.add((Map<String, Object>) ObjectUtils.objectToHashMap(ped)));
			byte[] bt = exportService.exportDataByMapTemplet(listMap, templetId);
			re = FileDownload.fileDownload(bt, "2C计划信息导出数据.xls");
		} catch (Exception e) {
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
}
