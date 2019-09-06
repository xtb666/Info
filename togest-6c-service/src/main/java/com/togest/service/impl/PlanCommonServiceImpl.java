package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.DeptFeignService;
import com.togest.clien.sevice.SupplyArmFeignService;
import com.togest.clien.sevice.WorkflowFeignService;
import com.togest.client.resquest.DefectFlowrRejectRequest;
import com.togest.client.resquest.FlowStartRequest;
import com.togest.client.resquest.ProcessInstanceDelRequest;
import com.togest.client.resquest.TaskHandleRequest;
import com.togest.code.client.ExportClient;
import com.togest.code.client.ImportClient;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectConstants;
import com.togest.config.DefectSystem;
import com.togest.config.InfoStatus;
import com.togest.config.PlanAuditStatus;
import com.togest.config.PlanStatus;
import com.togest.dao.C1InfoDao;
import com.togest.dao.C2InfoDao;
import com.togest.dao.C4InfoDao;
import com.togest.dao.CInfoDao;
import com.togest.dao.PlanBaseDao;
import com.togest.dao.PlanDetailDao;
import com.togest.dao.PlanExecuteDao;
import com.togest.dao.PlanExecuteRecordDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.C2Info;
import com.togest.domain.C2InfoDTO;
import com.togest.domain.C4Info;
import com.togest.domain.C4InfoDTO;
import com.togest.domain.CInfo;
import com.togest.domain.CheckTrainDTO;
import com.togest.domain.DefectFlow;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.IdGen;
import com.togest.domain.Naming;
import com.togest.domain.Page;
import com.togest.domain.PlanBaseDTO;
import com.togest.domain.PlanDetailDTO;
import com.togest.domain.PlanExecuteDTO;
import com.togest.domain.PlanExecuteRecordDTO;
import com.togest.domain.SimplePage;
import com.togest.domain.TaskFlowResponse;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.C1PlanRequest;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.PlanQueryFilter;
import com.togest.request.RePlanTaskRequest;
import com.togest.request.TaskRejectRequest;
import com.togest.request.TaskRequest;
import com.togest.response.PlanTaskFlowResponse;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.PlanMonData;
import com.togest.response.statistics.PlanMonthAccumulativeData;
import com.togest.response.statistics.PlanStatisticsByMon;
import com.togest.response.statistics.PlanStatisticsData;
import com.togest.response.statistics.TopPlanRectifyRate;
import com.togest.service.CheckTrainService;
import com.togest.service.DefectFlowService;
import com.togest.service.DictionaryService;
import com.togest.service.LineService;
import com.togest.service.PlanCommonService;
import com.togest.utils.PageUtils;

