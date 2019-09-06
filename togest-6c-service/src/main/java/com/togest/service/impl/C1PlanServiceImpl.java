package com.togest.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.client.resquest.FlowStartRequest;
import com.togest.client.resquest.TaskHandleRequest;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectConstants;
import com.togest.config.InfoStatus;
import com.togest.config.PlanAuditStatus;
import com.togest.config.PlanStatus;
import com.togest.dao.C1InfoDao;
import com.togest.dao.CInfoDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.CInfo;
import com.togest.domain.DefectFlow;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.IdGen;
import com.togest.domain.Page;
import com.togest.domain.PlanBaseDTO;
import com.togest.domain.PlanDetailDTO;
import com.togest.domain.PlanExecuteDTO;
import com.togest.domain.PlanExecuteRecordDTO;
import com.togest.domain.SimplePage;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.C1TaskRequest;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.PlanQueryFilter;
import com.togest.request.TaskRequest;
import com.togest.response.PlanTaskFlowResponse;
import com.togest.service.C1PlanService;

@Service
public class C1PlanServiceImpl extends PlanCommonServiceImpl implements C1PlanService {

	@Autowired
	protected CInfoDao cInfoDao;
	@Autowired
	protected C1InfoDao c1InfoDao;
	protected static final Integer FLOW_TAG_D = 1;

