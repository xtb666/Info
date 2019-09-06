package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.JcwNcFeignService;
import com.togest.clien.sevice.WorkflowFeignService;
import com.togest.client.resquest.DefectFlowrRejectRequest;
import com.togest.client.resquest.FlowStartRequest;
import com.togest.client.resquest.TaskHandleRequest;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.BussinessType;
import com.togest.config.DealyStatus;
import com.togest.config.DefectAuditStatus;
import com.togest.config.DefectStatus;
import com.togest.config.DelayStatus;
import com.togest.dao.DefectDao;
import com.togest.dao.DefectHandleInfoDao;
import com.togest.dao.ExpireTaskDao;
import com.togest.domain.Defect;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.Defect2CDTO;
import com.togest.domain.Defect3CDTO;
import com.togest.domain.Defect4CDTO;
import com.togest.domain.Defect5CDTO;
import com.togest.domain.DefectDTO;
import com.togest.domain.DefectFlow;
import com.togest.domain.DefectHandleDeadline;
import com.togest.domain.DefectHandleInfo;
import com.togest.domain.DefectHistoryDelay;
import com.togest.domain.DefectReformHandle;
import com.togest.domain.ExpireTask;
import com.togest.domain.IdGen;
import com.togest.domain.TaskFlowResponse;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.ApplyDelayRequest;
import com.togest.request.CheckReformRequest;
import com.togest.request.DefectAddField;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.ReTaskC1Request;
import com.togest.request.ReTaskC2Request;
import com.togest.request.ReTaskC3Request;
import com.togest.request.ReTaskC4Request;
import com.togest.request.ReTaskC5Request;
import com.togest.request.ReTaskRequest;
import com.togest.request.TaskRejectRequest;
import com.togest.request.TaskRequest;
import com.togest.service.Defect1CService;
import com.togest.service.Defect2CService;
import com.togest.service.Defect3CService;
import com.togest.service.Defect4CService;
import com.togest.service.Defect5CService;
import com.togest.service.DefectCheckHandleService;
import com.togest.service.DefectDelayService;
import com.togest.service.DefectFlowService;
import com.togest.service.DefectHandleDeadlineService;
import com.togest.service.DefectReformHandleService;
import com.togest.service.DefectService;
import com.togest.service.DefectSysncQuestionService;
import com.togest.service.FlowService;

@Service
public class FlowServiceImpl implements FlowService {

	@Autowired
	private DefectDao dao;
	@Autowired
	private DefectService defectService;
	@Autowired
	private Defect1CService c1Service;
	@Autowired
	private Defect2CService c2Service;
	@Autowired
	private Defect3CService c3Service;
	@Autowired
	private Defect4CService c4Service;
	@Autowired
	private Defect5CService c5Service;
	@Autowired
	private ExpireTaskDao expireTaskDao;
	@Autowired
	private DefectHandleInfoDao defectHandleInfoDao;
	@Autowired
	private WorkflowFeignService workflowFeignService;
	@Autowired
	private DefectCheckHandleService defectCheckHandleService;
	@Autowired
	private DefectReformHandleService defectReformHandleService;
	@Autowired
	private DefectDelayService defectDelayService;
	@Autowired
	private DefectFlowService defectFlowService;
	@Autowired
	private DefectHandleDeadlineService defectHandleDeadlineService;
	@Autowired
	private JcwNcFeignService jcwNcFeignService;
	@Autowired
	private DefectSysncQuestionService defectSysncQuestionService;

	private static final int TASK_USERS = 1;//
	private static final int WORK_SHOP = 2;// 车间
	private static final int WORK_AREA = 3;// 工区
	private static final int WORK_SHOP_AREA = 4;// 工区

	private static final String REVIEW_CONCLUSION = "查无异常";

	private static final String LEADERPASS = "true";
	
	public static final String YING_TAN_GONG_DIAN_DUAN_SECTION_ID = "3ceeec04f1264f1290ba8763279a5de0";
	public static final int Number = 10;

	@Value("${my.defect.urlStr}")
	private String urlStr;
	@Value("${my.defectTimeout.secondLevel}")
	private Integer secondLevel;
	@Value("${my.defect.handleInfo}")
	private Boolean handleInfo = false;
	@Value("${my.defect.isDefectAssortment}")
	private Boolean isDefectAssortment = false;