public class PlanCommonServiceImpl implements PlanCommonService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanCommonServiceImpl.class);
	@Autowired
	protected PlanBaseDao planBaseDao;
	@Autowired
	protected PlanDetailDao planDetailDao;
	@Autowired
	protected PlanExecuteDao planExecuteDao;
	@Autowired
	protected PlanExecuteRecordDao planExecuteRecordDao;
	@Autowired
	protected CheckTrainService checkTrainService;
	@Autowired
	protected ImportClient importService;
	@Autowired
	protected DictionaryService dictionaryService;
	@Autowired
	protected LineService lineService;
	@Autowired
	protected DeptFeignService deptFeignService;
	@Autowired
	protected WorkflowFeignService workflowFeignService;
	@Autowired
	protected DefectFlowService defectFlowService;
	@Autowired
	protected C1InfoDao c1InfoDao;
	@Autowired
	protected C2InfoDao c2InfoDao;
	@Autowired
	protected C4InfoDao c4InfoDao;
	@Autowired
	protected CInfoDao cInfoDao;
	@Autowired
	protected ExportClient exportService;
	@Autowired
	protected SupplyArmFeignService supplyArmFeignService;
	@Value("${my.repeatDefect.isSuiDe}")
	private Boolean isSuiDe = false;

	protected static final String NAME = "name";
	protected static final String ID = "id";
	protected static final String LEADERPASS = "true";
	protected static final String RECORDSTATUS_NO = "0";
	protected static final String RECORDSTATUS_OK = "1";
	protected static final Integer FLOW_TAG_C = 0;
	
	@Override
	@Transactional
	public int startPlan(FlowStartUserDataRequest entity) {
		List<FlowStartRequest> flows = new ArrayList<FlowStartRequest>();
		List<String> list = Arrays.asList(entity.getId().split(","));
		List<PlanExecuteDTO> planExecuteList = planExecuteDao.getListByKeys(list);
		for (PlanExecuteDTO planExecute : planExecuteList) {
			FlowStartRequest request = new FlowStartRequest();
			request.setUserId(entity.getUserId());
			request.setProcessDefKey(entity.getFlowKey());
			request.setBusinessKey(planExecute.getId());
			// request.setTaskUsers(entity.getDeptId());
			request.setSectionId(entity.getSectionId());
			// 流程主题
			String processName = "";
			if (StringUtil.isEmpty(planExecute.getPlanBasePlanDate())) {
				processName = planExecute.getPlanDetailLineName();
			} else {
				processName = DateUtils.format(planExecute.getPlanBasePlanDate(), DateUtils.DATE_FORMAT_YMD) + "_"
						+ planExecute.getPlanDetailLineName();
			}
			request.setProcessName(processName);
			flows.add(request);
		}
		planBaseDao.updatePlanStatus(CollectionUtils.distinctExtractToList(planExecuteList, "planBaseId", String.class),
				PlanStatus.PlanAudit.getStatus());
		planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanAudit.getStatus());
		planExecuteDao.updateFlowTag(list, String.valueOf(FLOW_TAG_C));
		RestfulResponse<List<Map<String, String>>> response = workflowFeignService.startProcessInstance(flows);
		if (RestfulResponse.DEFAULT_OK != response.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL, response.getMessage());
		}
		List<Map<String, String>> flowLists = response.getData();
		if (StringUtil.isNotEmpty(flowLists)) {
			defectFlowService.insert(flowLists);
		}
		return list.size();
	}

	@Override
	@Transactional
	public int reStartPlan(RePlanTaskRequest entity) {
		PlanExecuteDTO planExecute = entity.getPlanExecute();
		savePlanExecuteByConfig(planExecute);
		planExecuteDao.updateAuditStatus(Arrays.asList(entity.getId()), null);
		planExecuteDao.updateExecuteStatus(Arrays.asList(entity.getId()), PlanStatus.PlanAudit.getStatus());
		entity.setLeaderPass("true");
		completeTask(entity, false);
		return 1;
	}

	@Override
	@Transactional
	public int auditPlan(TaskRequest entity) {
		List<String> list = Arrays.asList(entity.getId().split(","));
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
		for (String planExecuteId : list) {
			PlanExecuteDTO planExecute = new PlanExecuteDTO();
			planExecute.setDeptId(entity.getTaskUsers());
			planExecute.setId(planExecuteId);
			planExecute.setSectionId(entity.getSectionId());
			planExecuteDao.update(planExecute);
		}
		if (isPass) {
			planExecuteDao.updateAuditData(entity.getId(), entity.getUserId() + "_" + entity.getUserName(), new Date());
			planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanExecution.getStatus());
			planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanAudit.getStatus());
		} else {
			planExecuteDao.updateAuditData(entity.getId(), "", null);
			planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanRegister.getStatus());
			planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanAuditNotPass.getStatus());
		}

		int rs = completeTask(entity, isPass);
		if (rs != list.size()) {
			Shift.fatal(StatusCode.FAIL);
		}
		return list.size();
	}

	@Override
	@Transactional
	public void fillRecordPlan(TaskRequest entity) {
		Date date = processDate(new Date());
		List<String> list = Arrays.asList(entity.getId().split(","));
		PlanExecuteDTO planExecute = planExecuteDao.getByKey(entity.getId());
		// 执行情况
		if (StringUtil.isNotEmpty(entity.getComment())) {
			planExecute.setImplementation(entity.getComment());
		}else{
		planExecute.setImplementation(entity.getImplementation());
		}
		planExecute.setActualAddDate(entity.getActualAddDate());
		planExecute.setActualAddTrafficNumber(entity.getActualAddTrainNumber());
		planExecute.setActualAddTrainNumber(entity.getActualAddTrainNumber());
		planExecute.setActualCheckRegion(entity.getActualCheckRegion());
		planExecute.setActualPatcher(entity.getActualPatcher());
		planExecute.setEquNo(entity.getEquNo());
		planExecute.setEquOperation(entity.getEquOperation());
		//planExecute.setPatcher(entity.getUserId()+"_"+entity.getUserName());
		planExecuteDao.update(planExecute);
		// 添乘人
		// planExecuteDao.updateAddData(entity.getId(),entity.getUserName(),new Date());
		planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanComplete.getStatus());
		planExecuteDao.updateAuditStatus(list, null);

		entity.setLeaderPass(LEADERPASS);
		completeTask(entity, true);
	}

	@Override
	@Transactional
	public int completionConfirmationPlan(TaskRequest entity) {
		List<String> list = Arrays.asList(entity.getId().split(","));
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
		if (isPass) {
			planExecuteDao.updateConfirmationData(entity.getId(), entity.getUserId() + "_" + entity.getUserName(),
					new Date());
			planExecuteDao.updateExecuteStatus(list, PlanStatus.Cannel.getStatus());
			planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanComplete.getStatus());
		} else {
			// planExecuteDao.updateConfirmationData(entity.getId(),"",null);
			// planExecuteDao.updateAddData(entity.getId(),"",null);
			planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanExecution.getStatus());
			planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanCompleteNotPass.getStatus());
		}
		int rs = completeTask(entity, isPass);
		if (rs != list.size()) {
			Shift.fatal(StatusCode.FAIL);
		}
		return list.size();
	}
	
	@Transactional
	public int archivePlan(TaskRejectRequest entity, String taskId) {
		if (StringUtil.isEmpty(entity.getComment())) {
			entity.setComment("");
		}
		List<String> list = Arrays.asList(entity.getId().split(","));
		String activityNodeId = entity.getActivityNodeId();
		String userId = entity.getUserId();
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));

		List<TaskFlowResponse> taskFlows = planExecuteDao.getTaskFlowResponseByIds(list);

		if (isPass) {

		} else {
			if (StringUtil.isEmpty(activityNodeId)) {
				return 0;
			}
			List<DefectFlowrRejectRequest> dFRList = new ArrayList<DefectFlowrRejectRequest>();
			for (TaskFlowResponse taskFlow : taskFlows) {
				DefectFlowrRejectRequest request = new DefectFlowrRejectRequest();
				request.setActivityNodeId(activityNodeId);
				request.setTaskId(taskId);
				request.setProcessInstanceId(taskFlow.getProcessInstanceId());
				request.setUserId(userId);
				request.setComment(entity.getComment());
				dFRList.add(request);
			}

			String executeAuditStatus = PlanAuditStatus.PlanAuditNotPass.getStatus();
			String executeStatus = null;
			switch (activityNodeId) {
			case "usertask5":
				executeStatus = PlanStatus.PlanComplete.getStatus();
				break;
			case "usertask4":
				executeStatus = PlanStatus.PlanExecution.getStatus();
				break;
			case "usertask3":
				executeStatus = PlanStatus.PlanAudit.getStatus();
				break;
			case "usertask1":
				executeStatus = PlanStatus.PlanRegister.getStatus();
				break;
			}

			planExecuteDao.updateAuditStatus(list, executeAuditStatus);
			planExecuteDao.updateExecuteStatus(list, executeStatus);
			RestfulResponse<List<Map<String, String>>> response = workflowFeignService.rejectTask(dFRList);
			if (RestfulResponse.DEFAULT_OK != response.getErrorCode()) {
				Shift.fatal(StatusCode.FAIL, response.getMessage());
			}
			List<Map<String, String>> flowLists = response.getData();
			defectFlowService.update(flowLists);
		}
		return list.size();
	}

	public int completeTask(TaskRequest entity, boolean fg) {
		List<PlanTaskFlowResponse> taskFlows = planExecuteDao
				.findPlanTaskFlow(Arrays.asList(entity.getId().split(",")));
		List<TaskHandleRequest> flows = new ArrayList<TaskHandleRequest>();
		for (PlanTaskFlowResponse taskFlow : taskFlows) {
			TaskHandleRequest request = new TaskHandleRequest();
			request.setUserId(entity.getUserId());
			request.setLeaderPass(entity.getLeaderPass());
			request.setTaskId(taskFlow.getTaskId());
			request.setProcessInstanceId(taskFlow.getProcessInstanceId());
			request.setComment(entity.getComment());
			request.setSectionId(entity.getSectionId());
			if (fg) {
				request.setTaskUsers(taskFlow.getDeptId());
			}
			if (StringUtil.isNotBlank(entity.getTaskUsers())) {
				request.setTaskUsers(entity.getTaskUsers());
			}
			flows.add(request);
		}
		RestfulResponse<List<Map<String, String>>> response = workflowFeignService.completeTask(flows);
		if (RestfulResponse.DEFAULT_OK != response.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL);
		}
		List<Map<String, String>> flowLists = response.getData();
		if (StringUtil.isNotEmpty(flowLists)) {
			defectFlowService.update(flowLists);
			return flowLists.size();
		} else {
			return flows.size();
		}

	}

	@Override
	public Map<String, Object> analyzeExcelData(String originalFilename, InputStream inputStream, String createBy,
			String systemId, String sectionId) {

		Date createDate = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatPlanBase = 0;
		int repeatPlanDetail = 0;
		StringBuilder repeat = new StringBuilder();
		StringBuilder errorStr = new StringBuilder();
		StringBuilder repeatMsg = new StringBuilder();
		boolean flag = false;
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(new String[] { "direction" }).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);

		List<String> properties = Arrays.asList(new String[] { "planDate", "trainNumber", "trainName", "lineName",
				"directionName", "checkRegion", "deptName", "startStation", "endStation", "contacts", "remark" });
		List<Map<String, Object>> list = null;
		Map<String, String> checkTrainMap = new HashMap<String, String>();
		CheckTrainDTO checkTrainDTO = new CheckTrainDTO();
		checkTrainDTO.setSystemId(systemId);
		List<CheckTrainDTO> checkTrainList = checkTrainService.findList(checkTrainDTO);
		if (StringUtil.isNotEmpty(checkTrainList)) {
			for (CheckTrainDTO checkTrain : checkTrainList) {
				checkTrainMap.put(checkTrain.getTrainNum(), checkTrain.getId());
			}
		}
		try {
			list = importService.importBaseData(properties, originalFilename, inputStream, 1);

			if (StringUtil.isNotEmpty(list)) {
				Map<String, String> deptMap = new HashMap<String, String>();
				List<String> deptNames = CollectionUtils.distinctExtractToList(list, properties.get(6), String.class);
				if (StringUtil.isNotEmpty(deptNames)) {
					List<Map<String, String>> tempDept = deptFeignService
							.getIdByName(CollectionUtils.convertToString(deptNames, ","), sectionId).getData();
					if (StringUtil.isNotEmpty(tempDept)) {
						for (Map<String, String> map2 : tempDept) {
							deptMap.put(map2.get(NAME), map2.get(ID));
						}
					}
				}
				for (int i = 0; i < list.size(); i++) {
					flag = false;
					Map<String, Object> obj = list.get(i);
					if (StringUtil.isEmpty(obj)) {
						continue;
					}
					PlanBaseDTO planBase = new PlanBaseDTO();
					PlanDetailDTO planDetail = new PlanDetailDTO();
					PlanExecuteDTO planExecute = new PlanExecuteDTO();

					// 检测日期
					Object planDate = obj.get(properties.get(0));
					if (StringUtil.isNotEmpty(planDate)) {
						Date date = DateUtils.stringToDate(planDate.toString().trim());
						if (StringUtil.isNotEmpty(date)) {
							planBase.setPlanDate(processDate(date));
						} else {
							errorStr.append("第" + (i + 2) + "行计划检测日期有误：" + planDate.toString() + "<br/>");
							flag = true;
						}
					} else {
						errorStr.append("第" + (i + 2) + "行计划检测日期为空<br/>");
						flag = true;
					}
					// 往返车次
					Object trainNumber = obj.get(properties.get(1));
					if (StringUtil.isNotEmpty(trainNumber)) {
						planBase.setTrainNumber(trainNumber.toString().trim());
					}
					// 车辆编号
					Object trainName = obj.get(properties.get(2));
					if (StringUtil.isNotEmpty(trainName)) {
						String trainId = checkTrainMap.get(trainName.toString().trim());
						if (StringUtil.isNotEmpty(trainId)) {
							planBase.setTrainId(trainId);
						} else {
							errorStr.append("第" + (i + 2) + "行车辆编号：" + trainName.toString() + "不存在<br/>");
							flag = true;
						}
					}
					// 添乘人
					Object patcher = obj.get(properties.get(9));
					if (StringUtil.isNotEmpty(patcher)) {
						planDetail.setPatcher(patcher.toString().trim());
						planExecute.setPatcher(patcher.toString().trim());
					}
					// 备注
					Object remark = obj.get(properties.get(10));
					if (StringUtil.isNotEmpty(remark)) {
						planBase.setRemark(remark.toString().trim());
					}
					// 线路
					Object lineName = obj.get(properties.get(3));
					String lineId = null;
					if (StringUtil.isNotEmpty(lineName)) {// 线路
						lineId = lineService.getIdByName(lineName.toString().trim());
						if (StringUtil.isNotBlank(lineId)) {
							planDetail.setLineId(lineId);
						} else {
							errorStr.append("第" + (i + 2) + "行线路：" + lineName + "不存在<br/>");
							flag = true;
						}
					} else {
						errorStr.append("第" + (i + 2) + "行存在线路没有填写线路信息<br/>");
						flag = true;
					}
					// 行别
					Object directionName = obj.get(properties.get(4));
					String direction = null;
					if (StringUtil.isNotEmpty(directionName)) {// 行别
						Map<String, String> map1 = dictMap.get("direction");
						if (map1 != null) {
							direction = map1.get(directionName.toString().trim());
						}
						if (StringUtil.isNotBlank(direction)) {
							planDetail.setDirection(direction);
						} else {
							errorStr.append("第" + (i + 2) + "行行别：" + directionName + "不存在<br/>");
							flag = true;
						}
					} else {
						errorStr.append("第" + (i + 2) + "行行别信息为空<br/>");
						flag = true;
					}
					// 检测区间
					Object checkRegion = obj.get(properties.get(5));
					if (StringUtil.isNotEmpty(checkRegion)) {
						planDetail.setCheckRegion(checkRegion.toString().trim());
					} else {
						errorStr.append("第" + (i + 2) + "行检测区间数据为空<br/>");
						flag = true;
					}
					// 添乘部门
					Object deptName = obj.get(properties.get(6));
					if (StringUtil.isNotEmpty(deptName)) {
						String deptId = deptMap.get(deptName.toString().trim());
						if (StringUtil.isNotBlank(deptId)) {
							planExecute.setDeptId(deptId);
						} else {
							errorStr.append("第" + (i + 2) + "行添乘部门：" + directionName + "不存在<br/>");
							flag = true;
						}
					} else {
						errorStr.append("第" + (i + 2) + "行添乘部门信息为空<br/>");
						flag = true;
					}
					// 上车站
					Object startStation = obj.get(properties.get(7));
					if (StringUtil.isNotEmpty(startStation)) {
						planExecute.setStartStation(startStation.toString().trim());
					}
					// 下车站
					Object endStation = obj.get(properties.get(8));
					if (StringUtil.isNotEmpty(endStation)) {
						planExecute.setEndStation(endStation.toString().trim());
					}

					if (!flag) {
						boolean planRepeat = false;
						String planBaseId = null;
						planBase.setSystemId(systemId);

						planBase.setId(IdGen.uuid());
						planBase.setSystemId(systemId);
						planBase.setPlanStatus(PlanStatus.PlanRegister.getStatus());
						planBase.setCreateDate(createDate);
						planBaseId = planBase.getId();

						planDetail.setPlanBaseId(planBaseId);
						String planDetailId = null;
						planDetail.setId(IdGen.uuid());
						// planDetail.setPlanBaseId(planBaseId);
						planDetail.setCreateDate(createDate);

						planDetailId = planDetail.getId();

						CInfo info = null;
						if (DefectSystem.defectC1.getStatus().equals(systemId)) {
							info = new C1InfoDTO();
						}
						if (DefectSystem.defectC2.getStatus().equals(systemId)) {
							info = new C2InfoDTO();
						}
						if (DefectSystem.defectC4.getStatus().equals(systemId)) {
							info = new C4InfoDTO();
						}

						info.setId(UUID.randomUUID().toString());
						info.setPlanId(planDetailId);
						info.setCheckDate(planExecute.getPlanBasePlanDate());
						info.setLineId(lineId);
						info.setDirection(planDetail.getDirection());
						info.setStartStation(planExecute.getStartStation());
						info.setEndStation(planExecute.getEndStation());
						info.setSystemId(systemId);
						info.setSectionId(sectionId);
						info.setCreateBy(createBy);

						planExecute.setSectionId(sectionId);
						planExecute.setPlanBaseId(planBaseId);
						planExecute.setPlanDetailId(planDetailId);

						planExecute.setPlanBasePlanDate(planBase.getPlanDate());
						planExecute.setPlanBaseTrainId(planBase.getTrainId());
						planExecute.setPlanDetailLineId(planDetail.getLineId());
						planExecute.setPlanDetailDirection(planDetail.getDirection());
						planExecute.setSystemId(systemId);
						planExecute.setActualCheckRegion(planDetail.getCheckRegion());
						planExecute.setPlanDetailCheckRegion(planDetail.getCheckRegion());

						PlanExecuteDTO pe = checkPlanExecuteRP(planExecute);

						if (StringUtil.isNotEmpty(pe)) {
							planRepeat = true;
						} else {
							planBaseDao.insert(planBase);
							planDetailDao.insert(planDetail);
							saveCInfo(info, systemId);

							planExecute.setId(IdGen.uuid());
							planExecute.setExecuteStatus(PlanStatus.PlanRegister.getStatus());
							planExecute.setCreateDate(createDate);
							planExecuteDao.insert(planExecute);
							planRepeat = false;
						}

						if (planRepeat) {
							repeatPlanBase++;
							repeatMsg.append("此计划已经存在: 计划日期为"
									+ DateUtils.format(planBase.getPlanDate(), DateUtils.DATE_FORMAT_YMD) + ", 车次为"
									+ planBase.getTrainNumber() + ", 车号为" + obj.get(properties.get(2)).toString()
									+ ",添乘部门为" + planExecute.getDeptId().toString() + ",线路为"
									+ lineName.toString().trim() + ",行别为" + directionName.toString().trim() + ",添乘人为"
									+ planExecute.getPatcher().toString() + "<br/>");
						}
					}

				}
			} else {
				errorStr.append("解析数据为空<br/>");
			}
			if (repeatPlanBase != 0) {
				repeat.append("计划重复" + repeatPlanBase + "条");
				if (repeatPlanDetail != 0) {
					repeat.append(", 线路重复" + repeatPlanDetail + "条");
				}
			} else if (repeatPlanDetail != 0) {
				repeat.append("线路重复" + repeatPlanDetail + "条");
			}
		} catch (Exception e) {
			errorStr.append(e.getMessage());
			LOGGER.error("Plan-Exception : " + e.getMessage());
		}

		map.put(DefectConstants.IMPORT_COUNT, count);
		map.put(DefectConstants.IMPORT_REPEAT, repeat.toString());
		map.put(DefectConstants.IMPORT_REPEAT_MSG, repeatMsg);
		map.put(DefectConstants.IMPORT_ERROR_MSG, errorStr.toString());

		return map;
	}

	public Date processDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public PlanBaseDTO checkPlanBase(PlanBaseDTO entity) {
		PlanBaseDTO dto = new PlanBaseDTO();
		dto.setPlanDate(processDate(entity.getPlanDate()));
		dto.setTrainId(entity.getTrainId());
		dto.setSystemId(entity.getSystemId());
		return planBaseDao.getByEntity(dto);
	}

	public PlanExecuteDTO checkPlanExecuteRP(PlanExecuteDTO entity) {
		PlanExecuteDTO pe = new PlanExecuteDTO();
		pe.setDeptId(entity.getDeptId());
		pe.setSystemId(entity.getSystemId());
		pe.setPatcher(entity.getPatcher());
		pe.setPlanBasePlanDate(entity.getPlanBasePlanDate());
		pe.setPlanBaseTrainId(entity.getPlanBaseTrainId());
		pe.setPlanDetailLineId(entity.getPlanDetailLineId());
		pe.setPlanDetailDirection(entity.getPlanDetailDirection());
		pe.setPlanBaseTrainNumber(entity.getPlanBaseTrainNumber());
		pe.setPlanDetailCheckRegion(entity.getPlanDetailCheckRegion());
		return planExecuteDao.checkPlanExecuteRP(pe);
	}

	@Override
	@DictAggregation
	public List<PlanBaseDTO> findPlanBaseLists(PlanQueryFilter entity) {
		return planBaseDao.findLists(entity);
	}

	@Override
	@DictAggregation
	public Page<PlanBaseDTO> findPlanBasePages(Page page, PlanQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(planBaseDao.findLists(entity));
	}

	@Override
	@DictAggregation
	public PlanBaseDTO getPlanBase(String id) {
		return planBaseDao.getByKey(id);
	}

	@Override
	@Transactional
	public int deletePlanBaseFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			List<ProcessInstanceDelRequest> list = new ArrayList<>();
			List<String> planBaseIds = Arrays.asList(ids.split(","));
			List<PlanExecuteDTO> planExecutes = planExecuteDao.getByPlanBaseIds(planBaseIds);
			List<String> peIds = CollectionUtils.distinctExtractToList(planExecutes, "id", String.class);
			if (ListUtils.isNotEmpty(peIds)) {
				List<PlanTaskFlowResponse> ptfList = planExecuteDao.findPlanTaskFlow(peIds);

				List<PlanExecuteRecordDTO> perList = planExecuteRecordDao.getDetailByPeKeys(peIds);
				ProcessInstanceDelRequest pidr;
				if (ListUtils.isNotEmpty(peIds)) {
					for (PlanTaskFlowResponse ptf : ptfList) {
						if (StringUtil.isBlank(ptf.getProcessInstanceId()))
							continue;
						pidr = new ProcessInstanceDelRequest();
						pidr.setProcessInstanceId(ptf.getProcessInstanceId());
						list.add(pidr);
					}
				}
				for (PlanExecuteRecordDTO per : perList) {
					if (StringUtil.isBlank(per.getProcessInstanceId()))
						continue;
					pidr = new ProcessInstanceDelRequest();
					pidr.setProcessInstanceId(per.getProcessInstanceId());
					list.add(pidr);
				}
			}
			c1InfoDao.deleteFalsesByPlanBaseId(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			c2InfoDao.deleteFalsesByPlanBaseId(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			c4InfoDao.deleteFalsesByPlanBaseId(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			cInfoDao.deleteFalsesByPlanBaseId(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());

			planDetailDao.deleteFalsesByPlanBaseId(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			planExecuteDao.deleteFalsesByPlanBaseId(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			planExecuteRecordDao.deleteFalsesByPlanBaseId(Arrays.asList(ids.split(",")), deleteBy, deleteIp,
					new Date());
			RestfulResponse<List<Map<String, String>>> deleteTask = workflowFeignService.deleteTask(list);
			return planBaseDao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
		}
		return 0;
	}

	@Override
	@DictAggregation
	public List<PlanExecuteDTO> findPlanExecuteLists(PlanQueryFilter entity) {
		return planExecuteDao.findLists(entity);
	}

	@Override
	@DictAggregation
	public List<PlanExecuteRecordDTO> findPlanExecuteRecordLists(PlanQueryFilter entity) {
		return planExecuteRecordDao.findLists(entity);
	}

	public void handlePlanExecuteRecord(List<PlanExecuteRecordDTO> list) {
		if (StringUtil.isNotEmpty(list)) {
			List<String> iii = new ArrayList<>();
			for (PlanExecuteRecordDTO planExecuteRecordDTO : list) {
				if (StringUtil.isBlank(planExecuteRecordDTO.getProcessInstanceId())) {
					iii.add(planExecuteRecordDTO.getPlanExecuteId());
				}
			}
			if (StringUtil.isNotEmpty(iii)) {
				List<DefectFlow> s = defectFlowService.getBykeys(iii);

				Map<String, String> map = new HashMap<>();
				if (StringUtil.isNotEmpty(s)) {
					for (DefectFlow defectFlow : s) {
						map.put(defectFlow.getId(), defectFlow.getProcessInstanceId());
					}
				}
				for (PlanExecuteRecordDTO planExecuteRecordDTO : list) {
					if (StringUtil.isBlank(planExecuteRecordDTO.getProcessInstanceId())) {
						planExecuteRecordDTO.setProcessInstanceId(map.get(planExecuteRecordDTO.getPlanExecuteId()));
					}
				}

			}
		}

	}

	@Override
	@DictAggregation
	public Page<PlanExecuteRecordDTO> findPlanExecuteRecordPages(Page page, PlanQueryFilter entity) {
		PageUtils.setPage(page);
		PageUtils.buildPage(planExecuteRecordDao.findLists(entity));
		Page<PlanExecuteRecordDTO> pg = PageUtils.buildPage(planExecuteRecordDao.findLists(entity));
		handlePlanExecuteRecord(pg.getList());
		return pg;

	}

	@Override
	@DictAggregation
	public Page<PlanExecuteDTO> findPlanExecutePages(Page page, PlanQueryFilter entity) {
		Page<PlanExecuteDTO> pg = new Page<PlanExecuteDTO>();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		SimplePage sp = new SimplePage();
		sp.setPageNo(pageNo);
		sp.setPageSize(pageSize);
		sp.setStartNum((pageNo - 1) * pageSize);
		sp.setEndNum(pageNo * pageSize);
		entity.setPage(sp);
		int total = planExecuteDao.findListsCounts(entity);
		List<PlanExecuteDTO> list = planExecuteDao.findLists(entity);
		pg.setList(list);
		pg.setTotal((long) total);
		pg.setPageNo(sp.getPageNo());
		pg.setPageSize(sp.getPageSize());
		return pg;
	}

	@Override
	public int deletePlanExecuteFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			List<ProcessInstanceDelRequest> list = new ArrayList<>();
			List<String> peList = Arrays.asList(ids.split(","));
			List<DefectFlow> dfList = defectFlowService.getBykeys(peList);
			ProcessInstanceDelRequest pidr;
			for (DefectFlow df : dfList) {
				if (StringUtil.isBlank(df.getProcessInstanceId()))
					continue;
				pidr = new ProcessInstanceDelRequest();
				pidr.setProcessInstanceId(df.getProcessInstanceId());
				list.add(pidr);
			}
			workflowFeignService.deleteTask(list);
			c2InfoDao.deleteFalsesByPlanExecuteId(peList, deleteBy, deleteIp, new Date());
			c4InfoDao.deleteFalsesByPlanExecuteId(peList, deleteBy, deleteIp, new Date());
			cInfoDao.deleteFalsesByPlanExecuteId(peList, deleteBy, deleteIp, new Date());
			planExecuteRecordDao.deleteFalsesByPlanExecuteId(peList, deleteBy, deleteIp, new Date());
			return planExecuteDao.deleteFalses(peList, deleteBy, deleteIp, new Date());
		}
		return 0;
	}

	@Override
	@DictAggregation
	public PlanExecuteDTO getPlanExecute(String id) {
		PlanExecuteDTO entity = planExecuteDao.getByKey(id);
		if (StringUtil.isNotEmpty(entity)) {
			PlanExecuteRecordDTO dto = new PlanExecuteRecordDTO();
			dto.setPlanExecuteId(id);
			List<PlanExecuteRecordDTO> planExecuteRecords = planExecuteRecordDao.findList(dto);
			entity.setPlanExecuteRecords(planExecuteRecords);
		}
		return entity;
	}

	@Override
	@DictAggregation
	public PlanExecuteRecordDTO getDetailPlanExecuteRecord(String id) {
		PlanExecuteRecordDTO entity = planExecuteRecordDao.getDetailByKey(id);
		if (StringUtil.isNotEmpty(entity) && StringUtil.isBlank(entity.getProcessInstanceId())) {
			DefectFlow defectFlow = defectFlowService.getByKey(entity.getPlanExecuteId());
			if (StringUtil.isNotEmpty(defectFlow)) {
				entity.setProcessInstanceId(defectFlow.getProcessInstanceId());
			}

		}
		return entity;
	}

	@Override
	@DictAggregation
	public PlanExecuteRecordDTO getPlanExecuteRecord(PlanExecuteRecordDTO entity) {
		PlanExecuteRecordDTO dto = planExecuteRecordDao.getByEntity(entity);
		return dto;
	}

	@Override
	public int savePlan(C1PlanRequest entity) {
		Date date = processDate(new Date());

		PlanBaseDTO planBase = entity.getPlanBase();

		List<PlanDetailDTO> planDetail = entity.getPlanDetail();

		PlanExecuteDTO planExecute = entity.getPlanExecute();

		if (StringUtil.isBlank(planBase.getId())) {
			planBase.setId(IdGen.uuid());
			planBase.setCreateDate(date);
			planBaseDao.insert(planBase);
			if (StringUtil.isNotEmpty(planDetail)) {
				for (PlanDetailDTO planDetailDTO : planDetail) {
					planDetailDTO.setPlanBaseId(planBase.getId());
					planDetailDTO.setId(IdGen.uuid());
					planDetailDao.update(planDetailDTO);
					List<Naming> namings = planDetailDTO.getDepts();
					if (StringUtil.isNotEmpty(namings)) {
						for (Naming naming : namings) {
							PlanExecuteDTO planExecuteDTO = new PlanExecuteDTO();
							planExecuteDTO.setId(IdGen.uuid());
							planExecuteDTO.setResponsibleDeptId(naming.getId());
							planExecuteDTO.setPlanBaseId(planBase.getId());
							planExecuteDTO.setPlanDetailId(planDetailDTO.getId());
							planExecuteDTO.setCreateDate(date);
							planExecuteDao.insert(planExecuteDTO);
						}
					}
				}
			}
		} else {
			planBaseDao.update(planBase);
			planExecuteDao.update(planExecute);
			if (StringUtil.isNotEmpty(planDetail)) {
				for (PlanDetailDTO planDetailDTO : planDetail) {
					planDetailDao.update(planDetailDTO);
				}
			}
		}
		return 0;
	}

	@Override
	@DictAggregation
	public List<PlanDetailDTO> findPlanDetailLists(PlanQueryFilter entity) {
		return planDetailDao.findLists(entity);
	}

	@Override
	@DictAggregation
	public PlanDetailDTO getPlanDetail(String id) {
		return planDetailDao.getByKey(id);
	}

	@Override
	public int deletePlanDetailFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			c1InfoDao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			c2InfoDao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			c4InfoDao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			cInfoDao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			planExecuteDao.deleteFalsesByPlanDetailId(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
			return planDetailDao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
		}
		return 0;
	}

	@Override
	public int savePlanDetail(PlanDetailDTO entity) {
		Date date = processDate(new Date());
		if (StringUtil.isBlank(entity.getId())) {
			entity.setId(IdGen.uuid());
			planDetailDao.insert(entity);
		}else{
			planDetailDao.update(entity);
			planExecuteDao.deleteFalsesByPlanDetailId(Arrays.asList(entity.getId()), null, null, new Date());
		}
			List<Naming> list = entity.getDepts();
			if (StringUtil.isNotEmpty(list)) {
				for (Naming naming : list) {
					PlanExecuteDTO dto = new PlanExecuteDTO();
					dto.preInsert();
					dto.setPlanBaseId(entity.getPlanBaseId());
					dto.setPlanDetailId(entity.getId());
					dto.setResponsibleDeptId(naming.getId());
					dto.setExecuteStatus(PlanStatus.PlanRegister.getStatus());
					dto.setPatcher(entity.getPatcher());
					dto.setCreateDate(date);
					dto.setSystemId(DefectSystem.defectC1.getStatus());
					
					//dto.setFlowTag(Integer.valueOf(1));
					dto.setSectionId(entity.getSectionId());
					planExecuteDao.insert(dto);
				}
			}
				
		return 0;
	}
	@Override
	public int savePlanBase(PlanBaseDTO entity) {
		Date date = processDate(new Date());
		if (StringUtil.isNotBlank(entity.getId())) {
			planBaseDao.update(entity);
		} else {
			entity.setCreateIp(IdGen.uuid());
			entity.setCreateDate(date);
			planBaseDao.insert(entity);
		}
		return 0;
	}

	// 判断是否为绥德项目计划新增编辑
	@Override
	public int savePlanExecuteByConfig(PlanExecuteDTO entity) {
		if (isSuiDe) {
			return savePlanExecute(entity);
		} else {
			return savePlanExecute1(entity);
		}
	}

	/**
	 * 绥德计划新增
	 */
	@Override
	@Transactional
	public int savePlanExecute(PlanExecuteDTO entity) {
		if (entity != null) {
			String planBaseId = entity.getPlanBaseId();
			String planDetailId = entity.getPlanDetailId();
			String systemId = entity.getSystemId();
			String createBy = entity.getCreateBy();

			PlanBaseDTO pbDTO = new PlanBaseDTO();
			PlanDetailDTO pdDTO = new PlanDetailDTO();
			PlanExecuteDTO peDTO = new PlanExecuteDTO();
			Date date = processDate(new Date());

			pbDTO.setTrainNumber(entity.getPlanBaseTrainNumber());
			pbDTO.setPlanDate(processDate(entity.getPlanBasePlanDate()));
			pbDTO.setTrainId(entity.getPlanBaseTrainId());
			pbDTO.setSystemId(systemId);
//		PlanBaseDTO planBase = planBaseDao.getByEntity(pbDTO);

			pbDTO.setContacts(entity.getPlanBaseContacts());
			if(StringUtil.isBlank(entity.getPlanBaseRemark())){
				pbDTO.setRemark(entity.getRemark());
			}else{
				pbDTO.setRemark(entity.getPlanBaseRemark());
			}
			pbDTO.setSystemId(entity.getSystemId());
			pbDTO.setCreateDate(date);
			pbDTO.setPlanStatus(PlanStatus.PlanRegister.getStatus());
			pbDTO.setContacts(entity.getPlanBaseContacts());

			if (planBaseId != null) {
				pbDTO.setId(planBaseId);
				planBaseDao.update(pbDTO);
			} else {
				pbDTO.setId(IdGen.uuid());
				planBaseDao.insert(pbDTO);
				planBaseId = pbDTO.getId();
			}

			pdDTO.setLineId(entity.getPlanDetailLineId());
			pdDTO.setDirection(entity.getPlanDetailDirection());
			pdDTO.setPlanBaseId(planBaseId);
//		PlanDetailDTO planDetail = planDetailDao.getByEntity(pdDTO);
			pdDTO.setCheckRegion(entity.getPlanDetailCheckRegion());
			pdDTO.setPatcher(entity.getPatcher());
			pdDTO.setCreateDate(date);
			if (planDetailId == null) {

				pdDTO.setId(pbDTO.getId());
				planDetailDao.insert(pdDTO);
				planDetailId = pdDTO.getId();

				if (StringUtil.isBlank(entity.getId())) {
					CInfo info = null;
					if (DefectSystem.defectC1.getStatus().equals(systemId)) {
						info = new C1InfoDTO();
					}
					if (DefectSystem.defectC2.getStatus().equals(systemId)) {
						info = new C2InfoDTO();
					}
					if (DefectSystem.defectC4.getStatus().equals(systemId)) {
						info = new C4InfoDTO();
					}

					info.setId(pbDTO.getId());
					info.setPlanId(planDetailId);
					info.setCheckDate(entity.getPlanBasePlanDate());
					info.setLineId(entity.getPlanDetailLineId());
					info.setDirection(entity.getPlanDetailDirection());
					info.setStartStation(entity.getStartStation());
					info.setEndStation(entity.getEndStation());
					info.setSystemId(systemId);
					info.setSectionId(entity.getSectionId());
					info.setCreateDate(date);
					info.setCreateBy(createBy);

					saveCInfo(info, systemId);
				}
			} else {
				pdDTO.setId(planDetailId);
				planDetailDao.update(pdDTO);
			}

			if (StringUtil.isNotBlank(entity.getId())) {
				planExecuteDao.update(entity);
			} else {
				PlanExecuteDTO pe = checkPlanExecuteRP(entity);

				if (pe != null)
					Shift.fatal(StatusCode.PLAN_REPEAT);

				if(StringUtil.isBlank(entity.getRemark())){
					entity.setRemark(entity.getPlanBaseRemark());
				}
				entity.setExecuteStatus(PlanStatus.PlanRegister.getStatus());
				entity.setCreateDate(date);
				//entity.setAddDate(date);
				entity.setPlanBaseId(planBaseId);
				entity.setPlanDetailId(planDetailId);
				entity.setId(pbDTO.getId());
				planExecuteDao.insert(entity);
			}
			return 1;
		}
		return 0;
	}

	/**
	 * 区别上个新增方法，修改起始站终点站，新增检测里程(非绥德)
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public int savePlanExecute1(PlanExecuteDTO entity) {
		if (entity != null) {
			String planBaseId = entity.getPlanBaseId();
			String planDetailId = entity.getPlanDetailId();
			String systemId = entity.getSystemId();
			String createBy = entity.getCreateBy();

			PlanBaseDTO pbDTO = new PlanBaseDTO();
			PlanDetailDTO pdDTO = new PlanDetailDTO();
			PlanExecuteDTO peDTO = new PlanExecuteDTO();
			Date date = processDate(new Date());

			pbDTO.setTrainNumber(entity.getPlanBaseTrainNumber());
			pbDTO.setPlanDate(processDate(entity.getPlanBasePlanDate()));
			pbDTO.setTrainId(entity.getPlanBaseTrainId());
			pbDTO.setSystemId(systemId);
//		PlanBaseDTO planBase = planBaseDao.getByEntity(pbDTO);

			pbDTO.setContacts(entity.getPlanBaseContacts());
			if(StringUtil.isBlank(entity.getPlanBaseRemark())){
				pbDTO.setRemark(entity.getRemark());
			}else{
				pbDTO.setRemark(entity.getPlanBaseRemark());
			}
			pbDTO.setSystemId(entity.getSystemId());
			pbDTO.setCreateDate(date);
			pbDTO.setPlanStatus(PlanStatus.PlanRegister.getStatus());
			pbDTO.setContacts(entity.getPlanBaseContacts());

			if (planBaseId != null) {
				pbDTO.setId(planBaseId);
				planBaseDao.update(pbDTO);
			} else {
				pbDTO.setId(IdGen.uuid());
				planBaseDao.insert(pbDTO);
				planBaseId = pbDTO.getId();
			}

			pdDTO.setLineId(entity.getPlanDetailLineId());
			pdDTO.setDirection(entity.getPlanDetailDirection());
			pdDTO.setPlanBaseId(planBaseId);
//		PlanDetailDTO planDetail = planDetailDao.getByEntity(pdDTO);
			pdDTO.setCheckRegion(entity.getPlanDetailCheckRegion());
			pdDTO.setPatcher(entity.getPatcher());

			// 检测里程
			pdDTO.setDetectMileage(entity.getPlanDetailDetectMileage());
				
			// 检测区段(起始站)
			if(StringUtil.isNotBlank(entity.getPlanDetailStartStation())) {
				 pdDTO.setStartStation(entity.getPlanDetailStartStation()); 
				 RestfulResponse<List<Map<String, String>>> restfulResponse = supplyArmFeignService.getNameByIds(entity.getPlanDetailStartStation());
				 if(null != restfulResponse ) {
					 pdDTO.setStartStationName(restfulResponse.getData().get(0).get("name"));
				 }
			}
			// 检测区段(终点站)
			if(StringUtil.isNotBlank(entity.getPlanDetailEndStation())) {
				 pdDTO.setEndStation(entity.getPlanDetailEndStation()); 
				 RestfulResponse<List<Map<String, String>>> restfulResponse = supplyArmFeignService.getNameByIds(entity.getPlanDetailEndStation());
				 if(null != restfulResponse ) {
					 pdDTO.setEndStationName(restfulResponse.getData().get(0).get("name"));
				 }
			}
			/*if (StringUtil.isNotBlank(entity.getPlanDetailCheckRegion())) {
				pdDTO.setCheckRegion(entity.getPlanDetailCheckRegion());
				String checkRegion = entity.getPlanDetailCheckRegion();
				String startStationName = null;
				String endStationName = null;
				String startStation = null;
				String endStation = null;

				List<String> checkRegionList = StringUtil.stringToList(checkRegion.replace("-", ","));
				Map<String, String> psaMap = new HashMap<String, String>();
				if (StringUtil.isNotEmpty(checkRegionList)) {
					List<Map<String, String>> tempPsa = supplyArmFeignService
							.getIdByNames(checkRegion.replace("-", ",")).getData();
					if (StringUtil.isNotEmpty(tempPsa)) {
						for (Map<String, String> map1 : tempPsa) {
							psaMap.put(map1.get(NAME), map1.get(ID));
						}
					}
				}
				if (checkRegionList.size() == 2) {
					startStationName = checkRegionList.get(0);
					startStation = psaMap.get(startStationName);
					endStationName = checkRegionList.get(1);
					endStation = psaMap.get(endStationName);
					if (StringUtil.isNotBlank(startStation) && StringUtil.isNotBlank(endStation)) {
						pdDTO.setStartStation(startStation);
						pdDTO.setEndStation(endStation);
					} else {
						Shift.fatal(StatusCode.STATION_NOT_FIND);
					}
				}else {
					Shift.fatal(StatusCode.STATION_NOT_FIND);
				}
			}*/
			
			pdDTO.setCreateDate(date);
			if (planDetailId == null) {

				pdDTO.setId(pbDTO.getId());
				planDetailDao.insert(pdDTO);
				planDetailId = pdDTO.getId();

				if (StringUtil.isBlank(entity.getId())) {
					CInfo info = null;
					if (DefectSystem.defectC1.getStatus().equals(systemId)) {
						info = new C1InfoDTO();
					}
					if (DefectSystem.defectC2.getStatus().equals(systemId)) {
						info = new C2InfoDTO();
					}
					if (DefectSystem.defectC4.getStatus().equals(systemId)) {
						info = new C4InfoDTO();
					}

					info.setId(pbDTO.getId());
					info.setPlanId(planDetailId);
					info.setCheckDate(entity.getPlanBasePlanDate());
					info.setLineId(entity.getPlanDetailLineId());
					info.setDirection(entity.getPlanDetailDirection());
					info.setStartStation(pdDTO.getStartStation());
					info.setEndStation(pdDTO.getEndStation());
					info.setSystemId(systemId);
					info.setSectionId(entity.getSectionId());
					info.setCreateDate(date);
					info.setCreateBy(createBy);

					saveCInfo(info, systemId);
				}
			} else {
				pdDTO.setId(planDetailId);
				planDetailDao.update(pdDTO);
			}

			if (StringUtil.isNotBlank(entity.getId())) {
				planExecuteDao.update(entity);
			} else {
				PlanExecuteDTO pe = checkPlanExecuteRP(entity);

				if (pe != null)
					Shift.fatal(StatusCode.PLAN_REPEAT);
				
				if(StringUtil.isBlank(entity.getRemark())){
					entity.setRemark(entity.getPlanBaseRemark());
				}
				entity.setExecuteStatus(PlanStatus.PlanRegister.getStatus());
				entity.setCreateDate(date);
			//	entity.setAddDate(date);
				entity.setPlanBaseId(planBaseId);
				entity.setPlanDetailId(planDetailId);
				entity.setId(pbDTO.getId());
				planExecuteDao.insert(entity);
			}
			return 1;
		}
		return 0;
	}

	public void saveCInfo(CInfo info, String systemId) {
		Date date = processDate(new Date());

		info.setCreateDate(date);
		info.setAnalyStatus(InfoStatus.UploadCompleted.getStatus());
		info.setDefectDataStatus(InfoStatus.UploadCompleted.getStatus());
		info.setRawdataStatus(InfoStatus.UploadCompleted.getStatus());

		cInfoDao.insert(info);

		if (DefectSystem.defectC1.getStatus().equals(systemId)) {
			c1InfoDao.insert((C1InfoDTO) info);
		}
		if (DefectSystem.defectC2.getStatus().equals(systemId)) {
			c2InfoDao.insert((C2Info) info);
		}
		if (DefectSystem.defectC4.getStatus().equals(systemId)) {
			c4InfoDao.insert((C4Info) info);
		}
	}

	@Override
	public PlanStatisticsData findPlanStatisticsData(PlanQueryFilter entity) {
		/*
		 * thePsd.setpENum(psdList.stream().mapToInt(planStatisticsData->
		 * planStatisticsData.getNum()).sum());
		 * if(StringUtil.isNotBlank(entity.getSystemId())){
		 * thePsd.setUploadDataNum(psdList.stream().filter(planStatisticsData->"1".
		 * equals(planStatisticsData.getDefectDataStatus()))
		 * .mapToInt(PlanStatisticsData::getNum).sum()); }
		 * thePsd.setReformPENum(psdList.stream().filter(planStatisticsData->"5".equals(
		 * planStatisticsData.getExecuteStatus()))
		 * .mapToInt(PlanStatisticsData::getNum).sum());
		 */
		return planExecuteDao.findPlanStatisticsData(entity);
	}

	@Override
	public List<PlanStatisticsByMon> planDataFromByMon(PlanQueryFilter entity) {
		List<PlanStatisticsByMon> psbmList = new ArrayList<>();
		List<PlanMonData> pBList = planExecuteDao.planDataFromByMon(entity);
		if (StringUtil.isEmpty(pBList)) {
			pBList = new ArrayList<PlanMonData>();
			if (StringUtil.isNotEmpty(entity.getSystemId())) {
				PlanMonData a = new PlanMonData();
				a.setPlanDate(1);
				a.setSystemId(entity.getSystemId());
				pBList.add(a);
			} else {
				for (int i = 1; i <= 3; i++) {
					if (i == 3)
						i = i + 1;
					PlanMonData a = new PlanMonData();
					a.setSystemId("C" + i);
					a.setPlanDate(1);
					pBList.add(a);
				}
			}
		}
		if (StringUtil.isNotEmpty(pBList)) {
			Map<String, List<PlanMonData>> maps = pBList.stream()
					.collect(Collectors.groupingBy(PlanMonData::getSystemId));
			maps.forEach((key, value) -> {
				PlanStatisticsByMon planStatisticsByMon = new PlanStatisticsByMon();
				Map<Integer, List<PlanMonData>> temp = value.stream()
						.filter(planMonData -> StringUtil.isNotEmpty(planMonData.getPlanDate()))
						.collect(Collectors.groupingBy(PlanMonData::getPlanDate));
				List<PlanMonData> list = new ArrayList<>();
				for (int i = 1; i < 13; i++) {
					PlanMonData planMonData = null;
					if (temp.containsKey(i)) {
						planMonData = temp.get(i).get(0);
					} else {
						planMonData = new PlanMonData();
						planMonData.setCancelNum(0);
						planMonData.setSystemId(key);
						planMonData.setPlanDate(i);
						planMonData.setNum(0);
					}
					list.add(planMonData);
				}
				planStatisticsByMon.setName(key);
				planStatisticsByMon.setPmdList(list);
				psbmList.add(planStatisticsByMon);
			});
		}
		return psbmList;
	}

	@Override
	public List<PlanStatisticsByMon> planDataFromByLine(PlanQueryFilter entity) {
		List<PlanStatisticsByMon> psbmList = new ArrayList<>();
		List<PlanMonData> pBList = planExecuteDao.planDataFromByLine(entity);
		if (StringUtil.isNotEmpty(pBList)) {
			Map<String, List<PlanMonData>> maps = pBList.stream()
					.filter(planMonData -> StringUtil.isNotEmpty(planMonData.getLineName()))
					.collect(Collectors.groupingBy(PlanMonData::getLineName));
			maps.forEach((key, value) -> {
				PlanStatisticsByMon planStatisticsByMon = new PlanStatisticsByMon();
				Map<String, List<PlanMonData>> temp1 = value.stream()
						.collect(Collectors.groupingBy(PlanMonData::getSystemId));
				Map<String, List<PlanMonData>> temp = new LinkedHashMap<String, List<PlanMonData>>();
				if (StringUtil.isEmpty(entity.getSystemId())) {
					for (int i = 1; i <= 4; i++) {
						if (i == 3)
							continue;
						if (!temp1.containsKey(("C" + i))) {
							temp.put("C" + i, new ArrayList<PlanMonData>());
						} else {
							temp.put("C" + i, temp1.get("C" + i));
						}
					}
				} else {
					if (!temp1.containsKey(entity.getSystemId())) {
						temp.put(entity.getSystemId(), new ArrayList<PlanMonData>());
					} else {
						temp.put(entity.getSystemId(), temp1.get(entity.getSystemId()));
					}
				}
				List<PlanMonData> list = new ArrayList<>();
				temp.forEach((key1, value1) -> {
					PlanMonData planMonData = new PlanMonData();
					planMonData.setSystemId(key1);
					planMonData.setLineName(key);
					if (value != null)
						planMonData.setLineId(value.get(0).getLineId());
					planMonData.setNum(value1.stream().mapToInt(PlanMonData::getNum).sum());
					planMonData.setCancelNum(value1.stream().mapToInt(PlanMonData::getCancelNum).sum());
					list.add(planMonData);

				});
				planStatisticsByMon.setName(key);
				planStatisticsByMon.setId(list.get(0).getLineId());
				planStatisticsByMon.setPmdList(list);
				psbmList.add(planStatisticsByMon);

			});
		}
		if (StringUtil.isEmpty(pBList)) {
			pBList = new ArrayList<PlanMonData>();
		}
		return psbmList;
	}

	@Override
	public List<FlowCountData> planFlowCount(PlanQueryFilter entity) {
		List<FlowCountData> planFlowCountList = planExecuteDao.planFlowCount(entity);
		Map<String, List<FlowCountData>> map = planFlowCountList.stream()
				.collect(Collectors.groupingBy(FlowCountData::getStatus));
		List<FlowCountData> List = new ArrayList<>();
		for (Integer i = 1; i <= 5; i++) {
			if (map.containsKey(i.toString())) {
				List.addAll(map.get(i.toString()));
			} else {
				FlowCountData fcd = new FlowCountData();
				fcd.setStatus(i.toString());
				List.add(fcd);
			}
		}
		List.forEach(pfc -> {
			String str = pfc.getStatus();
			if (PlanStatus.PlanRegister.getStatus().equals(str)) {
				pfc.setName("计划发布");
			} else if (PlanStatus.PlanAudit.getStatus().equals(str)) {
				pfc.setName("计划审核");
			} else if (PlanStatus.PlanExecution.getStatus().equals(str)) {
				pfc.setName("添乘记录填写");
			} else if (PlanStatus.PlanComplete.getStatus().equals(str)) {
				pfc.setName("计划完成确认");
			} else if (PlanStatus.Cannel.getStatus().equals(str)) {
				pfc.setName("计划结束");
			} else {
			}
		});
		return List;
	}

	@Override
	public TopPlanRectifyRate planTopRefromCount(PlanQueryFilter entity) {
		TopPlanRectifyRate planTopRefromCount = planExecuteDao.planTopRefromCount(entity);
		planTopRefromCount.setNub(planTopRefromCount.getNub() + "%");
		return planTopRefromCount;
	}

	// 判断是否为绥德项目计划导入
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String systemId,
			String templetId, String createBy, String sectionId) {
		if (isSuiDe) {
			return importData(fileName, inputStream, systemId, templetId, createBy, sectionId);
		} else {
			return importData1(fileName, inputStream, systemId, templetId, createBy, sectionId);
		}
	}

	// 绥德导入
	@Transactional
	@Override
	public Map<String, Object> importData(String fileName, InputStream inputStream, String systemId, String templetId,
			String createBy, String sectionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date createDate = new Date();
		int count = 0;
		int repeatPlanBase = 0;
		int repeatPlanDetail = 0;
		StringBuilder repeat = new StringBuilder();
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();

		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(new String[] { "direction" }).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);

		Map<String, String> checkTrainMap = new HashMap<String, String>();
		CheckTrainDTO checkTrainDTO = new CheckTrainDTO();
		checkTrainDTO.setSystemId(systemId);
		List<CheckTrainDTO> checkTrainList = checkTrainService.findList(checkTrainDTO);
		if (StringUtil.isNotEmpty(checkTrainList)) {
			for (CheckTrainDTO checkTrain : checkTrainList) {
				checkTrainMap.put(checkTrain.getTrainNum(), checkTrain.getId());
			}
		}
		try {
			List<Map<String, Object>> result = importService.analyzeExcelData(fileName, inputStream, templetId);
			Map<String, String> deptMap = new HashMap<String, String>();
			List<String> deptNames = CollectionUtils.distinctExtractToList(result, "deptName", String.class);
			if (StringUtil.isNotEmpty(deptNames)) {
				List<Map<String, String>> tempDept = deptFeignService
						.getIdByName(CollectionUtils.convertToString(deptNames, ","), sectionId).getData();
				if (StringUtil.isNotEmpty(tempDept)) {
					for (Map<String, String> map2 : tempDept) {
						deptMap.put(map2.get(NAME), map2.get(ID));
					}
				}
			}
			if (StringUtil.isNotEmpty(result)) {
				List<PlanExecuteDTO> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
					boolean fg = false;
					PlanExecuteDTO entity = dataList.get(i);
					// 检测日期
					Date planDate = entity.getPlanBasePlanDate();
					if (StringUtil.isEmpty(planDate)) {
						errorMsg.append("第" + (i + 2) + "行计划检测日期有误!" + "<br/>");
						fg = true;
					}
					// 往返车次
					String trainNumber = entity.getPlanBaseTrainNumber();
					// 车辆编号
					String trainName = entity.getPlanBaseTrainName();
					String trainId = null;
					if (StringUtil.isNotEmpty(trainName)) {
						trainId = checkTrainMap.get(trainName.toString().trim());
						if (StringUtil.isNotEmpty(trainId)) {
						} else {
							errorMsg.append("第" + (i + 2) + "行车辆编号" + "不存在!<br/>");
							fg = true;
						}
					}
					// 添乘人
					String patcher = entity.getPatcher();
					// 备注
					String remark = entity.getRemark();
					// 线路
					String lineName = entity.getPlanDetailLineName();
					String lineId = null;
					if (StringUtil.isNotEmpty(lineName)) {// 线路
						lineId = lineService.getIdByName(lineName.trim());
						if (StringUtil.isNotBlank(lineId)) {
						} else {
							errorMsg.append("第" + (i + 2) + "行线路：" + "不存在!<br/>");
							fg = true;
						}
					} else {
						errorMsg.append("第" + (i + 2) + "行存在线路没有填写线路信息!<br/>");
						fg = true;
					}
					// 行别
					String directionName = entity.getPlanDetailDirectionName();
					String direction = null;
					if (StringUtil.isNotEmpty(directionName)) {// 行别
						Map<String, String> map1 = dictMap.get("direction");
						if (map1 != null) {
							direction = map1.get(directionName.trim());
						}
						if (StringUtil.isNotBlank(direction)) {
						} else {
							errorMsg.append("第" + (i + 2) + "行行别：" + "不存在!<br/>");
							fg = true;
						}
					} else {
						errorMsg.append("第" + (i + 2) + "行行别信息为空<br/>");
						fg = true;
					}
					// 检测区间
					String checkRegion = entity.getPlanDetailCheckRegion();
					if (StringUtil.isNotEmpty(checkRegion)) {
					} else {
						errorMsg.append("第" + (i + 2) + "行检测区间数据为空<br/>");
						fg = true;
					}
					// 添乘部门
					String deptName = entity.getDeptName();
					String deptId = null;
					if (StringUtil.isNotEmpty(deptName)) {
						deptId = deptMap.get(deptName);
						if (StringUtil.isNotBlank(deptId)) {
						} else {
							errorMsg.append("第" + (i + 2) + "行添乘部门" + "不存在!<br/>");
							fg = true;
						}
					} else {
						errorMsg.append("第" + (i + 2) + "行添乘部门信息为空<br/>");
						fg = true;
					}
					// 上车站
					String startStation = entity.getStartStation();
					if (StringUtil.isNotEmpty(startStation)) {
					}
					// 下车站
					String endStation = entity.getEndStation();
					if (StringUtil.isNotEmpty(endStation)) {
					}
					if (!fg) {
						boolean planRepeat = false;
						String planBaseId = null;
						String planDetailId = null;
						PlanBaseDTO pbDTO = new PlanBaseDTO();
						PlanDetailDTO pdDTO = new PlanDetailDTO();
						PlanExecuteDTO peDTO = new PlanExecuteDTO();
						pbDTO.setPlanDate(planDate);
						pbDTO.setTrainId(trainId);
						pbDTO.setSystemId(systemId);
						pbDTO.setId(IdGen.uuid());
						pbDTO.setTrainNumber(trainNumber);
						pbDTO.setRemark(remark);

						pbDTO.setSystemId(systemId);
						pbDTO.setPlanStatus(PlanStatus.PlanRegister.getStatus());
						pbDTO.setCreateDate(createDate);

						planBaseId = pbDTO.getId();
						planRepeat = false;
						count++;
						pdDTO.setCheckRegion(checkRegion);
						pdDTO.setPatcher(patcher);
						pdDTO.setLineId(lineId);
						pdDTO.setDirection(direction);
						pdDTO.setPlanBaseId(planBaseId);
						pdDTO.setId(IdGen.uuid());
						pdDTO.setCreateDate(createDate);
						planDetailId = pdDTO.getId();
						Date date = processDate(new Date());
						CInfo info = null;
						if (DefectSystem.defectC1.getStatus().equals(systemId)) {
							info = new C1InfoDTO();
						}
						if (DefectSystem.defectC2.getStatus().equals(systemId)) {
							info = new C2InfoDTO();
						}
						if (DefectSystem.defectC4.getStatus().equals(systemId)) {
							info = new C4InfoDTO();
						}
						info.preInsert();
						info.setPlanId(planDetailId);
						info.setCheckDate(planDate);
						info.setLineId(lineId);
						info.setDirection(direction);
						info.setStartStation(startStation);
						info.setEndStation(endStation);
						info.setSystemId(systemId);
						info.setSectionId(sectionId);
						info.setCreateDate(date);
						info.setCreateBy(createBy);
						planRepeat = false;
						peDTO.setPlanBaseId(planBaseId);
						peDTO.setPlanDetailId(planDetailId);

						peDTO.setDeptId(deptId);
						peDTO.setSystemId(systemId);
						peDTO.setPatcher(patcher);
						peDTO.setPlanBasePlanDate(planDate);
						peDTO.setPlanBaseTrainId(trainId);
						peDTO.setPlanDetailDirection(direction);
						peDTO.setPlanDetailLineId(lineId);

						peDTO.setPlanBaseTrainNumber(trainNumber);
						peDTO.setActualCheckRegion(checkRegion);
						peDTO.setSectionId(sectionId);
						peDTO.setStartStation(startStation);
						peDTO.setEndStation(endStation);
						peDTO.setRemark(remark);
						peDTO.setPlanDetailCheckRegion(checkRegion);

						PlanExecuteDTO pe = checkPlanExecuteRP(peDTO);
						if (StringUtil.isNotEmpty(pe)) {
							planRepeat = true;
						} else {
							planBaseDao.insert(pbDTO);
							planDetailDao.insert(pdDTO);
							saveCInfo(info, systemId);

							peDTO.setId(IdGen.uuid());
							peDTO.setExecuteStatus(PlanStatus.PlanRegister.getStatus());
							peDTO.setCreateDate(createDate);
							planExecuteDao.insert(peDTO);
							planRepeat = false;
						}

						if (planRepeat) {
							repeatPlanBase++;
							repeatStr.append("第" + (i + 2) + "行," + "此计划已经存在: 计划日期为"
									+ DateUtils.format(pbDTO.getPlanDate(), DateUtils.DATE_FORMAT_YMD) + ",添乘部门为"
									+ peDTO.getDeptId().toString() + ",线路为" + lineName.toString().trim() + ",行别为"
									+ directionName.toString().trim() + "<br/>");
						}
					}
				}
			} else {
				errorMsg.append("数据为空,请修改后上传");
			}
		} catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
		map.put("repeat", repeat);
		map.put("repeatMsg", repeatStr.toString());
		return map;
	}

	// 区别上个导入放过，修改起始站终点站，新增检测里程
	@Transactional
	@Override
	public Map<String, Object> importData1(String fileName, InputStream inputStream, String systemId, String templetId,
			String createBy, String sectionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date createDate = new Date();
		int count = 0;
		int repeatPlanBase = 0;
		int repeatPlanDetail = 0;
		StringBuilder repeat = new StringBuilder();
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();

		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(new String[] { "direction" }).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);

		Map<String, String> checkTrainMap = new HashMap<String, String>();
		CheckTrainDTO checkTrainDTO = new CheckTrainDTO();
		checkTrainDTO.setSystemId(systemId);
		List<CheckTrainDTO> checkTrainList = checkTrainService.findList(checkTrainDTO);
		if (StringUtil.isNotEmpty(checkTrainList)) {
			for (CheckTrainDTO checkTrain : checkTrainList) {
				checkTrainMap.put(checkTrain.getTrainNum(), checkTrain.getId());
			}
		}
		try {
			List<Map<String, Object>> result = importService.analyzeExcelData(fileName, inputStream, templetId);
			// 部门
			Map<String, String> deptMap = new HashMap<String, String>();
			List<String> deptNames = CollectionUtils.distinctExtractToList(result, "deptName", String.class);
			if (StringUtil.isNotEmpty(deptNames)) {
				List<Map<String, String>> tempDept = deptFeignService
						.getIdByName(CollectionUtils.convertToString(deptNames, ","), sectionId).getData();
				if (StringUtil.isNotEmpty(tempDept)) {
					for (Map<String, String> map2 : tempDept) {
						deptMap.put(map2.get(NAME), map2.get(ID));
					}
				}
			}
			// 站
			List<String> planDetailCheckRegionNames = CollectionUtils.distinctExtractToList(result,
					"planDetailCheckRegion", String.class);
			List<String> checkRegionList = StringUtil
					.stringToList(CollectionUtils.convertToString(planDetailCheckRegionNames, ",").replace("-", ","));
			Set<String> set1 = new HashSet<String>();
			for (String checkRegionStr : checkRegionList) {
				set1.add(checkRegionStr);
			}
			String checkRegionNames = CollectionUtils.convertToString(set1, ",");
			Map<String, String> psaMap = new HashMap<String, String>();
			if (StringUtil.isNotEmpty(checkRegionNames)) {
				List<Map<String, String>> tempPsa = supplyArmFeignService.getIdByNames(checkRegionNames).getData();
				if (StringUtil.isNotEmpty(tempPsa)) {
					for (Map<String, String> map1 : tempPsa) {
						psaMap.put(map1.get(NAME), map1.get(ID));
					}
				}
			}

			if (StringUtil.isNotEmpty(result)) {
				List<PlanExecuteDTO> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
					boolean fg = false;
					PlanExecuteDTO entity = dataList.get(i);
					// 检测日期
					Date planDate = entity.getPlanBasePlanDate();
					if (StringUtil.isEmpty(planDate)) {
						errorMsg.append("第" + (i + 2) + "行计划检测日期有误!" + "<br/>");
						fg = true;
					}
					// 往返车次
					String trainNumber = entity.getPlanBaseTrainNumber();
					// 车辆编号
					/*
					 * String trainName = entity.getPlanBaseTrainName(); String trainId = null; if
					 * (StringUtil.isNotEmpty(trainName)) { trainId =
					 * checkTrainMap.get(trainName.toString().trim()); if
					 * (StringUtil.isNotEmpty(trainId)) { } else { errorMsg.append("第" + (i + 2) +
					 * "行车辆编号" + "不存在!<br/>"); fg = true; } }
					 */
					// 添乘人
				    String patcher = entity.getPatcher();
					// 备注
					String remark = entity.getRemark();
					// 线路
					String lineName = entity.getPlanDetailLineName();
					String lineId = null;
					if (StringUtil.isNotEmpty(lineName)) {// 线路
						lineId = lineService.getIdByName(lineName.trim());
						if (StringUtil.isNotBlank(lineId)) {
						} else {
							errorMsg.append("第" + (i + 2) + "行线路：" + "不存在!<br/>");
							fg = true;
						}
					} else {
						errorMsg.append("第" + (i + 2) + "行存在线路没有填写线路信息!<br/>");
						fg = true;
					}
					// 行别
					String directionName = entity.getPlanDetailDirectionName();
					String direction = null;
					if (StringUtil.isNotEmpty(directionName)) {// 行别
						Map<String, String> map1 = dictMap.get("direction");
						if (map1 != null) {
							direction = map1.get(directionName.trim());
						}
						if (StringUtil.isNotBlank(direction)) {
						} else {
							errorMsg.append("第" + (i + 2) + "行行别：" + "不存在!<br/>");
							fg = true;
						}
					} else {
						errorMsg.append("第" + (i + 2) + "行行别信息为空<br/>");
						fg = true;
					}
					// 检测区段(起始站-终点站)
					String checkRegion = entity.getPlanDetailCheckRegion();
					String startStationName = null;
					String endStationName = null;
					String startStation = null;
					String endStation = null;
					if(DefectSystem.defectC2.getStatus().equals(systemId)) {
						startStationName = entity.getPlanDetailStartStationName(); //上车站名称
						endStationName = entity.getPlanDetailEndStationName(); //下车站名称
						if(StringUtil.isNotBlank(startStationName) && StringUtil.isNotBlank(endStationName)) {
							startStation = psaMap.get(startStationName);
							endStation = psaMap.get(endStationName);
							if(startStation != null && endStation != null) { 
								//2个都为站区
							}else {
								RestfulResponse<List<Map<String, String>>> restfulResponse = null;
								if(startStation == null && endStation != null) {
									restfulResponse = supplyArmFeignService.findByCondition(lineId, direction, startStationName, endStationName, 1);
								}else if(startStation != null && endStation == null) {
									restfulResponse = supplyArmFeignService.findByCondition(lineId, direction, startStationName, endStationName, 2);
								}else {
									restfulResponse = supplyArmFeignService.findByCondition(lineId, direction, startStationName, endStationName, 3);
								}
								if(restfulResponse!= null && !CollectionUtils.isEmpty(restfulResponse.getData())) {
									startStation = restfulResponse.getData().get(0).get("startId");
									endStation = restfulResponse.getData().get(0).get("endId");
								}
							}
						}else {
							if(StringUtil.isBlank(startStationName)) {
								errorMsg.append("第" + (i + 2) + "行上车站数据为空<br/>");
								fg = true;
							}
							if(StringUtil.isBlank(endStationName)) {
								errorMsg.append("第" + (i + 2) + "行下车站数据为空<br/>");
								fg = true;
							}
						}
					}else {
						if (StringUtil.isNotEmpty(checkRegion)) {
							List<String> list1 = StringUtil.stringToList(checkRegion.replace("-", ","));
							if (list1.size() != 2) {
								errorMsg.append("第" + (i + 2) + "行检测区段数据格式错误<br/>");
								fg = true;
							} else {
								startStationName = list1.get(0);
								startStation = psaMap.get(startStationName);
								if (StringUtil.isNotBlank(startStation)) {
								} else {
									errorMsg.append("第" + (i + 2) + "行检测区段起始站" + "不存在!<br/>");
									fg = true;
								}
								endStationName = list1.get(1);
								endStation = psaMap.get(endStationName);
								if (StringUtil.isNotBlank(endStation)) {
								} else {
									errorMsg.append("第" + (i + 2) + "行检测区段终点站" + "不存在!<br/>");
									fg = true;
								}
							}
						} else {
							errorMsg.append("第" + (i + 2) + "行检测区段数据为空<br/>");
							fg = true;
						}
					}
					// 添乘部门
					String deptName = entity.getDeptName();
					String deptId = null;
					if (StringUtil.isNotEmpty(deptName)) {
						deptId = deptMap.get(deptName);
						if (StringUtil.isNotBlank(deptId)) {
						} else {
							errorMsg.append("第" + (i + 2) + "行添乘部门" + "不存在!<br/>");
							fg = true;
						}
					} else {
						errorMsg.append("第" + (i + 2) + "行添乘部门信息为空<br/>");
						fg = true;
					}

					// 检测里程
					Double detectMileage = entity.getPlanDetailDetectMileage();

					if (!fg) {
						boolean planRepeat = false;
						String planBaseId = null;
						String planDetailId = null;
						PlanBaseDTO pbDTO = new PlanBaseDTO();
						PlanDetailDTO pdDTO = new PlanDetailDTO();
						PlanExecuteDTO peDTO = new PlanExecuteDTO();
						pbDTO.setPlanDate(planDate);
						// pbDTO.setTrainId(trainId);
						pbDTO.setSystemId(systemId);
						pbDTO.setId(IdGen.uuid());
						pbDTO.setTrainNumber(trainNumber);
						pbDTO.setRemark(remark);

						pbDTO.setSystemId(systemId);
						pbDTO.setPlanStatus(PlanStatus.PlanRegister.getStatus());
						pbDTO.setCreateDate(createDate);

						planBaseId = pbDTO.getId();
						planRepeat = false;
						
						pdDTO.setCheckRegion(checkRegion);
					    pdDTO.setPatcher(patcher);
						pdDTO.setLineId(lineId);
						pdDTO.setDirection(direction);
						pdDTO.setPlanBaseId(planBaseId);
						pdDTO.setId(IdGen.uuid());
						pdDTO.setCreateDate(createDate);
						pdDTO.setStartStation(startStation);
						pdDTO.setEndStation(endStation);
						pdDTO.setStartStationName(startStationName);
						pdDTO.setEndStationName(endStationName);
						
						pdDTO.setDetectMileage(detectMileage);
						planDetailId = pdDTO.getId();
						Date date = processDate(new Date());
						CInfo info = null;
						if (DefectSystem.defectC1.getStatus().equals(systemId)) {
							info = new C1InfoDTO();
						}
						if (DefectSystem.defectC2.getStatus().equals(systemId)) {
							info = new C2InfoDTO();
						}
						if (DefectSystem.defectC4.getStatus().equals(systemId)) {
							info = new C4InfoDTO();
						}
						info.preInsert();
						info.setPlanId(planDetailId);
						info.setCheckDate(planDate);
						info.setLineId(lineId);
						info.setDirection(direction);
						info.setStartStation(startStation);
						info.setEndStation(endStation);
						info.setStartStationName(startStationName);
						info.setEndStationName(endStationName);
						info.setSystemId(systemId);
						info.setSectionId(sectionId);
						info.setCreateDate(date);
						info.setCreateBy(createBy);
						planRepeat = false;
						peDTO.setPlanBaseId(planBaseId);
						peDTO.setPlanDetailId(planDetailId);

						peDTO.setDeptId(deptId);
						peDTO.setSystemId(systemId);
						peDTO.setPatcher(patcher);
						peDTO.setPlanBasePlanDate(planDate);
						// peDTO.setPlanBaseTrainId(trainId);
						peDTO.setPlanDetailDirection(direction);
						peDTO.setPlanDetailLineId(lineId);

						peDTO.setPlanBaseTrainNumber(trainNumber);
						peDTO.setActualCheckRegion(checkRegion);
						peDTO.setSectionId(sectionId);
						peDTO.setStartStation(startStation);
						peDTO.setEndStation(endStation);
						peDTO.setRemark(remark);
						peDTO.setPlanDetailCheckRegion(checkRegion);

						PlanExecuteDTO pe = checkPlanExecuteRP(peDTO);
						if (StringUtil.isNotEmpty(pe)) {
							planRepeat = true;
						} else {
							planBaseDao.insert(pbDTO);
							planDetailDao.insert(pdDTO);
							saveCInfo(info, systemId);

							peDTO.setId(IdGen.uuid());
							peDTO.setExecuteStatus(PlanStatus.PlanRegister.getStatus());
							peDTO.setCreateDate(createDate);
							planExecuteDao.insert(peDTO);
							planRepeat = false;
							count++;
						}

						if (planRepeat) {
							repeatPlanBase++;
							repeatStr.append("第" + (i + 2) + "行," + "此计划已经存在: 计划日期为"
									+ DateUtils.format(pbDTO.getPlanDate(), DateUtils.DATE_FORMAT_YMD) + ",添乘部门为"
									+ peDTO.getDeptId().toString() + ",线路为" + lineName.toString().trim() + ",行别为"
									+ directionName.toString().trim() + "<br/>");
						}
					}
				}
			} else {
				errorMsg.append("数据为空,请修改后上传");
			}
		} catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
		map.put("repeat", repeat);
		map.put("repeatMsg", repeatStr.toString());
		return map;
	}

	@Override
	public List<PlanExecuteDTO> findPlanExecuteByIds(String ids) {
		return planExecuteDao.findPlanExecuteByIds(ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findMonthAndAccumulativeFrom() {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM");
		String str = myFmt.format(new Date());

		List<PlanMonthAccumulativeData> list = planExecuteDao.findMonthAndAccumulativeFrom();

		list = list.stream().filter(pma -> StringUtil.isNotBlank(pma.getSystemId())
				|| StringUtil.isNotBlank(pma.getDate()) || StringUtil.isNotBlank(pma.getExcuteStatus()))
				.collect(Collectors.toList());

		int c1AllA = 0;
		int c1AllB = 0;
		int c2AllA = 0;
		int c2AllB = 0;
		int c4AllA = 0;
		int c4AllB = 0;

		int c1MonA = 0;
		int c1MonB = 0;
		int c2MonA = 0;
		int c2MonB = 0;
		int c4MonA = 0;
		int c4MonB = 0;
		for (PlanMonthAccumulativeData pma : list) {
			if ("C1".equals(pma.getSystemId())) {
				if ("1".equals(pma.getExcuteStatus())) {
					c1AllA += pma.getNum();
					if (str.equals(pma.getDate()))
						c1MonA += pma.getNum();
				}
				if ("6".equals(pma.getExcuteStatus())) {
					c1AllB += pma.getNum();
					if (str.equals(pma.getDate()))
						c1MonB += pma.getNum();
				}
			}
			if ("C2".equals(pma.getSystemId())) {
				if ("1".equals(pma.getExcuteStatus())) {
					c2AllA += pma.getNum();
					if (str.equals(pma.getDate()))
						c2MonA += pma.getNum();
				}
				if ("6".equals(pma.getExcuteStatus())) {
					c2AllB += pma.getNum();
					if (str.equals(pma.getDate()))
						c2MonB += pma.getNum();
				}
			}
			if ("C4".equals(pma.getSystemId())) {
				if ("1".equals(pma.getExcuteStatus())) {
					c4AllA += pma.getNum();
					if (str.equals(pma.getDate()))
						c4MonA += pma.getNum();
				}
				if ("6".equals(pma.getExcuteStatus())) {
					c4AllB += pma.getNum();
					if (str.equals(pma.getDate()))
						c4MonB += pma.getNum();
				}
			}
		}
		;

		Map<String, Object> map = new LinkedHashMap<>();

		Map<String, Object> Mon = new LinkedHashMap<>();
		List monList = new ArrayList<>();
		monList.add(c1MonA + "/" + c1MonB);
		monList.add(c2MonA + "/" + c2MonB);
		monList.add(c4MonA + "/" + c4MonB);

		Mon.put("name", "Mon");
		Mon.put("data", monList);

		Map<String, Object> All = new LinkedHashMap<>();
		List allList = new ArrayList<>();
		allList.add(c1AllA + "/" + c1AllB);
		allList.add(c2AllA + "/" + c2MonB);
		allList.add(c4AllA + "/" + c4MonB);

		All.put("name", "All");
		All.put("data", allList);

		List l = new ArrayList<>();
		l.add(Mon);
		l.add(All);
		map.put("data", l);
		return map;
	}

	public List<PlanExecuteDTO> setData(List<Map<String, Object>> list) {
		List<PlanExecuteDTO> lists = ListUtils.newArrayList();
		for (int i = 0; i < list.size(); i++) {

			PlanExecuteDTO planExecuteDTO = new PlanExecuteDTO();
			Map<String, Object> obj = list.get(i);
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			try {
				BeanUtils.populate(planExecuteDTO, obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lists.add(planExecuteDTO);
		}
		return lists;

	}

	@Override
	public List<PlanStatisticsByMon> planDataFromBySection(PlanQueryFilter entity) {
		List<PlanStatisticsByMon> psbmList = new ArrayList<>();
		List<PlanMonData> pBList = planExecuteDao.planDataFromBySection(entity);
		if (StringUtil.isNotEmpty(pBList)) {
			Map<String, List<PlanMonData>> maps = pBList.stream()
					.filter(planMonData -> StringUtil.isNotEmpty(planMonData.getSectionName()))
					.collect(Collectors.groupingBy(PlanMonData::getSectionName));
			maps.forEach((key, value) -> {
				PlanStatisticsByMon planStatisticsByMon = new PlanStatisticsByMon();
				Map<String, List<PlanMonData>> temp1 = value.stream()
						.collect(Collectors.groupingBy(PlanMonData::getSystemId));
				Map<String, List<PlanMonData>> temp = new LinkedHashMap<String, List<PlanMonData>>();
				if (StringUtil.isEmpty(entity.getSystemId())) {
					for (int i = 1; i <= 4; i++) {
						if (i == 3)
							continue;
						if (!temp1.containsKey(("C" + i))) {
							temp.put("C" + i, new ArrayList<PlanMonData>());
						} else {
							temp.put("C" + i, temp1.get("C" + i));
						}
					}
				} else {
					if (!temp1.containsKey(entity.getSystemId())) {
						temp.put(entity.getSystemId(), new ArrayList<PlanMonData>());
					} else {
						temp.put(entity.getSystemId(), temp1.get(entity.getSystemId()));
					}
				}
				List<PlanMonData> list = new ArrayList<>();
				temp.forEach((key1, value1) -> {
					PlanMonData planMonData = new PlanMonData();
					planMonData.setSystemId(key1);
					planMonData.setSectionName(key);
					if (value != null)
						planMonData.setSectionId(value.get(0).getSectionId());
					planMonData.setNum(value1.stream().mapToInt(PlanMonData::getNum).sum());
					planMonData.setCancelNum(value1.stream().mapToInt(PlanMonData::getCancelNum).sum());
					list.add(planMonData);

				});
				planStatisticsByMon.setName(key);
				planStatisticsByMon.setId(list.get(0).getSectionId());
				planStatisticsByMon.setPmdList(list);
				psbmList.add(planStatisticsByMon);

			});
		}
		if (StringUtil.isEmpty(pBList)) {
			pBList = new ArrayList<PlanMonData>();
		}
		return psbmList;
	}

	@Override
	public List<Object> planFlowCount1C(PlanQueryFilter entity) {
		List<Map<String, String>> mlist = null;
		if ("1".equals(entity.getFlowTag())) {
			mlist = planExecuteDao.planFlowCount1C(entity);
		} else {
			mlist = planExecuteDao.planFlowCount1CSection(entity);
		}
		Map<String, String> map = new HashMap<>();
		if (ListUtils.isNotEmpty(mlist)) {
			mlist.forEach(maps -> {
				map.put(String.valueOf(maps.get("status")), String.valueOf(maps.get("num")));
			});
		}
		List<Object> list = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			FlowCountData fcd = new FlowCountData();
			if (map.containsKey(String.valueOf(i)))
				fcd.setNum(map.get(String.valueOf(i)));

			switch (i) {
			case 1:
				fcd.setName("计划发布");
				fcd.setStatus("1");
				break;
			case 2:
				fcd.setName("计划审核");
				fcd.setStatus("2");
				break;
			case 3:
				fcd.setName("添乘记录填写");
				fcd.setStatus("3");
				break;
			case 4:
				fcd.setName("计划完成确认");
				fcd.setStatus("4");
				break;
			}
			list.add(fcd);
		}
		return list;
	}
	
	public void setPlanMap(Map<String, String> Srcmap, Map<String, Object> targetMap) {
		if(Srcmap.get("STATUS").equals("1")) {
			targetMap.replace("计划编制", Srcmap.get("count"));
		}else if(Srcmap.get("STATUS").equals("2")) {
			targetMap.replace("计划审核", Srcmap.get("count"));
		}else if(Srcmap.get("STATUS").equals("3")) {
			targetMap.replace("计划执行", Srcmap.get("count"));
		}else if(Srcmap.get("STATUS").equals("4")) {
			targetMap.replace("计划执行确认", Srcmap.get("count"));
		}
	}
	
	public void initPlanMap(Map<String, Object> map) {
		map.put("计划编制", 0);
		map.put("计划审核", 0);
		map.put("计划执行", 0);
		map.put("计划执行确认", 0);
	}

	@Override
	public List<Map<String, Object>> planFlowCountNew(PlanQueryFilter entity) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, String>> list = planExecuteDao.planFlowCountNew(entity);
		Map<String, Object> map1cc = new HashMap<String, Object>();
		Map<String, Object> map1cd = new HashMap<String, Object>();
		Map<String, Object> map2c = new HashMap<String, Object>();
		Map<String, Object> map4c = new HashMap<String, Object>();
		initPlanMap(map1cc);
		initPlanMap(map1cd);
		initPlanMap(map2c);
		initPlanMap(map4c);
		for (Map<String, String> map : list) {
			//1c处统计
			if(map.get("system_id").equals("C1") && map.get("flow_tag").equals("1")) {
				setPlanMap(map,map1cc);
			}
			//1c段统计
			else if(map.get("system_id").equals("C1") && map.get("flow_tag").equals("0")) {
				setPlanMap(map,map1cd);
			}
			//2c统计
			else if(map.get("system_id").equals("C2") && map.get("flow_tag").equals("0")) {
				setPlanMap(map,map2c);
			}
			//4c统计
			else if(map.get("system_id").equals("C4") && map.get("flow_tag").equals("0")) {
				setPlanMap(map,map4c);
			}
		}
		result.add(map1cc);
		result.add(map1cd);
		result.add(map2c);
		result.add(map4c);
		
		return result;
	}

	@Override
	public void auditRegister(PlanQueryFilter entity, String userId, String userName, String sectionId) {
		List<Map<String,String>> list = planExecuteDao.findPlanFormList(entity);
		if(!CollectionUtils.isEmpty(list)) {
			List<String> idList = new ArrayList<>();
			archivePlan(userId, userName, sectionId, list, idList);
//			if(!CollectionUtils.isEmpty(idList)) {
//				for(String id : idList) {
//					try {
//						restart(userId, userName, sectionId, id);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				LOGGER.debug("总记录数--->list:{}", list.size());
//				LOGGER.debug("总记录数--->idList:{}", idList.size());
//			}
		}
	}

	private void archivePlan(String userId, String userName, String sectionId, List<Map<String, String>> list,
			List<String> idList) {
		list.forEach(map ->{
			String taskId = map.get("id_");
			TaskRejectRequest taskRejectRequest = new TaskRejectRequest();
			taskRejectRequest.setId(map.get("id"));
			taskRejectRequest.setLeaderPass("false");
			taskRejectRequest.setActivityNodeId("usertask1");
			taskRejectRequest.setComment("驳回至上缺陷登记");
			taskRejectRequest.setUserId(userId);
			taskRejectRequest.setUserName(userName);
			taskRejectRequest.setSectionId(sectionId);
			try {
				archivePlan(taskRejectRequest, taskId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			idList.add(map.get("id"));
		});
	}
	
	private void restart(String userId, String userName, String sectionId, String id) {
		RePlanTaskRequest entity = new RePlanTaskRequest();
		entity.setId(id);
		entity.setUserId(userId);
		entity.setUserName(userName);
		entity.setSectionId(sectionId);
		entity.setLeaderPass("true");
		reStartPlan(entity);
	}
}
