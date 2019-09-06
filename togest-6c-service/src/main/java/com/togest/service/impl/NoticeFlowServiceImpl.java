package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.WorkflowFeignService;
import com.togest.client.resquest.FlowStartRequest;
import com.togest.client.resquest.TaskHandleRequest;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.BussinessType;
import com.togest.config.NoticeStatus;
import com.togest.dao.ExpireTaskDao;
import com.togest.domain.DefectFlow;
import com.togest.domain.ExpireTask;
import com.togest.domain.NoticeDTO;
import com.togest.domain.NoticeSectionDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.NoticeSignTaskRequest;
import com.togest.request.TaskRequest;
import com.togest.service.DefectFlowService;
import com.togest.service.FlowService;
import com.togest.service.NoticeDefectService;
import com.togest.service.NoticeFlowService;
import com.togest.service.NoticeSectionService;
import com.togest.service.NoticeService;

@Service
public class NoticeFlowServiceImpl implements NoticeFlowService{

	@Autowired
	private WorkflowFeignService workflowFeignService;
	@Autowired
	private DefectFlowService defectFlowService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeSectionService noticeSectionService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private NoticeDefectService noticeDefectService;
	@Autowired
	private ExpireTaskDao expireTaskDao;
	
	private static final String LEADERPASS = "true";
	
