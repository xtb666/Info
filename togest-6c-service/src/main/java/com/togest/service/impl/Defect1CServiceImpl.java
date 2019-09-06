package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.WorkflowFeignService;
import com.togest.client.resquest.DefectFlowrRejectRequest;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectAuditStatus;
import com.togest.config.DefectStatus;
import com.togest.dao.Defect1CDao;
import com.togest.dao.DefectCheckHandleDao;
import com.togest.dao.DefectDao;
import com.togest.dao.DefectHandleInfoDao;
import com.togest.dao.DefectReformHandleDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectHandleInfo;
import com.togest.domain.DefectReformHandle;
import com.togest.domain.Page;
import com.togest.domain.TaskFlowResponse;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC1Request;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.ReTaskC1Request;
import com.togest.request.TaskRejectRequest;
import com.togest.request.TaskRequest;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC1Response;
import com.togest.service.Defect1CService;
import com.togest.service.DefectFlowService;
import com.togest.service.FlowService;
import com.togest.utils.PageUtils;

@Service
public class Defect1CServiceImpl extends SixCServiceImpl<Defect1CDao, Defect1CDTO, DefectC1Response, DefectC1Form>
		implements Defect1CService {

	 private static final Logger LOGGER =
	 LoggerFactory.getLogger(Defect1CServiceImpl.class);

	@Autowired
	private DefectCheckHandleDao checkHandleDao;
	@Autowired
	private DefectReformHandleDao reformHandleDao;
	@Autowired
	private DefectDao defectDao;
	@Autowired
	private DefectHandleInfoDao defectHandleInfoDao;
	@Autowired
	private DefectFlowService defectFlowService;
	@Autowired
	private WorkflowFeignService workflowFeignService;
	@Autowired
	private FlowService flowService;

	private static final String LEADERPASS = "true";
	
	@Override
	@DictAggregation
	public Page<DefectC1Form> findC1FormForNotice(Page page, CQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findC1FormForNotice(entity));
	}

	@Override
	@DictAggregation
	public List<DefectC1Form> findC1DefectByIds(List<String> ids) {
		return dao.findC1DefectByIds(ids);
	}

	@Override
	public int updateDefectC1Request(DefectC1Request entity) {

		DefectCheckHandle checkHandle = entity.getDefectCheckHandle();
		if (StringUtil.isNotEmpty(checkHandle)) {
			checkHandle.setId(entity.getId());
			checkHandleDao.update(checkHandle);
		}
		DefectReformHandle reformHandle = entity.getDefectReformHandle();
		if (StringUtil.isNotEmpty(reformHandle)) {
			reformHandle.setId(entity.getId());
			reformHandleDao.update(reformHandle);
		}
		return save(entity);
	}

	public List<DefectC1Form> findC1FormBySectionNotice(List<String> noticeSectionIds, String sectionId) {

		return dao.findC1FormBySectionNotice(noticeSectionIds, sectionId);
	}

	@Override
	public void auditRegister(CQueryFilter entity, String userId, String userName, String sectionId) {
		List<Map<String,String>> list = dao.findDefectFormList(entity);
		if(!CollectionUtils.isEmpty(list)) {
			List<String> idList = new ArrayList<>();
			archiveDefect(userId, userName, sectionId, list, idList);
			if(!CollectionUtils.isEmpty(idList)) {
				for(String id : idList) {
					try {
						restart(userId, userName, sectionId, id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				LOGGER.debug("总记录数--->list:{}", list.size());
				LOGGER.debug("总记录数--->idList:{}", idList.size());
			}
		}
	}

	private void archiveDefect(String userId, String userName, String sectionId, List<Map<String, String>> list,
			List<String> idList) {
		list.forEach(map ->{
			String taskId = map.get("id_");
//				String processInstanceId = map.get("processInstanceId");
			TaskRejectRequest taskRejectRequest = new TaskRejectRequest();
			taskRejectRequest.setId(map.get("id"));
			taskRejectRequest.setLeaderPass("false");
			taskRejectRequest.setActivityNodeId("usertask1");
			taskRejectRequest.setComment("驳回至上缺陷登记");
			taskRejectRequest.setUserId(userId);
			taskRejectRequest.setUserName(userName);
			taskRejectRequest.setSectionId(sectionId);
			try {
				archiveDefect(taskRejectRequest, taskId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			idList.add(map.get("id"));
		});
	}

	@Transactional
	public int archiveDefect(TaskRejectRequest entity, String taskId) {
		if (StringUtil.isEmpty(entity.getComment())) {
			entity.setComment("");
		}
		List<String> list = Arrays.asList(entity.getId().split(","));
		String activityNodeId = entity.getActivityNodeId();
		String userId = entity.getUserId();
		boolean isPass = (LEADERPASS.equals(entity.getLeaderPass()));

		List<TaskFlowResponse> taskFlows = defectDao.getTaskFlowResponseByIds(list);
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
				request.setTaskId(taskId);
				request.setProcessInstanceId(taskFlow.getProcessInstanceId());
				request.setUserId(userId);
				request.setComment(entity.getComment());
				dFRList.add(request);
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
			defectDao.updateDefectStatusBatch(list, defectStatus);
			RestfulResponse<List<Map<String, String>>> response = workflowFeignService.rejectTask(dFRList);
			if (RestfulResponse.DEFAULT_OK != response.getErrorCode()) {
				Shift.fatal(StatusCode.FAIL, response.getMessage());
			}
			List<Map<String, String>> flowLists = response.getData();
			defectFlowService.update(flowLists);
		}
		return list.size();
	}
	
	public void restart(String userId, String userName, String sectionId, String id) {
		ReTaskC1Request c1Request = new ReTaskC1Request();
		c1Request.setId(id);
		c1Request.setUserId(userId);
		c1Request.setUserName(userName);
		c1Request.setSectionId(sectionId);
		c1Request.setLeaderPass("true");
		flowService.reStart(c1Request);
	}

	@Override
	public void handleDefectStart() {
		List<String> idList = dao.handleDefectStart();
		start(idList);
	}

	private void start(List<String> idList) {
		if(!CollectionUtils.isEmpty(idList)) {
			ExecutorService pool = Executors.newFixedThreadPool(10);
			try {
				for(String id:idList) {
					pool.execute(new Runnable() {
						@Override
						public void run() {
							FlowStartUserDataRequest entity = new FlowStartUserDataRequest();
							entity.setId(id);
							entity.setFlowKey("defect1c");
							entity.setUserId("34c9084cf615455182649aa9830cc3e8");
							entity.setDeptId("f4d5b2de07ae4e52afb263f98f263a3f");
							entity.setSectionId("7c5e13b4dd8d4f599bfb11a9a8ef4d16");
							try {
								flowService.start(entity);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				pool.shutdown();
			}
		}
	}

	@Override
	public void handleDefectAudit() {
		List<String> idList = dao.handleDefectAudit();
		auditDefect(idList);
	}

	private void auditDefect(List<String> idList) {
		if(!CollectionUtils.isEmpty(idList)) {
			ExecutorService pool = Executors.newFixedThreadPool(10);
			try {
				for(String id:idList) {
					pool.execute(new Runnable() {
						@Override
						public void run() {
							TaskRequest entity = new TaskRequest();
							entity.setId(id);
							entity.setUserId("34c9084cf615455182649aa9830cc3e8");
							entity.setUserName("李枫");
							entity.setSectionId("7c5e13b4dd8d4f599bfb11a9a8ef4d16");
							entity.setLeaderPass("true");
							try {
								flowService.auditDefect(entity);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				pool.shutdown();
			}
		}
	}

	@Override
	public void handleDefectReceive() {
		List<String> idList = dao.handleDefectReceive();
		receiveDefect(idList);
	}

	private void receiveDefect(List<String> idList) {
		if(!CollectionUtils.isEmpty(idList)) {
			for(String id:idList) {
				TaskRequest entity = new TaskRequest();
				entity.setId(id);
				entity.setUserId("34c9084cf615455182649aa9830cc3e8");
				entity.setUserName("李枫");
				entity.setSectionId("7c5e13b4dd8d4f599bfb11a9a8ef4d16");
				entity.setLeaderPass("true");
				flowService.receiveDefect(entity);
			}
		}
	}

	@Override
	public void receiveRegister(CQueryFilter entity, String userId, String userName, String sectionId) {
		List<Map<String,String>> list = dao.findDefectFormList(entity);
		if(!CollectionUtils.isEmpty(list)) {
			List<String> idList = new ArrayList<>();
			archiveDefect(userId, userName, sectionId, list, idList);
			if(!CollectionUtils.isEmpty(idList)) {
				List<String> auditIdList = new ArrayList<>();
				for(String id : idList) {
					try {
						restart(userId, userName, sectionId, id);
						auditIdList.add(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(!CollectionUtils.isEmpty(auditIdList)) {
					auditDefect(auditIdList);
				}
				LOGGER.debug("总记录数--->list:{}", list.size());
				LOGGER.debug("总记录数--->idList:{}", idList.size());
			}
		}
		
	}

	@Override
	public void rectificationRegister(CQueryFilter entity, String userId, String userName, String sectionId) {
		List<Map<String,String>> list = dao.findDefectFormList(entity);
		if(!CollectionUtils.isEmpty(list)) {
			List<String> idList = new ArrayList<>();
			archiveDefect(userId, userName, sectionId, list, idList);
			if(!CollectionUtils.isEmpty(idList)) {
				List<String> auditIdList = new ArrayList<>();
				for(String id : idList) {
					try {
						restart(userId, userName, sectionId, id);
						auditIdList.add(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(!CollectionUtils.isEmpty(auditIdList)) {
					auditDefect(auditIdList);
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					receiveDefect(auditIdList);
				}
				LOGGER.debug("总记录数--->list:{}", list.size());
				LOGGER.debug("总记录数--->idList:{}", idList.size());
			}
		}
	}

}
