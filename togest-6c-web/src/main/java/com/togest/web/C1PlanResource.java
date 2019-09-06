package com.togest.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.DataControlPut;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.function.OptionalUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.PlanBaseDTO;
import com.togest.domain.PlanDetailDTO;
import com.togest.domain.PlanExecuteDTO;
import com.togest.domain.PlanExecuteRecordDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.C1PlanRequest;
import com.togest.request.C1TaskRequest;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.PlanQueryFilter;
import com.togest.request.RePlanTaskRequest;
import com.togest.request.TaskRequest;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.C1PlanService;
import com.togest.service.DefectConfService;
import com.togest.service.PlanCommonService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("1C")
public class C1PlanResource {

	@Autowired
	private C1PlanService service;
	@Autowired
	private DefectConfService defectConfService;
	@Autowired
	private MetadataConfigClient metadataConfigClient;
	@Autowired
	private MetadataUtil metadataUtil;

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
		entity.setUserId(info.getId());
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
	public RestfulResponse<Boolean> fillRecordPlan(HttpServletRequest request, @RequestBody C1TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		if(StringUtil.isNotEmpty(info)){
		entity.setUserName(info.getName());
		entity.setUserId(info.getId());
		entity.setSectionId(info.getSectionId());
		entity.setDeptId(info.getDeptId());
		}
		service.fillRecordPlan(entity,info.getDeptId());
		return new RestfulResponse<Boolean>(true);
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
		int i = service.completionConfirmationPlan(entity,info.getDeptId());
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
	@RequestMapping(value = "plan", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除计划")
	public RestfulResponse<Integer> deletePlanDTO(HttpServletRequest request,
			String id, String deleteBy) {
		int i = service.deletePlanBaseFalses(id, deleteBy, request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "plan", method = RequestMethod.POST)
	@ApiOperation(value = "保存计划")
	public RestfulResponse<Integer> savePlanDTO(HttpServletRequest request,
			@RequestBody C1PlanRequest entity ) {
		
		int i = service.savePlan(entity);
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "plan/planBase", method = RequestMethod.POST)
	@ApiOperation(value = "保存计划")
	public RestfulResponse<Integer> savePlanBaseDTO(HttpServletRequest request,
			@RequestBody PlanBaseDTO entity ) {
		int i = service.savePlanBase(entity);
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "plan/planBase/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> planBaseEnclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		PlanBaseDTO pB = service.getPlanBase(id);
		SystemResoucesResponse response = OptionalUtils.map(metadataConfigClient.getSystemResources(resouceCode));
		Map<String, Object> map =(Map<String, Object>) ObjectUtils.objectToHashMap(pB);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(metadataUtil.enclosure(map, response.getProps()));
	}
	@RequestMapping(value = "plan/planExecute", method = RequestMethod.GET)
	@ApiOperation(value = "获取计划数据")
	public RestfulResponse<PlanExecuteDTO> getPlanExecuteDTO(HttpServletRequest request,
			String id) {
		PlanExecuteDTO entity = service.getPlanExecute(id);
		return new RestfulResponse<PlanExecuteDTO>(entity);
	}
	@RequestMapping(value = "plan/planExecute", method = RequestMethod.POST)
	@ApiOperation(value = "保存计划数据")
	public RestfulResponse<Integer> savePlanExecuteDTO(HttpServletRequest request,
			PlanExecuteDTO entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setSectionId(info.getSectionId());
		int count = service.savePlanExecute(entity);
		return new RestfulResponse<Integer>(count);
	}
	@RequestMapping(value = "plan/planExecuteSection", method = RequestMethod.POST)
	@ApiOperation(value = "保存计划数据")
	public RestfulResponse<Integer> saveSectionPlanExecuteDTO(HttpServletRequest request,
			PlanExecuteDTO entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setSectionId(info.getSectionId());
		int count = ((PlanCommonService)service).savePlanExecute(entity);
		return new RestfulResponse<Integer>(count);
	}
	@RequestMapping(value = "plan/planDetail", method = RequestMethod.GET)
	@ApiOperation(value = "获取计划数据")
	public RestfulResponse<PlanDetailDTO> getPlanDetailDTO(HttpServletRequest request,
			String id) {
		PlanDetailDTO entity = service.getPlanDetail(id);
		return new RestfulResponse<PlanDetailDTO>(entity);
	}
	@RequestMapping(value = "plan/planDetail", method = RequestMethod.POST)
	@ApiOperation(value = "保存计划数据")
	public RestfulResponse<Boolean> savePlanDetailDTO(HttpServletRequest request,
			@RequestBody PlanDetailDTO entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setSectionId(info.getSectionId());
		service.savePlanDetail(entity);
		return new RestfulResponse<Boolean>(true);
	}
	@RequestMapping(value = "plan/planDetail", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除计划")
	public RestfulResponse<Integer> deletePlanDetailDTO(HttpServletRequest request,
			String id, String deleteBy) {
		int i = service.deletePlanDetailFalses(id, deleteBy, request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "plan/planDetail/lists", method = RequestMethod.GET)
	@ApiOperation(value = "获取计划数据")
	public RestfulResponse<List<PlanDetailDTO>> getPlanDetailDTOLists(HttpServletRequest request,
			PlanQueryFilter entity) {
		List<PlanDetailDTO> list = service.findPlanDetailLists(entity);
		return new RestfulResponse<List<PlanDetailDTO>>(list);
	}
	@RequestMapping(value = "plan/planExecute", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除计划")
	public RestfulResponse<Integer> deletePlanExecuteDTO(HttpServletRequest request,
			String id, String deleteBy) {
		int i = service.deletePlanExecuteFalses(id, deleteBy, request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	// 计划数据集合
	@DataControlPut(authCode="LOOK_PLAN_INFORMATION_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/lists", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据集合")
	public RestfulResponse<List<PlanBaseDTO>> findPlanLists(HttpServletRequest request, PlanQueryFilter entity) {
		List<PlanBaseDTO> list = service.findPlanBaseLists(entity);
		return new RestfulResponse<List<PlanBaseDTO>>(list);
	}

	// 计划数据分页
	@DataControlPut(authCode="LOOK_PLAN_INFORMATION_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/pages", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据分页")
	public RestfulResponse<Page<PlanBaseDTO>> findPlanPages(HttpServletRequest request,Page page, PlanQueryFilter entity) {
		Page<PlanBaseDTO> pg = service.findPlanBasePages(page, entity);
		return new RestfulResponse<Page<PlanBaseDTO>>(pg);
	}
	// 计划数据集合
	@DataControlPut(authCode="LOOK_PLAN_INFORMATION_D_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planExecute/lists", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据集合")
	public RestfulResponse<List<PlanExecuteDTO>> findPlanExecuteLists(HttpServletRequest request, PlanQueryFilter entity) {
		List<PlanExecuteDTO> list = service.findPlanExecuteLists(entity);
		return new RestfulResponse<List<PlanExecuteDTO>>(list);
	}
	
	@RequestMapping(value = "plan/planExecuteRecord/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> planExecuteEnclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		PlanExecuteRecordDTO pE = service.getDetailPlanExecuteRecord(id);
		Map<String, List<Map<String, Object>>> map = defectConfService.handleEnclosure(resouceCode, pE);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);
	}
	
	// 计划数据分页
	@RequestMapping(value = "plan/planExecuteRecord", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据")
	public RestfulResponse<PlanExecuteRecordDTO> getPlanExecuteRecord(String id) {
		PlanExecuteRecordDTO entity = service.getDetailPlanExecuteRecord(id);
		return new RestfulResponse<PlanExecuteRecordDTO>(entity);
	}
	@RequestMapping(value = "plan/planExecuteRecords", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据")
	public RestfulResponse<PlanExecuteRecordDTO> getPlanExecuteRecord(PlanExecuteRecordDTO entity) {
		PlanExecuteRecordDTO dto = service.getPlanExecuteRecord(entity);
		return new RestfulResponse<PlanExecuteRecordDTO>(dto);
	}
	// 计划数据分页
	@DataControlPut(authCode="LOOK_PLAN_INFORMATION_D_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planExecuteRecord/pages", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据分页")
	public RestfulResponse<Page<PlanExecuteRecordDTO>> findPlanExecuteRecordPages(HttpServletRequest request,Page page, PlanQueryFilter entity) {
		Page<PlanExecuteRecordDTO> pg = service.findPlanExecuteRecordPages(page, entity);
		return new RestfulResponse<Page<PlanExecuteRecordDTO>>(pg);
	}
	// 计划数据集合
	@DataControlPut(authCode="LOOK_PLAN_INFORMATION_D_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planExecuteRecord/lists", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据集合")
	public RestfulResponse<List<PlanExecuteRecordDTO>> findPlanExecuteRecordLists(HttpServletRequest request, PlanQueryFilter entity) {
		List<PlanExecuteRecordDTO> list = service.findPlanExecuteRecordLists(entity);
		return new RestfulResponse<List<PlanExecuteRecordDTO>>(list);
	}
	
	// 计划数据分页
	@DataControlPut(authCode="LOOK_PLAN_INFORMATION_D_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "plan/planExecute/pages", method = RequestMethod.GET)
	@ApiOperation(value = "计划数据分页")
	public RestfulResponse<Page<PlanExecuteDTO>> findPlanExecutePages(HttpServletRequest request,Page page, PlanQueryFilter entity) {
		Page<PlanExecuteDTO> pg = service.findPlanExecutePages(page, entity);
		return new RestfulResponse<Page<PlanExecuteDTO>>(pg);
	}
	
	@RequestMapping(value = "plan/import", method = RequestMethod.POST)
	@ApiOperation(value = "计划数据导入")
	public RestfulResponse<Map<String, Object>> importPlan(
			HttpServletRequest request, String fileName, MultipartFile file,
			String createBy, String systemId) {
		UserInfo info = TokenUtil.getUser(request);
		String sectionId = info.getSectionId();
		Map<String, Object> map = null;
		if (file != null) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = file.getOriginalFilename();
				}
				map = service.analyzePlanExcelData(fileName, file
						.getInputStream(), createBy, systemId,sectionId);
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}
}
/*@RequestMapping(value = "plan/import", method = RequestMethod.POST)
@ApiOperation(value = "计划数据导入")
public RestfulResponse<Map<String, Object>> importPlan(
		HttpServletRequest request, MultipartFile file,String systemId, String templetId,
		String fileName,String createBy) {
	Map<String, Object> map = new HashMap<String, Object>();
	if (file != null ) {
		try {
			if (StringUtil.isBlank(fileName)) {
				fileName = file.getOriginalFilename();
			}
			map = service.importData(fileName, file
					.getInputStream(),systemId, templetId, createBy,
					TokenUtil.getUser(request).getSectionId());
		} catch (IOException e) {
			Shift.fatal(StatusCode.FAIL);
		}
	}
	return new RestfulResponse<Map<String, Object>>(map);
}*/