	@Value("${my.defectTimeout.oneLevel}")
	private Integer oneLevel;
	//下发
	@Transactional
	public int startNotice(FlowStartUserDataRequest entity) {
		
		List<FlowStartRequest> flows = new ArrayList<FlowStartRequest>();
		////////////
		String sign = NoticeStatus.Sign.getStatus();
		List<String> list = Arrays.asList(entity.getId().split(","));
		////////////
		List<NoticeDTO> notices = noticeService.getByIds(list);
		
		for (NoticeDTO notice : notices) {
			List<NoticeSectionDTO> noticeSections = noticeSectionService.getByNoticeId(notice.getId());
			if(StringUtil.isNotEmpty(noticeSections)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if(StringUtil.isNotEmpty(entity.getFlowDay())){
					calendar.add(Calendar.DATE, entity.getFlowDay());
				}else{
					calendar.add(Calendar.DATE, oneLevel);
				}
				Date planCompleteDate = calendar.getTime();
				for (NoticeSectionDTO noticeSection : noticeSections) {

					//添加通知书定时任务
					ExpireTask expireTask = new ExpireTask();
					expireTask.preInsert();
					expireTask.setDataId(noticeSection.getId());
					expireTask.setCreateDate(date);
					expireTask.setExpireDate(planCompleteDate);
					expireTask.setLockVersion(1);
					expireTask.setBussinessType(BussinessType.Notice.getStatus());
					ExpireTask et = expireTaskDao.getByDataId(noticeSection.getId(),BussinessType.Notice.getStatus());
					if (StringUtil.isEmpty(et)) {
						expireTaskDao.insert(expireTask);
					}
					
					FlowStartRequest request = new FlowStartRequest();
					request.setUserId(entity.getUserId());
					request.setProcessDefKey(entity.getFlowKey());
					request.setBusinessKey(noticeSection.getId());
					request.setSectionId(noticeSection.getSectionId());
					//request.setTaskUsers(taskUsers);
					String processName = notice.getNoticeNumberYear()+"年第"+notice.getNoticeNumberStr()+"号";
					request.setProcessName(processName);
					flows.add(request);
				}
			}
		}
		if(StringUtil.isEmpty(flows)){
			Shift.fatal(StatusCode.FLOW_EMPTY);
		}
		RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.startProcessInstance(flows);
		if(RestfulResponse.DEFAULT_OK !=reponse.getErrorCode()){
			Shift.fatal(StatusCode.FAIL,reponse.getMessage());
		}
		List<Map<String, String>> flowLists = reponse.getData();
		if (StringUtil.isNotEmpty(flowLists)) {
			defectFlowService.insert(flowLists);
			for (Map<String, String> flowMap : flowLists) {
				NoticeSectionDTO dto = new NoticeSectionDTO();
				dto.setId(flowMap.get("bussinessKey"));
				dto.setStatus(sign);
				dto.setSendPerson(entity.getUserId());
				dto.setSendDate(new Date());
				noticeSectionService.save(dto);
			//	noticeSectionService.updateStatus(flowMap.get("bussinessKey"), sign);
			}
		}
		//修改知书通状态
		noticeService.updateNoticeStatus(CollectionUtils.convertToString(list, ","),
				 NoticeStatus.AlreadyIssued.getStatus());
		return list.size();
	}
	//批量下发
	@Transactional
	public int startNotice(List<FlowStartUserDataRequest> entitys) {
		FlowStartUserDataRequest entity = new FlowStartUserDataRequest();
		Map<String,String> map = new HashMap<>();
		List<String> list = new ArrayList<>();
		
		entitys.forEach(f->{
			if(StringUtils.isNoneBlank(f.getId())&StringUtils.isNoneBlank(f.getFlowKey())){
				List<String> l = Arrays.asList(f.getId().split(","));
				l.forEach(a->{
					map.put(a, f.getFlowKey());
				});
				list.addAll(l);
			}else{
				Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
			}
			
			try {
				BeanUtils.copyProperties(entity, f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		entity.setId(null);
		entity.setFlowKey(null);
		
		List<FlowStartRequest> flows = new ArrayList<FlowStartRequest>();
		////////////
		String sign = NoticeStatus.Sign.getStatus();
		//List<String> list = Arrays.asList(entity.getId().split(","));
		////////////
		List<NoticeDTO> notices = noticeService.getByIds(list);
		
		for (NoticeDTO notice : notices) {
			List<NoticeSectionDTO> noticeSections = noticeSectionService.getByNoticeId(notice.getId());
			if(StringUtil.isNotEmpty(noticeSections)){
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if(StringUtil.isNotEmpty(entity.getFlowDay())){
					calendar.add(Calendar.DATE, entity.getFlowDay());
				}else{
					calendar.add(Calendar.DATE, oneLevel);
				}
				Date planCompleteDate = calendar.getTime();
				for (NoticeSectionDTO noticeSection : noticeSections) {
					
					//添加通知书定时任务
					ExpireTask expireTask = new ExpireTask();
					expireTask.preInsert();
					expireTask.setDataId(noticeSection.getId());
					expireTask.setCreateDate(date);
					expireTask.setExpireDate(planCompleteDate);
					expireTask.setLockVersion(1);
					expireTask.setBussinessType(BussinessType.Notice.getStatus());
					ExpireTask et = expireTaskDao.getByDataId(noticeSection.getId(),BussinessType.Notice.getStatus());
					if (StringUtil.isEmpty(et)) {
						expireTaskDao.insert(expireTask);
					}
					
					FlowStartRequest request = new FlowStartRequest();
					request.setUserId(entity.getUserId());
					request.setProcessDefKey(map.get(notice.getId()));
					request.setBusinessKey(noticeSection.getId());
					request.setSectionId(noticeSection.getSectionId());
					//request.setTaskUsers(taskUsers);
					String processName = notice.getNoticeNumberYear()+"年第"+notice.getNoticeNumberStr()+"号";
					request.setProcessName(processName);
					flows.add(request);
				}
			}
		}
		if(StringUtil.isEmpty(flows)){
			Shift.fatal(StatusCode.FLOW_EMPTY);
		}
		RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.startProcessInstance(flows);
		if(RestfulResponse.DEFAULT_OK !=reponse.getErrorCode()){
			Shift.fatal(StatusCode.FAIL,reponse.getMessage());
		}
		List<Map<String, String>> flowLists = reponse.getData();
		if (StringUtil.isNotEmpty(flowLists)) {
			defectFlowService.insert(flowLists);
			for (Map<String, String> flowMap : flowLists) {
				NoticeSectionDTO dto = new NoticeSectionDTO();
				dto.setId(flowMap.get("bussinessKey"));
				dto.setStatus(sign);
				dto.setSendPerson(entity.getUserId());
				dto.setSendDate(new Date());
				noticeSectionService.save(dto);
				//	noticeSectionService.updateStatus(flowMap.get("bussinessKey"), sign);
			}
		}
		//修改知书通状态
		noticeService.updateNoticeStatus(CollectionUtils.convertToString(list, ","),
				NoticeStatus.AlreadyIssued.getStatus());
		return list.size();
	}

	//签收
	@Transactional
	public int signNotice(NoticeSignTaskRequest entity) {
		String cancel = NoticeStatus.Cancel.getStatus();
		List<String> list = Arrays.asList(entity.getId().split(","));
		List<TaskHandleRequest> flows = new ArrayList<TaskHandleRequest>();
		if(StringUtil.isNotEmpty(list)){
			//通知书签收流程
			List<DefectFlow> defectFlows = defectFlowService.getBykeys(list);
			if(StringUtil.isNotEmpty(defectFlows)){
				for (DefectFlow defectFlow : defectFlows) {
					TaskHandleRequest request = new TaskHandleRequest();
					request.setUserId(entity.getUserId());
					request.setLeaderPass(entity.getLeaderPass());
					request.setTaskId(defectFlow.getTaskId());
					request.setProcessInstanceId(defectFlow.getProcessInstanceId());
					request.setComment(entity.getComment());
					request.setSectionId(entity.getSectionId());
					//下部操作人（默认签收本部门）
					//request.setTaskUsers(taskUsers);
					flows.add(request);
				}
			}
			
			RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.completeTask(flows);
			if(RestfulResponse.DEFAULT_OK !=reponse.getErrorCode()){
				Shift.fatal(StatusCode.FAIL,reponse.getMessage());
			}
			List<Map<String, String>> flowLists = reponse.getData();
			if (StringUtil.isNotEmpty(flowLists)) {
				defectFlowService.update(flowLists);
			} 
			noticeSectionService.updateStatus(CollectionUtils.convertToString(list, ","), cancel);
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, oneLevel);
			Date planCompleteDate = calendar.getTime();
			
			for (String noticeSectionId : list) {
				//更新签收人信息
				NoticeSectionDTO dto = new NoticeSectionDTO();
				dto.setReceiptDate(new Date());
				dto.setId(noticeSectionId);
				dto.setReceiptPerson(entity.getUserId());
				noticeSectionService.save(dto);
				//添加通知书定时任务
				ExpireTask expireTask = new ExpireTask();
				expireTask.preInsert();
				expireTask.setDataId(noticeSectionId);
				expireTask.setCreateDate(date);
				expireTask.setExpireDate(planCompleteDate);
				expireTask.setLockVersion(1);
				expireTask.setBussinessType(BussinessType.Notice.getStatus());
				ExpireTask et = expireTaskDao.getByDataId(noticeSectionId,BussinessType.Notice.getStatus());
				if (StringUtil.isEmpty(et)) {
					expireTaskDao.insert(expireTask);
				}

				/*//缺陷流程
				List<String> defectIds = noticeDefectService.getDefectIds(noticeSectionId);
				FlowStartUserDataRequest flowStartUserDataRequest = new FlowStartUserDataRequest();
				flowStartUserDataRequest.setId(CollectionUtils.convertToString(defectIds, ","));
				//发起缺陷流程
				flowStartUserDataRequest.setUserId(entity.getId());
				flowStartUserDataRequest.setFlowKey(entity.getDefectKey());
				flowStartUserDataRequest.setFlowAuditCode(entity.getDefectCode());
				flowStartUserDataRequest.setFlowNode(entity.getDefectNode());
				flowStartUserDataRequest.setSectionId(entity.getSectionId());
				flowService.start(flowStartUserDataRequest);
				//审核缺陷流程
				TaskRequest taskRequest = new TaskRequest();
				taskRequest.setId(CollectionUtils.convertToString(defectIds, ","));
				taskRequest.setLeaderPass(LEADERPASS);
				taskRequest.setUserId(entity.getId());
				taskRequest.setUserName(entity.getUserName());
				taskRequest.setSectionId(entity.getSectionId());
				flowService.auditDefect(taskRequest);
				//更新处通知书状态
				if(noticeSectionService.needUpdateStatus(noticeSectionId, true)){
					noticeService.updateNoticeStatus(noticeSectionId,
							feedback);
				}	*/
			}
			for (String noticeSectionId : list) {
				//检查是否需要更新处级状态
				boolean needUpdate = noticeSectionService.needUpdateStatus(noticeSectionId, true);
				if(needUpdate) {
					String noticeId = noticeSectionService.getNoticeIdByNoticeSectionId(noticeSectionId);
					if(StringUtil.isNotBlank(noticeId)) {
						noticeService.updateNoticeStatus(noticeId, NoticeStatus.Cancel.getStatus());
					}
				}
			}
		}
		
		return list.size();
	}

	//反馈
	@Transactional
	public int feedbackNotice(TaskRequest entity) {
		String cancel = NoticeStatus.Cancel.getStatus();
		List<String> list = Arrays.asList(entity.getId().split(","));
		List<TaskHandleRequest> flows = new ArrayList<TaskHandleRequest>();
		if(StringUtil.isNotEmpty(list)){
			List<DefectFlow> defectFlows = defectFlowService.getBykeys(list);
			if(StringUtil.isNotEmpty(defectFlows)){
				for (DefectFlow defectFlow : defectFlows) {
					TaskHandleRequest request = new TaskHandleRequest();
					request.setUserId(entity.getUserId());
					request.setLeaderPass(entity.getLeaderPass());
					request.setTaskId(defectFlow.getTaskId());
					request.setProcessInstanceId(defectFlow.getProcessInstanceId());
					request.setComment(entity.getComment());
					request.setSectionId(entity.getSectionId());
					flows.add(request);
				}
				RestfulResponse<List<Map<String, String>>> reponse = workflowFeignService.completeTask(flows);
				if(RestfulResponse.DEFAULT_OK !=reponse.getErrorCode()){
					Shift.fatal(StatusCode.FAIL,reponse.getMessage());
				}
				List<Map<String, String>> flowLists = reponse.getData();
				if (StringUtil.isNotEmpty(flowLists)) {
					defectFlowService.update(flowLists);
					for (Map<String, String> map : flowLists) {
						//更新处通知书状态
						if(noticeSectionService.needUpdateStatus(map.get("bussinessKey"), false)){
							noticeService.updateNoticeStatus(map.get("bussinessKey"),cancel);
						}
					}
				} 
			}
			noticeSectionService.updateStatus(CollectionUtils.convertToString(list, ","), cancel);
			noticeSectionService.updateFeedbackInfo(CollectionUtils.convertToString(list, ","), entity.getUserId(), 
					entity.getComment(), new Date());
			expireTaskDao.deleteByDataIds(list);
			for (String noticeSectionId : list) {
				
				//检查是否需要更新处级状态
				boolean needUpdate = noticeSectionService.needUpdateStatus(noticeSectionId, false);
				if(needUpdate) {
					String noticeId = noticeSectionService.getNoticeIdByNoticeSectionId(noticeSectionId);
					if(StringUtil.isNotBlank(noticeId)) {
						noticeService.updateNoticeStatus(noticeId, NoticeStatus.AlreadyFeedback.getStatus());
					}
				}
			}
		}
		return list.size();
	}

}
