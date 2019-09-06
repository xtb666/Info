package com.togest.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ListUtils;
import com.togest.dao.DefectDao;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.ApplyDelayRequest;
import com.togest.request.CheckReformRequest;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.ReTaskC1Request;
import com.togest.request.ReTaskC2Request;
import com.togest.request.ReTaskC3Request;
import com.togest.request.ReTaskC4Request;
import com.togest.request.ReTaskC5Request;
import com.togest.request.TaskRejectRequest;
import com.togest.request.TaskRequest;
import com.togest.service.FlowService;
import com.togest.service.NoticeDefectService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

import io.swagger.annotations.ApiOperation;

//@RequestMapping(value = "1C")
@RestController
public class DefectFlowResource {
	@Autowired
	private FlowService flowService;
	@Autowired
	private NoticeDefectService noticeDefectService;
	@Autowired
	private DefectDao defectDao;

	/**
	 * 缺陷发起
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "defect/start", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷发起")
	public RestfulResponse<Integer> start(HttpServletRequest request,
			@RequestBody FlowStartUserDataRequest entity) {
		
		//检测是否已经被通知书关联
//		List<String> list = Arrays.asList(entity.getId().split(","));
//		List<String> defectIds = noticeDefectService.hasDefect(list);
//		if(StringUtil.isNotEmpty(defectIds)){
//			//List<Defect> defects = defectDao.getByKeys(defectIds);
//			Shift.fatal(StatusCode.NOTICE_ERROR, defectIds);
//		}
		UserInfo info = TokenUtil.getUser(request);
		entity.setSectionId(info.getSectionId());
		 int count =  flowService.start(entity);
		return new RestfulResponse<Integer>(count);
	}
	/**
	 * 缺陷发起
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "defect/startBatch", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷批量发起")
	public RestfulResponse<Integer> startBatch(HttpServletRequest request,
			@RequestBody List<FlowStartUserDataRequest> entityList) {
		
		//检测是否已经被通知书关联
//		List<String> list = Arrays.asList(entity.getId().split(","));
//		List<String> defectIds = noticeDefectService.hasDefect(list);
//		if(StringUtil.isNotEmpty(defectIds)){
//			//List<Defect> defects = defectDao.getByKeys(defectIds);
//			Shift.fatal(StatusCode.NOTICE_ERROR, defectIds);
//		}
		int count =0;
		UserInfo info = TokenUtil.getUser(request);
		if(ListUtils.isNotEmpty(entityList)){
			for(int i =0; i<entityList.size();i++){
				FlowStartUserDataRequest entity = entityList.get(i);
				entity.setSectionId(info.getSectionId());
				int n =  flowService.start(entity);
				count +=n;
			};
		}
		return new RestfulResponse<Integer>(count);
	}

	/**
	 * 缺陷重新发起
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "defect/reStart/1C", method = RequestMethod.POST)
	@ApiOperation(value = "1C缺陷重新发起")
	public RestfulResponse<Integer> start(HttpServletRequest request,
			@RequestBody ReTaskC1Request entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.reStart(entity);
		return new RestfulResponse<Integer>(count);
	}

	@RequestMapping(value = "defect/reStart/2C", method = RequestMethod.POST)
	@ApiOperation(value = "2C缺陷重新发起")
	public RestfulResponse<Integer> start(HttpServletRequest request,
			@RequestBody ReTaskC2Request entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.reStart(entity);
		return new RestfulResponse<Integer>(count);
	}

	@RequestMapping(value = "defect/reStart/3C", method = RequestMethod.POST)
	@ApiOperation(value = "3C缺陷重新发起")
	public RestfulResponse<Integer> start(HttpServletRequest request,
			@RequestBody ReTaskC3Request entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.reStart(entity);
		return new RestfulResponse<Integer>(count);
	}

	@RequestMapping(value = "defect/reStart/4C", method = RequestMethod.POST)
	@ApiOperation(value = "4C缺陷重新发起")
	public RestfulResponse<Integer> start(HttpServletRequest request,
			@RequestBody ReTaskC4Request entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.reStart(entity);
		return new RestfulResponse<Integer>(count);
	}

	@RequestMapping(value = "defect/reStart/5C", method = RequestMethod.POST)
	@ApiOperation(value = "5C缺陷重新发起")
	public RestfulResponse<Integer> start(HttpServletRequest request,
			@RequestBody ReTaskC5Request entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.reStart(entity);
		return new RestfulResponse<Integer>(count);
	}

	/**
	 * 缺陷审批办理(todo 办理流程、更新缺陷状态、审核人、审核日期、审核标志，更新当前流程信息表考虑审核通过不通过，id逗号拆分)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "defect/audit", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷审批")
	public RestfulResponse<Integer> audit(HttpServletRequest request,
			@RequestBody TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.auditDefect(entity);
		return new RestfulResponse<Integer>(count);
	}

	/**
	 * 车间接收办理(todo 办理流程、更新缺陷状态、更新当前流程信息表考虑拒绝接收，id逗号拆分)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "defect/receive", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷车间接收办理")
	public RestfulResponse<Integer> receive(@RequestBody TaskRequest request) {
		int count = flowService.receiveDefect(request);
		return new RestfulResponse<Integer>(count);
	}

	/**
	 * 销号(todo 办理流程、更新缺陷状态、更新当前流程信息表考虑退回)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "defect/reviewRectification", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷复核整改")
	public RestfulResponse<Integer> reviewRectification(
			@RequestBody CheckReformRequest request) {
		flowService.reviewRectificationDefect(request);
		return new RestfulResponse<Integer>(1);
	}

	/**
	 * 缺陷申请延期
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "defect/applyDelay", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷申请延期")
	public RestfulResponse<Integer> applyDelay(HttpServletRequest request,
			@RequestBody ApplyDelayRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setUserId(info.getId());
		int count = flowService.applyDelay(entity,info.getSectionId());
		return new RestfulResponse<Integer>(count);
	}

	/**
	 * 缺陷延期
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "defect/delay", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷延期")
	public RestfulResponse<Integer> delay(HttpServletRequest request,@RequestBody TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setUserId(info.getId());
		int count = flowService.delayAuditDefect(entity);
		return new RestfulResponse<Integer>(count);
	}

	/**
	 * 销号(todo 办理流程、更新缺陷状态、更新当前流程信息表考虑退回)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "defect/cancel", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷销号")
	public RestfulResponse<Integer> cance(HttpServletRequest request,
			@RequestBody TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.cancelDefect(entity);
		return new RestfulResponse<Integer>(count);
	}
	
	@RequestMapping(value = "defect/archive", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷归档")
	public RestfulResponse<Integer> archive(HttpServletRequest request,
			@RequestBody TaskRejectRequest entity ) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserId(info.getId());
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int count = flowService.archiveDefect(entity);
		return new RestfulResponse<Integer>(count);
	}
	
	@AddDataSectionPut
	@RequestMapping(value = "defect/saveReviewRectification", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷复核整改信息保存")
	public RestfulResponse<Integer> saveDefectReviewRectification(HttpServletRequest request,
			@RequestBody CheckReformRequest entity) {
		int count = flowService.saveDefectReviewRectification(entity);
		return new RestfulResponse<Integer>(count);
	}

}