	@Transactional
	public int start(FlowStartUserDataRequest entity) {
		List<FlowStartRequest> flows = new ArrayList<FlowStartRequest>();

		String defectStatus = DefectStatus.DefectAudit.getStatus();
		List<String> list = Arrays.asList(entity.getId().split(","));
		List<TaskFlowResponse> taskFlows = dao.getTaskFlowResponseByIds(list);
		for (TaskFlowResponse taskFlow : taskFlows) {
			FlowStartRequest request = new FlowStartRequest();
			request.setUserId(entity.getUserId());
			request.setProcessDefKey(entity.getFlowKey());
			request.setBusinessKey(taskFlow.getId());
			if (StringUtil.isBlank(taskFlow.getDefectName())) {
				taskFlow.setDefectName(getDefectName(taskFlow.getId()));
			}
			request.setProcessName(taskFlow.getDefectName());
			request.setSectionId(taskFlow.getSectionId());
			flows.add(request);
		}
		RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.startProcessInstance(flows);
		if (RestfulResponse.DEFAULT_OK != reponse.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL, reponse.getMessage());
		}
		List<Map<String, String>> flowLists = reponse.getData();
		if (StringUtil.isNotEmpty(flowLists)) {
			defectFlowService.insert(flowLists);
			for (Map<String, String> flowMap : flowLists) {
				dao.updateDefectStatus(defectStatus, flowMap.get("bussinessKey"));
				if (!handleInfo) {
					DefectHandleInfo info = new DefectHandleInfo();
					info.setId(flowMap.get("bussinessKey"));
					defectHandleInfoDao.insert(info);
				}
			}
		}
		return list.size();

	}

	private String getDefectName(String id) {
		String defectName = "";
		DefectDTO defect = dao.getDefectById(id);
		if (StringUtil.isNotEmpty(defect)) {
			if (StringUtil.isNotBlank(defect.getPillarName())) {
				defectName = defect.getLineName() + "_" + defect.getPsaName() + "_"
						+ (StringUtil.isNotEmpty(defect.getCheckDate())
								? (DateUtils.format(defect.getCheckDate(), DateUtils.DATE_FORMAT_YMD) + "_(") : "(")
						+ defect.getPillarName() + "支柱)_" + defect.getSystemName();
			} else {
				defectName = defect.getLineName() + "_" + defect.getPsaName() + "_"
						+ (StringUtil.isNotEmpty(defect.getCheckDate())
								? (DateUtils.format(defect.getCheckDate(), DateUtils.DATE_FORMAT_YMD) + "_") : "")
						+ defect.getSystemName();
			}
			if (StringUtil.isNotBlank(defectName)) {
				dao.updateDefectName(id, defectName);
			}

		}
		return defectName;
	}

	@Transactional
	public int reStart(ReTaskC1Request entity) {
		Defect1CDTO defect = entity.getDefect1C();
		if (defect != null) {
			c1Service.save(defect);
		}
		int i = reStartTask(entity);
		return i;
	}

	@Transactional
	public int reStart(ReTaskC2Request entity) {
		Defect2CDTO defect = entity.getDefect2C();
		if (defect != null) {
			c2Service.save(defect);
		}
		int i = reStartTask(entity);
		return i;
	}

	@Transactional
	public int reStart(ReTaskC3Request entity) {
		Defect3CDTO defect = entity.getDefect3C();
		if (defect != null) {
			c3Service.save(defect);
		}
		int i = reStartTask(entity);
		return i;
	}

	@Transactional
	public int reStart(ReTaskC4Request entity) {
		Defect4CDTO defect = entity.getDefect4C();
		if (defect != null) {
			c4Service.save(defect);
		}
		int i = reStartTask(entity);
		return i;
	}

	@Transactional
	public int reStart(ReTaskC5Request entity) {
		Defect5CDTO defect = entity.getDefect5C();
		if (defect != null) {
			c5Service.save(defect);
		}
		int i = reStartTask(entity);
		return i;
	}

	private int reStartTask(ReTaskRequest entity) {
		List<String> list = Arrays.asList(entity.getId().split(","));
		int rs = completeTask(entity, 0);
		if (rs != list.size()) {
			Shift.fatal(StatusCode.FAIL);
		}
		defectHandleInfoDao.updateAuditStatuBatch(list, null);
		dao.updateDefectStatusBatch(list, DefectStatus.DefectAudit.getStatus());
		return list.size();
	}

	public boolean handleDate(List<String> list, String confirmPerson) {
		boolean fg = true;
		if (StringUtil.isNotEmpty(list)) {
			Date date = new Date();
			List<Defect> defects = dao.getByKeys(list);
			if (StringUtil.isNotEmpty(defects)) {
				for (Defect defect : defects) {
					if (StringUtil.isNotBlank(defect.getDefectDataLevel())
							&& StringUtil.isNotBlank(defect.getDefectDataCategory())) {
						DefectHandleDeadline dh = defectHandleDeadlineService.getByEntity(defect.getDefectDataLevel(),
								defect.getDefectDataCategory());
						if(StringUtil.isNotEmpty(dh)){
							fg = false;
							//复核期限
							Integer reviewDeadline = dh.getReviewDeadline();
							if(StringUtil.isNotEmpty(reviewDeadline)){
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								calendar.add(Calendar.DATE, reviewDeadline);
								Date reviewDate = calendar.getTime();
								// 复核定时任务
								saveExpireTask(defect.getId(), date, reviewDate, BussinessType.DefectCheck.getStatus());
							}
							
							//整改期限
							Integer reformDeadline = dh.getReformDeadline();
							if(StringUtil.isNotEmpty(reformDeadline)){
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								calendar.add(Calendar.DATE, reformDeadline);
								Date reformDate = calendar.getTime();
								// 复核定时任务
								saveExpireTask(defect.getId(), date, reformDate, BussinessType.Defect.getStatus());
								DefectHandleInfo defectHandleInfo = new DefectHandleInfo(confirmPerson, date, 1,
										DefectAuditStatus.DefectAudit.getStatus(), reformDate, 1);
								defectHandleInfo.setFg(false);
								defectHandleInfoDao.updateBatch(Arrays.asList(defect.getId()), defectHandleInfo);
							}
							
						}
					}
				}
			}
		}
		return fg;
	}

	public void saveExpireTask(String dataId, Date createDate, Date expireDate, String bussinessType) {
		ExpireTask expireTask = new ExpireTask();
		expireTask.preInsert();
		expireTask.setDataId(dataId);
		expireTask.setCreateDate(createDate);
		expireTask.setExpireDate(expireDate);
		expireTask.setLockVersion(1);
		expireTask.setBussinessType(bussinessType);
		ExpireTask et = expireTaskDao.getByDataId(dataId, bussinessType);
		if (StringUtil.isEmpty(et)) {
			expireTaskDao.insert(expireTask);
		}
	}

	@Transactional
	public int auditDefect(TaskRequest entity) {
		List<String> list = Arrays.asList(entity.getId().split(","));
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
		if (isPass) {
			Date date = new Date();
			dao.updateDefectStatusBatch(list, DefectStatus.ShopReception.getStatus());
			boolean fg = true;
			if(isDefectAssortment){
				fg = handleDate(list, entity.getUserId() + "_" + entity.getUserName());
			}
			if(fg){
				// 计划完成时间
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, secondLevel);
				Date planCompleteDate = calendar.getTime();
				DefectHandleInfo defectHandleInfo = new DefectHandleInfo(entity.getUserId() + "_" + entity.getUserName(),
						date, 1, DefectAuditStatus.DefectAudit.getStatus(), planCompleteDate,1);
				defectHandleInfo.setFg(false);
				defectHandleInfoDao.updateBatch(list, defectHandleInfo);
				// 添加任务表
				ExpireTask expireTask = new ExpireTask();
				for (String defectId : list) {
					expireTask.preInsert();
					expireTask.setDataId(defectId);
					expireTask.setCreateDate(date);
					expireTask.setExpireDate(planCompleteDate);
					expireTask.setLockVersion(1);
					expireTask.setBussinessType(BussinessType.Defect.getStatus());
					ExpireTask et = expireTaskDao.getByDataId(defectId,BussinessType.Defect.getStatus());
					if (StringUtil.isEmpty(et)) {
						expireTaskDao.insert(expireTask);
					}
				}
			}
			for (String defectId : list) {
				DefectAddField defectAddField = new DefectAddField();
				defectAddField.setConfirmDate(date);
				defectAddField.setConfirmPerson(entity.getUserName());
				defectAddField.setId(defectId);
				dao.updateDefectData(defectAddField);
			}
			dao.updateDefectStatusBatch(list, DefectStatus.ShopReception.getStatus());
			
			// defectService.sendAuditDefectData(urlStr+"/contact/DevQuestion/save_6c",
			// list,
			// entity.getUserName(), date);
		} else {
			dao.updateDefectStatusBatch(list, DefectStatus.DefectRegister.getStatus());
			defectHandleInfoDao.updateAuditStatuBatch(list, DefectAuditStatus.DefectAuditNotPass.getStatus());
		}
		int rs = 0;
		if (isPass) {
			rs = completeTask(entity, WORK_SHOP);
		} else {
			rs = completeTask(entity);
		}
		if (rs != list.size()) {
			Shift.fatal(StatusCode.FAIL);
		}
		return list.size();

	}

	@Transactional
	public int receiveDefect(TaskRequest entity) {

		List<String> list = Arrays.asList(entity.getId().split(","));
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
		if (isPass) {

			defectHandleInfoDao.updateAuditStatuBatch(list, DefectAuditStatus.ShopReception.getStatus());
			dao.updateDefectStatusBatch(list, DefectStatus.ReviewRectification.getStatus());
			//向问题库同步缺陷
			defectSysncQuestionService.sysncDefectData(list);
		} else {
			dao.updateDefectStatusBatch(list, DefectStatus.DefectAudit.getStatus());
			defectHandleInfoDao.updateAuditStatuBatch(list, DefectAuditStatus.ShopNotReception.getStatus());
			// defectHandleInfoDao.updateConfirmBatch(list, null, null, 0);
		}
		int rs = 0;
		if (isPass) {
			rs = completeTask(entity, WORK_AREA);
		} else {
			rs = completeTask(entity);
		}
		if (rs != list.size()) {
			Shift.fatal(StatusCode.FAIL);
		}
		return list.size();

	}

	private int completeTask(TaskRequest entity) {
		List<TaskFlowResponse> taskFlows = dao.getTaskFlowResponseByIds(Arrays.asList(entity.getId().split(",")));
		List<TaskHandleRequest> flows = new ArrayList<TaskHandleRequest>();
		for (TaskFlowResponse taskFlow : taskFlows) {
			TaskHandleRequest request = new TaskHandleRequest();
			request.setUserId(entity.getUserId());
			request.setLeaderPass(entity.getLeaderPass());
			request.setTaskId(taskFlow.getTaskId());
			request.setProcessInstanceId(taskFlow.getProcessInstanceId());
			request.setComment(entity.getComment());
			request.setSectionId(taskFlow.getSectionId());
			flows.add(request);
		}
		RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.completeTask(flows);
		if (RestfulResponse.DEFAULT_OK != reponse.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL);
		}
		List<Map<String, String>> flowLists = reponse.getData();
		if (StringUtil.isNotEmpty(flowLists)) {
			defectFlowService.update(flowLists);
			return flowLists.size();
		} else {
			return flows.size();
		}

	}

	private int completeTask(TaskRequest entity, int completeType) {

		List<TaskFlowResponse> taskFlows = dao.getTaskFlowResponseByIds(Arrays.asList(entity.getId().split(",")));
		List<TaskHandleRequest> flows = new ArrayList<TaskHandleRequest>();
		for (TaskFlowResponse taskFlow : taskFlows) {
			TaskHandleRequest request = new TaskHandleRequest();
			request.setUserId(entity.getUserId());
			request.setLeaderPass(entity.getLeaderPass());
			request.setTaskId(taskFlow.getTaskId());
			request.setSectionId(taskFlow.getSectionId());
			if (completeType == WORK_SHOP) {// 区间
				request.setTaskUsers(taskFlow.getWorkShopId());
			} else if (completeType == WORK_AREA) {// 工区
				request.setTaskUsers(taskFlow.getWorkAreaId());
			} else if (completeType == WORK_SHOP_AREA) {// 工区
				request.setTaskUsers(taskFlow.getWorkAreaId() + "," + taskFlow.getWorkShopId());
			} else {
				request.setTaskUsers(entity.getTaskUsers());
			}
			request.setProcessInstanceId(taskFlow.getProcessInstanceId());
			request.setComment(entity.getComment());
			flows.add(request);
		}
		RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.completeTask(flows);
		if (RestfulResponse.DEFAULT_OK != reponse.getErrorCode()) {
			Shift.fatal(StatusCode.FAIL, reponse.getMessage());
		}
		List<Map<String, String>> flowLists = reponse.getData();
		defectFlowService.update(flowLists);
		return flowLists.size();

	}

	@Transactional
	public int reviewRectificationDefect(CheckReformRequest entity) {

		if (entity != null) {

			Date date = new Date();
			
			if (StringUtil.isNotEmpty(entity.getDefectCheckHandle())
					&& StringUtil.isNotBlank(entity.getDefectCheckHandle().getId())) {

				defectHandleInfoDao.updateAuditStatuBatch(Arrays.asList(entity.getDefectCheckHandle().getId()), null);
				if (StringUtil.isEmpty(defectCheckHandleService.getCheckByKey(entity.getDefectCheckHandle().getId()))) {
					defectCheckHandleService.insertCheck(entity.getDefectCheckHandle());
				} else {
					defectCheckHandleService.updateCheck(entity.getDefectCheckHandle());
				}
				if (!entity.getDefectCheckHandle().getIsReform()
						&& REVIEW_CONCLUSION.equals(entity.getDefectCheckHandle().getReviewConclusion())) {
					dao.updateDefectStatus(DefectStatus.RectificationVerification.getStatus(),
							entity.getDefectCheckHandle().getId());
					////
					defectService.updateDefectData(entity.getDefectCheckHandle());
					TaskRequest r = new TaskRequest();
					r.setUserId(entity.getUserId());
					r.setId(entity.getDefectCheckHandle().getId());
					r.setLeaderPass(LEADERPASS);
					// 更新整改完成时间
					DefectHandleInfo info = new DefectHandleInfo();
					info.setCompleteDate(date);
					info.setIsNeedreform(0);
					info.setIsReformed(1);
					info.setId(entity.getDefectCheckHandle().getId());
					defectHandleInfoDao.update(info);
					defectReformHandleService.deleteReformByIds(Arrays.asList(entity.getDefectCheckHandle().getId()));
					defectSysncQuestionService.sysncDefectReformHandleData(entity.getDefectCheckHandle(),null);
					if (StringUtil.isNotBlank(entity.getTaskUsers())) {
						this.completeTask(r, TASK_USERS);
					} else {
						this.completeTask(r, WORK_SHOP);
					}

				} else {
					dao.updateDefectStatus(DefectStatus.CHECKHANDLE.getStatus(), entity.getDefectCheckHandle().getId());
				}

			}

			if (StringUtil.isNotEmpty(entity.getDefectCheckHandle())
					&& StringUtil.isNotBlank(entity.getDefectCheckHandle().getId())
					&& REVIEW_CONCLUSION.equals(entity.getDefectCheckHandle().getReviewConclusion())) {
				defectReformHandleService
						.deleteReformByIds(Arrays.asList(entity.getDefectCheckHandle().getId().split(",")));
				return 1;
			}

			if (StringUtil.isNotEmpty(entity.getDefectReformHandle())
					&& StringUtil.isNotBlank(entity.getDefectReformHandle().getId())) {
				DefectReformHandle defectReformHandle = entity.getDefectReformHandle();
				// defectReformHandle.setHandleDate(date);
				// defectReformHandle.setHandlePerson(entity.getUserId() + "_"
				// + entity.getUserName());
				if (StringUtil.isEmpty(defectReformHandleService.getReformByKey(defectReformHandle.getId()))) {
					defectReformHandleService.insertReform(defectReformHandle);
				} else {
					defectReformHandleService.updateReform(defectReformHandle);
				}
				defectService.updateDefectData(entity.getDefectReformHandle());
				dao.updateDefectStatus(DefectStatus.RectificationVerification.getStatus(),
						entity.getDefectReformHandle().getId());
				TaskRequest r = new TaskRequest();
				r.setUserId(entity.getUserId());
				r.setId(defectReformHandle.getId());
				r.setLeaderPass(LEADERPASS);
				// 更新整改完成时间
				DefectHandleInfo info = new DefectHandleInfo();
				info.setCompleteDate(date);
				info.setIsNeedreform(1);
				info.setIsReformed(1);
				info.setId(defectReformHandle.getId());
				defectHandleInfoDao.update(info);
				defectHandleInfoDao.updateAuditStatuBatch(Arrays.asList(defectReformHandle.getId()), null);
				//同步符合整改信息到问题库
				if(null != entity.getDefectCheckHandle() || null != entity.getDefectReformHandle())
				{
					defectSysncQuestionService.sysncDefectReformHandleData(entity.getDefectCheckHandle(),entity.getDefectReformHandle());
				}
				
				
				if (StringUtil.isNotBlank(entity.getTaskUsers())) {
					this.completeTask(r, TASK_USERS);
				} else {
					this.completeTask(r, WORK_SHOP);
				}

			}
		}
		return 1;

	}

	@Transactional
	public int applyDelay(ApplyDelayRequest delay, String sectionId) {
		String ids = delay.getId();
		List<String> list = Arrays.asList(ids.split(","));
		Date date = new Date();

		for (String id : list) {
			delay.setDelayDate(date);
			delay.setDelayPerson(delay.getUserId() + "_" + delay.getUserName());
			delay.setDelayStatus(DealyStatus.DealyRegister.getStatus());
			defectDelayService.delete(id);
			delay.setId(id);
			defectDelayService.insert(delay);// 判断先删除
			DefectHistoryDelay hisDelay = new DefectHistoryDelay();
			hisDelay.setDefectId(id);
			hisDelay.setDelayDate(delay.getDelayDate());
			hisDelay.setDelayPerson(delay.getDelayPerson());
			hisDelay.setDelayToDate(delay.getDelayToDate());
			hisDelay.setDelayStatus(delay.getDelayStatus());
			hisDelay.setId(IdGen.uuid());
			defectDelayService.insertHistory(hisDelay);
			dao.updateDefectStatus(DefectStatus.DELAY.getStatus(), id);
			if (StringUtil.isNotEmpty(delay.getDelayToDate())) {

				ExpireTask checkExpireTask = expireTaskDao.getByDataId(id, BussinessType.DefectCheck.getStatus());
				if (StringUtil.isNotEmpty(checkExpireTask)) {
					Date expireDate = checkExpireTask.getExpireDate();
					Long time = delay.getDelayToDate().getTime() - date.getTime();
					Date delayPlanCompleteDate = new Date(expireDate.getTime() + time);
					expireTaskDao.updateExpireDate(delayPlanCompleteDate, id, BussinessType.DefectCheck.getStatus());
					// defectHandleInfoDao.updatePlanCompleteDateBatch(Arrays.asList(id),
					// delayPlanCompleteDate);
				}
				ExpireTask expireTask = expireTaskDao.getByDataId(id, BussinessType.Defect.getStatus());
				if (StringUtil.isNotEmpty(expireTask)) {
					Date expireDate = expireTask.getExpireDate();
					Long time = delay.getDelayToDate().getTime() - date.getTime();
					Date delayPlanCompleteDate = new Date(expireDate.getTime() + time);
					expireTaskDao.updateExpireDate(delayPlanCompleteDate, id, BussinessType.Defect.getStatus());
					defectHandleInfoDao.updatePlanCompleteDateBatch(Arrays.asList(id), delayPlanCompleteDate);
				}

			}

		}
		defectHandleInfoDao.updateAuditStatuBatch(list, null);
		TaskRequest r = new TaskRequest();
		r.setUserId(delay.getUserId());
		r.setId(ids);
		r.setLeaderPass("applyDelay");
		r.setSectionId(sectionId);
		r.setComment(delay.getRemark());

		int rs = this.completeTask(r, WORK_SHOP);
		if (rs != list.size()) {
			Shift.fatal(StatusCode.FAIL);
		}
		return list.size();
	}

	@Transactional
	public int cancelDefect(TaskRequest entity) {
		List<String> list = Arrays.asList(entity.getId().split(","));
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
		Date date = new Date();
		if (isPass) {
			defectHandleInfoDao.updateCancelBatch(list, entity.getUserId() + "_" + entity.getUserName(), date, 1);
			defectHandleInfoDao.updateAuditStatuBatch(list, DefectAuditStatus.RectificationVerification.getStatus());
			dao.updateDefectStatusBatch(list, DefectStatus.Cancel.getStatus());
			expireTaskDao.deleteByDataIds(list);
			// defectService.sendCancelDefectData(urlStr+"/contact/DevQuestion/destroy_6c",
			// list,
			// entity.getUserName(), new Date(),entity.getComment());
			entity.setTaskUsers("togest_123456");

			//
			for (String defectId : list) {
				DefectAddField defectAddField = new DefectAddField();
				defectAddField.setCancelDate(date);
				defectAddField.setCancelPerson(entity.getUserName());
				defectAddField.setId(defectId);
				dao.updateDefectData(defectAddField);
			}
			
		} else {
			dao.updateDefectStatusBatch(list, DefectStatus.ReviewRectification.getStatus());
			defectHandleInfoDao.updateAuditStatuBatch(list,
					DefectAuditStatus.RectificationVerificationNotPass.getStatus());
			//复合整改验证不通过
			List<Map<String, Object>> mapList = new ArrayList<>();
			list.forEach(x-> {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("passResult", "{整改验证不通过}");
				map.put("defectStatus", "1");
				map.put("dataId", x);
				mapList.add(map);
			});
			if(!CollectionUtils.isEmpty(mapList)){
				jcwNcFeignService.sysncDefectReformHandleData(mapList);
			}
		}
		
		completeTask(entity, 1);
		return list.size();

	}

	@Transactional
	public int delayAuditDefect(TaskRequest entity) {
		List<String> list = Arrays.asList(entity.getId().split(","));
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));
		if (isPass) {
			// 更新延期状态 延期次数
			defectDelayService.updateDefectStatusBatch(list, DelayStatus.DelayPass.getStatus());
			defectHandleInfoDao.updateAuditStatuBatch(list, DefectAuditStatus.DELAY.getStatus());
			dao.updateDefectStatusBatch(list, DefectStatus.ReviewRectification.getStatus());
			defectHandleInfoDao.updateDelayCountBatch(list);
			DefectHandleInfo info = new DefectHandleInfo();
			info.setIsDelayed(1);
			defectHandleInfoDao.updateBatch(list, info);
		} else {
			defectDelayService.updateDefectStatusBatch(list, DelayStatus.DelayNoPass.getStatus());
			dao.updateDefectStatusBatch(list, DefectStatus.ReviewRectification.getStatus());
			defectHandleInfoDao.updateAuditStatuBatch(list, DefectAuditStatus.DefectAuditNotPass.getStatus());
			DefectHandleInfo info = new DefectHandleInfo();
			info.setIsDelayed(0);
			defectHandleInfoDao.updateBatch(list, info);
		}
		// 添加历史表
		defectDelayService.insertHistoryByCopy(list);
		int rs = completeTask(entity, WORK_AREA);
		if (rs != list.size()) {
			Shift.fatal(StatusCode.FAIL);
		}
		return list.size();

	}

	@Override
	@Transactional
	public int archiveDefect(TaskRejectRequest entity) {
		if (StringUtil.isEmpty(entity.getComment())) {
			entity.setComment("");
		}
		List<String> list = Arrays.asList(entity.getId().split(","));
		String activityNodeId = entity.getActivityNodeId();
		String userId = entity.getUserId();
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));

		List<TaskFlowResponse> taskFlows = dao.getTaskFlowResponseByIds(list);
		List<DefectHandleInfo> nDhiList = new ArrayList<DefectHandleInfo>();
		List<DefectHandleInfo> oDhiList;

		if (isPass) {

		} else {
			if (StringUtil.isEmpty(activityNodeId)) {
				return 0;
			}
			List<DefectFlowrRejectRequest> dFRList = new ArrayList<DefectFlowrRejectRequest>();
			for (TaskFlowResponse taskFlow : taskFlows) {
				DefectFlowrRejectRequest request = new DefectFlowrRejectRequest();
				request.setActivityNodeId(activityNodeId);
				request.setTaskId(taskFlow.getTaskId());
				request.setProcessInstanceId(taskFlow.getProcessInstanceId());
				request.setUserId(userId);
				request.setComment(entity.getComment());
				dFRList.add(request);
			}
			List<DefectFlow> dFList = defectFlowService.getBykeys(list);
			if (dFList.size() != list.size()) {
				return 0;
			}

			oDhiList = defectHandleInfoDao.getByKeys(list);
			for (DefectHandleInfo oDhi : oDhiList) {
				if (oDhi == null) {
					return 0;
				}
				DefectHandleInfo nDhi = new DefectHandleInfo();
				switch (activityNodeId) {
				case "usertask6":
					nDhi.setCompleteDate(oDhi.getCompleteDate());
					nDhi.setIsReformed(oDhi.getIsReformed());
					nDhi.setIsNeedreform(oDhi.getIsNeedreform());
				case "usertask5":
				case "usertask4":
				case "usertask3":
					nDhi.setConfirmPerson(oDhi.getConfirmPerson());
					nDhi.setConfirmDate(oDhi.getConfirmDate());
					nDhi.setPlanCompleteDate(oDhi.getPlanCompleteDate());
				case "usertask2":
					nDhi.setIsConfirmed(oDhi.getIsConfirmed());
				case "usertask1":
					nDhi.setId(oDhi.getId());
					break;
				}
				switch (activityNodeId) {
				case "usertask6":
				case "usertask5":
				case "usertask4":
				case "usertask3":
					nDhi.setConfirmStatus(1);
					break;
				case "usertask2":
				case "usertask1":
					nDhi.setConfirmStatus(0);
					break;
				}
				nDhi.setComment(entity.getComment());
				nDhiList.add(nDhi);
				defectHandleInfoDao.updateArchiveRest(nDhi);
			}
			String defectAuditStatus = DefectAuditStatus.DefectReject.getStatus();
			String defectStatus = null;
			switch (activityNodeId) {
			case "usertask6":
				// defectAuditStatus =
				// DefectAuditStatus.RectificationVerification.getStatus();
				defectStatus = DefectStatus.RectificationVerification.getStatus();
				break;
			case "usertask5":
				// defectAuditStatus =
				// DefectAuditStatus.DelayNotPass.getStatus();
				defectStatus = DefectStatus.DELAY.getStatus();
				break;
			case "usertask4":
				// defectAuditStatus =
				// DefectAuditStatus.RectificationVerificationNotPass.getStatus();
				defectStatus = DefectStatus.ReviewRectification.getStatus();
				break;
			case "usertask3":
				// defectAuditStatus =
				// DefectAuditStatus.DefectAudit.getStatus();
				defectStatus = DefectStatus.ShopReception.getStatus();
				break;
			case "usertask2":
				// defectAuditStatus =
				// DefectAuditStatus.ShopNotReception.getStatus();
				defectStatus = DefectStatus.DefectAudit.getStatus();
				break;
			case "usertask1":
				// defectAuditStatus =
				// DefectAuditStatus.DefectAuditNotPass.getStatus();
				defectStatus = DefectStatus.DefectRegister.getStatus();

				break;
			}

			defectHandleInfoDao.updateAuditStatuBatch(list, defectAuditStatus);
			dao.updateDefectStatusBatch(list, defectStatus);
			RestfulResponse<List<Map<String, String>>> response = workflowFeignService.rejectTask(dFRList);
			if (RestfulResponse.DEFAULT_OK != response.getErrorCode()) {
				Shift.fatal(StatusCode.FAIL, response.getMessage());
			}
			List<Map<String, String>> flowLists = response.getData();
			defectFlowService.update(flowLists);
		}
		return list.size();
	}

	@Override
	@Transactional
	public int saveDefectReviewRectification(CheckReformRequest entity) {
		if (entity != null) {
			Date date = new Date();
			if (StringUtil.isNotEmpty(entity.getDefectCheckHandle())
					&& StringUtil.isNotBlank(entity.getDefectCheckHandle().getId())) {

				if (StringUtil.isEmpty(defectCheckHandleService.getCheckByKey(entity.getDefectCheckHandle().getId()))) {
					defectCheckHandleService.insertCheck(entity.getDefectCheckHandle());
				} else {
					defectCheckHandleService.updateCheck(entity.getDefectCheckHandle());
				}
				// 更新整改完成时间
				DefectHandleInfo info = new DefectHandleInfo();
				info.setCompleteDate(date);
				info.setId(entity.getDefectCheckHandle().getId());
				defectHandleInfoDao.update(info);

			}
			if (StringUtil.isNotEmpty(entity.getDefectCheckHandle())
					&& StringUtil.isNotBlank(entity.getDefectCheckHandle().getId())
					&& REVIEW_CONCLUSION.equals(entity.getDefectCheckHandle().getReviewConclusion())) {
				defectReformHandleService
						.deleteReformByIds(Arrays.asList(entity.getDefectCheckHandle().getId().split(",")));

				return 1;
			}

			if (StringUtil.isNotEmpty(entity.getDefectReformHandle())
					&& StringUtil.isNotBlank(entity.getDefectReformHandle().getId())) {
				DefectReformHandle defectReformHandle = entity.getDefectReformHandle();
				if (StringUtil.isEmpty(defectReformHandleService.getReformByKey(defectReformHandle.getId()))) {
					defectReformHandleService.insertReform(defectReformHandle);
				} else {
					defectReformHandleService.updateReform(defectReformHandle);
				}
				// 更新整改完成时间
				DefectHandleInfo info = new DefectHandleInfo();
				info.setCompleteDate(date);
				info.setId(defectReformHandle.getId());
				defectHandleInfoDao.update(info);
			}
		}
		return 1;
	}
}