	@Override
	public Map<String, Object> analyzePlanExcelData(String originalFilename, InputStream inputStream, String createBy,
			String systemId, String sectionId) {
		Date nowDate = processDate(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatPlanBase = 0;
		int repeatPlanDetail = 0;
		StringBuilder repeat = new StringBuilder();
		StringBuilder errorStr = new StringBuilder();
		StringBuilder repeatMsg = new StringBuilder();
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(new String[] { "direction" }).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
		List<String> properties = Arrays.asList(new String[] { "planDate", "trainNumber", "trainName", "lineName",
				"directionName", "checkRegion", "deptName", "startStation", "endStation", "contacts","patcher", "remark" });
		List<Map<String, Object>> list = null;
		try {
			list = importService.importBaseData(properties, originalFilename, inputStream, 1);

			if (StringUtil.isNotEmpty(list)) {

				for (int i = 0; i < list.size(); i++) {
					boolean flag = false;
					Map<String, Object> obj = list.get(i);

					if (StringUtil.isEmpty(obj)) {
						continue;
					}
					PlanBaseDTO planBase = new PlanBaseDTO();
					PlanDetailDTO planDetail = new PlanDetailDTO();
					PlanExecuteDTO planExecute = new PlanExecuteDTO();
					List<String> deptIds = new ArrayList<String>();

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
						String trainId = checkTrainService.getByTrainNo(trainName.toString().trim());
						if (StringUtil.isNotBlank(trainId)) {
							planBase.setTrainId(trainId);
						} else {
							errorStr.append("第" + (i + 2) + "行车辆编号：" + trainName.toString() + "不存在<br/>");
							flag = true;
						}
					} 
					// 联系人
					Object contacts = obj.get(properties.get(9));
					if (StringUtil.isNotEmpty(contacts)) {
						planBase.setContacts(contacts.toString().trim());
					}
					// 添乘人
					Object patcher = obj.get(properties.get(10));
					if (StringUtil.isNotEmpty(patcher)) {
						planDetail.setPatcher(patcher.toString().trim());
						planExecute.setPatcher(patcher.toString().trim());
					}
					// 备注
					Object remark = obj.get(properties.get(11));
					if (StringUtil.isNotEmpty(remark)) {
						planBase.setRemark(remark.toString().trim());
						planExecute.setRemark(remark.toString().trim());
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
						String tempDept = deptName.toString().replaceAll("、", ",").replaceAll("，", ",").trim();
						List<Map<String, String>> data = deptFeignService.getIdByName(tempDept, null).getData();
						if (StringUtil.isEmpty(data)) {
							errorStr.append("第" + (i + 2) + "行填写添乘部门不存在<br/>");
							flag = true;
						} else {
							if (data.size() == tempDept.split(",").length) {
								for (Map<String, String> map1 : data) {
									deptIds.add(map1.get(ID));
								}
							} else {
								errorStr.append("第" + (i + 2) + "行添乘部门" + deptName + "未找到<br/>");
								flag = true;
							}
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
							planBase.setSystemId(systemId);
							planBase.setPlanStatus(PlanStatus.PlanRegister.getStatus());
							planBase.setCreateDate(nowDate);
							
							planBaseId = planBase.getId();

						
						String planDetailId = null;
							
							planDetail.setCreateDate(nowDate);
							
							planDetailId = planDetail.getId();
							
							// 检测数据
							C1InfoDTO info = new C1InfoDTO();
							
								info.setRawdataStatus(InfoStatus.UploadCompleted.getStatus());
								info.setCheckDate(planBase.getPlanDate());
								info.setLineId(lineId);
								info.setDirection(direction);
								info.setStartStation(planExecute.getStartStation());
								info.setEndStation(planExecute.getEndStation());
								info.setSystemId(systemId);
								info.setSectionId(sectionId);
								info.setCreateDate(nowDate);
								info.setCreateBy(createBy);
								info.setAnalyStatus(InfoStatus.UploadCompleted.getStatus());
								info.setDefectDataStatus(InfoStatus.UploadCompleted.getStatus());
								info.setRawdataStatus(InfoStatus.UploadCompleted.getStatus());
							
							planExecute.setSectionId(sectionId);
							
							planExecute.setPlanBasePlanDate(planBase.getPlanDate());
							planExecute.setPlanBaseTrainId(planBase.getTrainId());
							planExecute.setPlanDetailLineId(lineId);
							planExecute.setPlanDetailDirection(direction);
							planExecute.setSystemId(systemId);
						
							if (StringUtil.isNotEmpty(deptIds)) {
								for (String deptId : deptIds) {
									planExecute.setDeptId(deptId);
									planExecute.setResponsibleDeptId(deptId);
									PlanExecuteDTO pe = checkPlanExecuteRP(planExecute);
									if (StringUtil.isNotEmpty(pe)) {
										planRepeat = true;
									} else {
										planBase.setId(IdGen.uuid());
										planBaseDao.insert(planBase);
										planDetail.setId(IdGen.uuid());
										planDetail.setPlanBaseId(planBase.getId());
										planDetailDao.insert(planDetail);
										info.setId(IdGen.uuid());
										info.setPlanId(planDetail.getId());
										cInfoDao.insert((CInfo)info);
										c1InfoDao.insert(info);
										planExecute.setPlanBaseId(planBase.getId());
										planExecute.setPlanDetailId(planDetail.getId());
										planExecute.setId(IdGen.uuid());
										planExecute.setResponsibleDeptId(deptId);
										planExecute.setDeptId(deptId);
										planExecute.setExecuteStatus(PlanStatus.PlanRegister.getStatus());
										planExecute.setCreateDate(nowDate);
										planExecuteDao.insert(planExecute);
										planRepeat = false;
									}
								}
							} else {
								errorStr.append("第" + (i + 2) + "行添乘部门信息未找到<br/>");
								flag = true;
							}

						if (planRepeat) {
							repeatPlanBase++;
							repeatMsg.append("此计划已经存在: 计划日期为"
									+ DateUtils.format(planBase.getPlanDate(), DateUtils.DATE_FORMAT_YMD) +
									", 车号为" + obj.get(properties.get(2)).toString()
									+",添乘部门为"+planExecute.getDeptId().toString()+",线路为"+lineName.toString().trim()
									+",行别为"+directionName.toString().trim()
									+ "<br/>");
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
			e.printStackTrace();
			errorStr.append(e.getMessage());
		}
		map.put(DefectConstants.IMPORT_COUNT, count);
		map.put(DefectConstants.IMPORT_REPEAT, repeat.toString());
		map.put(DefectConstants.IMPORT_REPEAT_MSG, repeatMsg);
		map.put(DefectConstants.IMPORT_ERROR_MSG, errorStr.toString());
		return map;
	}

	public PlanExecuteDTO checkPlanExecuteRP(PlanExecuteDTO entity) {
		PlanExecuteDTO pe = new PlanExecuteDTO();
		pe.setDeptId(entity.getDeptId());
		pe.setResponsibleDeptId(entity.getResponsibleDeptId());
		pe.setSystemId(entity.getSystemId());
		pe.setPatcher(entity.getPatcher());
		pe.setPlanBasePlanDate(entity.getPlanBasePlanDate());
		pe.setPlanBaseTrainId(entity.getPlanBaseTrainId());
		pe.setPlanDetailLineId(entity.getPlanDetailLineId());
		pe.setPlanDetailDirection(entity.getPlanDetailDirection());
		return planExecuteDao.checkPlanExecuteRP(pe);
	}
	
	@Override
	@DictAggregation
	public List<PlanBaseDTO> findPlanBaseLists(PlanQueryFilter entity) {
		return planBaseDao.findC1Lists(entity);
	}

	@Override
	@DictAggregation
	public Page<PlanBaseDTO> findPlanBasePages(Page page, PlanQueryFilter entity) {
		Page<PlanBaseDTO> pg = new Page<PlanBaseDTO>();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		SimplePage sp = new SimplePage();
		sp.setPageNo(pageNo);
		sp.setPageSize(pageSize);
		sp.setStartNum((pageNo - 1) * pageSize);
		sp.setEndNum(pageNo * pageSize);
		entity.setPage(sp);
		int total = planBaseDao.findC1ListsCounts(entity);
		List<PlanBaseDTO> list = planBaseDao.findC1Lists(entity);
		pg.setList(list);
		pg.setTotal((long) total);
		pg.setPageNo(sp.getPageNo());
		pg.setPageSize(sp.getPageSize());
		return pg;
	}

	@Override
	@Transactional
	public int startPlan(FlowStartUserDataRequest entity) {
		if (StringUtil.isBlank(entity.getId())) {
			return 0;
		}
		List<FlowStartRequest> flows = new ArrayList<FlowStartRequest>();
		List<String> list = Arrays.asList(entity.getId().split(","));
		if (StringUtil.isNotEmpty(list)) {
			for (String planBaseId : list) {
				List<PlanExecuteDTO> planExecutes = planExecuteDao.findPlanExecuteByPlanBaseId(planBaseId);
				if (StringUtil.isNotEmpty(planExecutes)) {
					for (PlanExecuteDTO planExecute : planExecutes) {
						FlowStartRequest request = new FlowStartRequest();
						request.setUserId(entity.getUserId());
						request.setProcessDefKey(entity.getFlowKey());
						request.setBusinessKey(planExecute.getId());
						// request.setTaskUsers(entity.getDeptId());
						request.setTaskUsers(planExecute.getResponsibleDeptId());
						request.setSectionId(entity.getSectionId());
						// 流程主题
						request.setProcessName(
								DateUtils.format(planExecute.getPlanBasePlanDate(), DateUtils.DATE_FORMAT_YMD) + "_"
										+ planExecute.getPlanDetailLineName());
						flows.add(request);
					}
					planExecuteDao.updateExecuteStatus(
							CollectionUtils.distinctExtractToList(planExecutes, "id", String.class),
							PlanStatus.PlanAudit.getStatus());
					planExecuteDao.updateFlowTag(
							CollectionUtils.distinctExtractToList(planExecutes, "id", String.class),
							String.valueOf(FLOW_TAG_D));
				}
			}
		}
		planBaseDao.updatePlanStatus(list, PlanStatus.PlanAudit.getStatus());
		RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.startProcessInstance(flows);
		if (RestfulResponse.DEFAULT_OK != reponse.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL, reponse.getMessage());
		}
		List<Map<String, String>> flowLists = reponse.getData();
		if (StringUtil.isNotEmpty(flowLists)) {
			defectFlowService.insert(flowLists);
		}
		return list.size();
	}

	@Transactional
	public int auditPlan(TaskRequest entity) {
		List<String> list = Arrays.asList(entity.getId().split(","));
		List<PlanExecuteDTO> peList = planExecuteDao.getListByKeys(list);
		List<PlanExecuteDTO> tempPeList = new ArrayList<>();
		List<PlanExecuteDTO> cPeList = new ArrayList<>();
		for (PlanExecuteDTO pe : peList) {
			if (FLOW_TAG_D.equals(pe.getFlowTag())) {
				cPeList.add(pe);
			} else {
				tempPeList.add(pe);
			}
		}

		if (StringUtil.isNotEmpty(cPeList)) {
			list = null;
			list = CollectionUtils.distinctExtractToList(cPeList, "id", String.class);
			boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
			if (isPass) {
				planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanExecution.getStatus());
				for (PlanExecuteDTO pe : cPeList) {
					String planExecuteId = pe.getId();
					if (StringUtil.isNotBlank(entity.getTaskUsers())) {
						for (String deptId : entity.getTaskUsers().split(",")) {
							PlanExecuteRecordDTO planExecuteRecordDTO = new PlanExecuteRecordDTO();
							planExecuteRecordDTO.setDeptId(deptId);
							planExecuteRecordDTO.setRecordStatus(RECORDSTATUS_NO);
							planExecuteRecordDTO.setId(IdGen.uuid());
							planExecuteRecordDTO.setPlanExecuteId(planExecuteId);
							planExecuteRecordDTO.setRecordStatus(PlanStatus.PlanExecution.getStatus());
							planExecuteRecordDTO.setStartStation(pe.getStartStation());
							planExecuteRecordDTO.setEndStation(pe.getEndStation());
							planExecuteRecordDTO.setRemark(pe.getRemark());
							planExecuteRecordDTO.setPatcher(pe.getPatcher());
							planExecuteRecordDTO.setImplementation(pe.getImplementation());
							planExecuteRecordDao.insert(planExecuteRecordDTO);
						}
					}
					PlanExecuteDTO planExecute = new PlanExecuteDTO();
					planExecute.setId(planExecuteId);
					planExecute.setSectionId(entity.getSectionId());
					planExecute.setAuditPerson(entity.getUserId()+"_"+entity.getUserName());
					planExecute.setAuditDate(new Date());
					planExecuteDao.update(planExecute);
				}
				planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanExecution.getStatus());
				planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanAudit.getStatus());
			} else {
				planExecuteDao.updateAuditData(String.join(",",list),"", null);
				planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanAuditNotPass.getStatus());
				planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanRegister.getStatus());
			}
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
				request.setTaskUsersList(Arrays.asList(entity.getTaskUsers().split(",")));
				flows.add(request);
			}
			RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.completeTask(flows);
			if (RestfulResponse.DEFAULT_OK != reponse.getErrorCode()) {
				Shift.fatal(StatusCode.FAIL, reponse.getMessage());
			}
			List<Map<String, String>> flowLists = reponse.getData();
			updateFlow(flowLists);
		}

		if (StringUtil.isNotEmpty(tempPeList)) {
			list = null;
			list = CollectionUtils.distinctExtractToList(tempPeList, "id", String.class);
			boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
			for (PlanExecuteDTO planExecute : tempPeList) {
				PlanExecuteRecordDTO planExecuteRecordDTO = new PlanExecuteRecordDTO();
				planExecuteRecordDTO.setDeptId(planExecute.getDeptId());
				planExecuteRecordDTO.setRecordStatus(RECORDSTATUS_NO);
				planExecuteRecordDTO.setId(IdGen.uuid());
				planExecuteRecordDTO.setPlanExecuteId(planExecute.getId());
				planExecuteRecordDTO.setRecordStatus(PlanStatus.PlanExecution.getStatus());
				planExecuteRecordDTO.setStartStation(planExecute.getStartStation());
				planExecuteRecordDTO.setEndStation(planExecute.getEndStation());
				planExecuteRecordDTO.setRemark(planExecute.getRemark());
				planExecuteRecordDTO.setPatcher(planExecute.getPatcher());
				planExecuteRecordDTO.setImplementation(planExecute.getImplementation());
				planExecuteRecordDao.insert(planExecuteRecordDTO);

				PlanExecuteDTO pe = new PlanExecuteDTO();
				pe.setDeptId(entity.getTaskUsers());
				pe.setId(planExecute.getId());
				pe.setSectionId(entity.getSectionId());
				pe.setAuditPerson(entity.getUserId()+"_"+entity.getUserName());
				pe.setAuditDate(new Date());
				planExecuteDao.update(pe);
			}
			if (isPass) {
				planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanExecution.getStatus());
				planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanAudit.getStatus());
			} else {
				planExecuteDao.updateAuditData(String.join(",",list),"", null);
				planExecuteDao.updateExecuteStatus(list, PlanStatus.PlanRegister.getStatus());
				planExecuteDao.updateAuditStatus(list, PlanAuditStatus.PlanAuditNotPass.getStatus());
			}
			int rs = completeTask(entity, isPass);
			if (rs != list.size()) {
				Shift.fatal(StatusCode.FAIL);
			}
		}

		return tempPeList.size() + cPeList.size();
	}

	@Transactional	
	public void fillRecordPlan(C1TaskRequest entity, String deptId) {
		PlanExecuteRecordDTO perDto = new PlanExecuteRecordDTO();
		perDto.setPlanExecuteId(entity.getId());
		perDto.setDeptId(deptId);
		PlanExecuteRecordDTO perDTO = planExecuteRecordDao.getByEntity(perDto);
		if (StringUtil.isNotEmpty(perDTO)) {
			entity.setId(perDTO.getId());
		}
		Date date = new Date();
		entity.setLeaderPass(LEADERPASS);
		PlanExecuteRecordDTO planExecuteRecordDTO = planExecuteRecordDao.getDetailByKey(entity.getId());
		String peId = planExecuteRecordDTO.getPlanExecuteId();
		if (FLOW_TAG_D.equals(planExecuteRecordDTO.getPlanExecute().getFlowTag())) {

			if (StringUtil.isNotEmpty(entity.getImplementation())) {
				if (StringUtil.isNotEmpty(entity.getAddDate())) {
					date = entity.getAddDate();
				}
				planExecuteRecordDTO.setMultiplicationDate(date);
				planExecuteRecordDTO.setStartStation(entity.getStartStation());
				planExecuteRecordDTO.setPatcher(entity.getPatcher());
				planExecuteRecordDTO.setEndStation(entity.getEndStation());
				planExecuteRecordDTO.setImplementation(entity.getImplementation());
				planExecuteRecordDTO.setRecordStatus(PlanStatus.PlanComplete.getStatus());
				planExecuteRecordDao.update(planExecuteRecordDTO);

				List<TaskHandleRequest> flows = new ArrayList<TaskHandleRequest>();
				TaskHandleRequest request = new TaskHandleRequest();
				request.setUserId(entity.getUserId());
				request.setLeaderPass(entity.getLeaderPass());
				request.setTaskId(planExecuteRecordDTO.getTaskId());
				request.setProcessInstanceId(planExecuteRecordDTO.getProcessInstanceId());
				request.setComment(entity.getImplementation());
				if(StringUtil.isNotBlank(entity.getTaskUsers())){
					request.setTaskUsers(entity.getTaskUsers());
				}else{
					request.setTaskUsers(planExecuteRecordDTO.getDeptId());
				}
				
				request.setSectionId(entity.getSectionId());
				flows.add(request);
				RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.completeTask(flows);
				if (RestfulResponse.DEFAULT_OK != reponse.getErrorCode()) {
					Shift.fatal(StatusCode.FAIL, reponse.getMessage());
				}
				List<Map<String, String>> flowLists = reponse.getData();
				updateFlow(flowLists);

				List<String> perIdList = planExecuteRecordDao.getByPlanExecuteIdAndRecordStatus(peId,
						PlanStatus.PlanComplete.getStatus());
				if (StringUtil.isEmpty(perIdList)) {
					planExecuteDao.updateAddData(peId,entity.getUserId()+"_"+entity.getUserName(),new Date());
					planExecuteDao.updateExecuteStatus(Arrays.asList(peId), PlanStatus.PlanComplete.getStatus());
					planExecuteDao.updateAuditStatus(Arrays.asList(peId), null);
				}
			} else {
				Shift.fatal(StatusCode.PLAN_EMPTY);
			}
		} else {
			planExecuteRecordDTO.setPatcher(entity.getUserId()+"_"+entity.getUserName());
			planExecuteRecordDTO.setImplementation(entity.getImplementation());
			planExecuteRecordDTO.setRecordStatus(PlanStatus.PlanComplete.getStatus());
			planExecuteRecordDao.update(planExecuteRecordDTO);

			List<String> perIdList = planExecuteRecordDao.getByPlanExecuteIdAndRecordStatus(peId,
					PlanStatus.PlanComplete.getStatus());
			if (StringUtil.isEmpty(perIdList)) {
				planExecuteDao.updateExecuteStatus(Arrays.asList(peId), PlanStatus.PlanComplete.getStatus());
				planExecuteDao.updateAuditStatus(Arrays.asList(peId), null);
				if (StringUtil.isNotEmpty(entity.getImplementation())) {
					PlanExecuteDTO planExecute = planExecuteDao.getByKey(peId);
					// 执行情况
					planExecute.setImplementation(entity.getImplementation());
					planExecute.setActualAddDate(entity.getActualAddDate());
					planExecute.setActualAddTrafficNumber(entity.getActualAddTrainNumber());
					planExecute.setActualAddTrainNumber(entity.getActualAddTrainNumber());
					planExecute.setActualCheckRegion(entity.getActualCheckRegion());
					planExecute.setActualPatcher(entity.getActualPatcher());
					planExecute.setEquNo(entity.getEquNo());
					planExecute.setEquOperation(entity.getEquOperation());
					// 添乘人
					//planExecute.setPatcher(entity.getUserId()+"_"+entity.getUserName());
					planExecute.setAddDate(new Date());
					planExecuteDao.update(planExecute);
				}
				entity.setId(peId);
				entity.setLeaderPass(LEADERPASS);
				completeTask(entity, true);
			}
		}
	}

	@Transactional
	public int completionConfirmationPlan(TaskRequest entity, String deptId) {
		List<String> idList = Arrays.asList(entity.getId().split(","));
		List<String> list = new ArrayList<String>();
		for (String id : idList) {
			PlanExecuteRecordDTO perDto = new PlanExecuteRecordDTO();
			perDto.setPlanExecuteId(id);
			perDto.setDeptId(deptId);
			PlanExecuteRecordDTO perDTO = planExecuteRecordDao.getByEntity(perDto);
			if (StringUtil.isNotEmpty(perDTO)) {
				list.add(perDTO.getId());
			} else {
				list.add(id);
			}
		}
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
		List<PlanExecuteRecordDTO> planExecuteRecords = planExecuteRecordDao.getDetailByKeys(list);
		List<PlanExecuteRecordDTO> tempPlanExecuteRecords = new ArrayList<PlanExecuteRecordDTO>();
		List<String> planExecuteIds = new ArrayList<String>();
		planExecuteRecordDao.updateRecordStatus(list, PlanStatus.Cannel.getStatus());
		if (StringUtil.isNotEmpty(planExecuteRecords)) {
			for (PlanExecuteRecordDTO planExecuteRecordDTO : planExecuteRecords) {
				PlanExecuteDTO planExecute = planExecuteRecordDTO.getPlanExecute();
				if (FLOW_TAG_D.equals(Integer.valueOf(planExecute.getFlowTag()))) {
					tempPlanExecuteRecords.add(planExecuteRecordDTO);
				} else {
					List<String> tempList = planExecuteRecordDao.getByPlanExecuteIdAndRecordStatus(planExecute.getId(),
							PlanStatus.Cannel.getStatus());
					if (StringUtil.isEmpty(tempList)) {
						planExecuteIds.add(planExecute.getId());
					}
				}
			}
		}
		List<TaskHandleRequest> flowsC = new ArrayList<TaskHandleRequest>();
		List<TaskHandleRequest> flowsD = new ArrayList<TaskHandleRequest>();
		if (StringUtil.isNotEmpty(tempPlanExecuteRecords)) {
			for (PlanExecuteRecordDTO planExecuteRecordDTO : tempPlanExecuteRecords) {
				TaskHandleRequest request = new TaskHandleRequest();
				request.setUserId(entity.getUserId());
				request.setLeaderPass(entity.getLeaderPass());
				request.setTaskId(planExecuteRecordDTO.getTaskId());
				request.setProcessInstanceId(planExecuteRecordDTO.getProcessInstanceId());
				request.setComment(entity.getComment());
				request.setSectionId(entity.getSectionId());
				if (!isPass) {
					request.setTaskUsers(planExecuteRecordDTO.getDeptId());
				}
				flowsC.add(request);
			}
		}
		if (StringUtil.isNotEmpty(planExecuteIds)) {
			List<PlanTaskFlowResponse> taskFlows = planExecuteDao.findPlanTaskFlow(planExecuteIds);
			for (PlanTaskFlowResponse taskFlow : taskFlows) {
				TaskHandleRequest request = new TaskHandleRequest();
				request.setUserId(entity.getUserId());
				request.setLeaderPass(entity.getLeaderPass());
				request.setTaskId(taskFlow.getTaskId());
				request.setProcessInstanceId(taskFlow.getProcessInstanceId());
				request.setComment(entity.getComment());
				request.setSectionId(entity.getSectionId());
				if (!isPass) {
					request.setTaskUsers(taskFlow.getDeptId());
				}
				flowsD.add(request);
			}
		}
		List<String> PeIdList = CollectionUtils.distinctExtractToList(planExecuteRecords, "planExecuteId",
				String.class);
		if (isPass) {
			if (PeIdList != null) {
				for (String planExecuteId : PeIdList) {
					List<String> tempList = planExecuteRecordDao.getByPlanExecuteIdAndRecordStatus(planExecuteId,
							PlanStatus.Cannel.getStatus());
					if (StringUtil.isEmpty(tempList)) {
						planExecuteDao.updateConfirmationData(planExecuteId,entity.getUserId()+"_"+entity.getUserName(),new Date());
						planExecuteDao.updateAuditStatus(Arrays.asList(planExecuteId), PlanAuditStatus.PlanComplete.getStatus());
						planExecuteDao.updateExecuteStatus(Arrays.asList(planExecuteId), PlanStatus.Cannel.getStatus());
						
						DefectFlow defectFlow = new DefectFlow();
						defectFlow.setTaskId(null);
						defectFlow.setTaskName("结束");
						defectFlow.setId(planExecuteId);
						defectFlowService.update(defectFlow);
						PlanExecuteDTO pe = planExecuteDao.getByKey(planExecuteId);
						List<String> perList = planExecuteRecordDao.getByPeAndPbIdAndRecordStatus(planExecuteId,
								PlanStatus.Cannel.getStatus());
						if (StringUtil.isEmpty(perList)) {
							planBaseDao.updatePlanStatus(Arrays.asList(pe.getPlanBaseId()),
									PlanStatus.Cannel.getStatus());
						}
					}
				}
			}
			
		} else {
			//planExecuteDao.updateConfirmationData(String.join(",",PeIdList),"",null);
			//planExecuteDao.updateAddData(String.join(",",PeIdList),"",null);
			planExecuteDao.updateExecuteStatus(PeIdList, PlanStatus.PlanExecution.getStatus());
			planExecuteDao.updateAuditStatus(PeIdList, PlanAuditStatus.PlanCompleteNotPass.getStatus());
			planExecuteRecordDao.updateRecordStatus(list, PlanStatus.PlanExecution.getStatus());
		}
		RestfulResponse<List<Map<String, String>>> reponseC = workflowFeignService.completeTask(flowsC);
		RestfulResponse<List<Map<String, String>>> reponseD = workflowFeignService.completeTask(flowsD);
		if (RestfulResponse.DEFAULT_OK != reponseC.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL, reponseC.getMessage());
		}
		if (RestfulResponse.DEFAULT_OK != reponseD.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL, reponseD.getMessage());
		}
		List<Map<String, String>> flowListsC = reponseC.getData();
		List<Map<String, String>> flowListsD = reponseD.getData();
		updateFlow(flowListsC);
		defectFlowService.update(flowListsD);
		return list.size();
	}

	private void updateFlow(List<Map<String, String>> flowLists) {

		if (StringUtil.isNotEmpty(flowLists)) {
			for (Map<String, String> map2 : flowLists) {
				PlanExecuteRecordDTO planExecuteRecordDTO1 = new PlanExecuteRecordDTO();
				planExecuteRecordDTO1.setProcessInstanceId(map2.get("processInstanceId"));
				planExecuteRecordDTO1.setFormKey(map2.get("formKey"));
				planExecuteRecordDTO1.setTaskName(map2.get("taskName"));
				planExecuteRecordDTO1.setTaskId(map2.get("taskId"));
				planExecuteRecordDTO1.setPlanExecuteId(map2.get("bussinessKey"));
				planExecuteRecordDTO1.setDeptId(map2.get("taskUser"));
				planExecuteRecordDao.updateFlow(planExecuteRecordDTO1);
			}
		}
	}




}
