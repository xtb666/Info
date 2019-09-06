package com.togest.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.model.resposne.RestfulResponse;
import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.NoticeSignTaskRequest;
import com.togest.request.TaskRequest;
import com.togest.service.NoticeFlowService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

@RestController
public class NoticeFlowResource {

	@Autowired
	private NoticeFlowService noticeFlowService;

	// 发布
	@RequestMapping(value = "notice/start", method = RequestMethod.POST)
	@ApiOperation(value = "通知书发起")
	public RestfulResponse<Integer> startNotice(HttpServletRequest request,
			@RequestBody FlowStartUserDataRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setSectionId(info.getSectionId());
		int rs = noticeFlowService.startNotice(entity);
		return new RestfulResponse<Integer>(rs);
	}
	// 发布
	@RequestMapping(value = "notice/startList", method = RequestMethod.POST)
	@ApiOperation(value = "通知书发起")
	public RestfulResponse<Integer> startNotice(HttpServletRequest request,
			@RequestBody List<FlowStartUserDataRequest> entitys) {
		List<FlowStartUserDataRequest> list = new ArrayList<FlowStartUserDataRequest>();
		UserInfo info = TokenUtil.getUser(request);
		for(FlowStartUserDataRequest f :entitys){
			f.setSectionId(info.getSectionId());
		}
		int rs = noticeFlowService.startNotice(entitys);
		return new RestfulResponse<Integer>(rs);
	}

	// 签收
	@RequestMapping(value = "notice/sign", method = RequestMethod.POST)
	@ApiOperation(value = "通知书签收")
	public RestfulResponse<Integer> signNotice(HttpServletRequest request,
			@RequestBody NoticeSignTaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int rs = noticeFlowService.signNotice(entity);
		return new RestfulResponse<Integer>(rs);
	}

	// 反馈
	@RequestMapping(value = "notice/feedback", method = RequestMethod.POST)
	@ApiOperation(value = "通知书反馈")
	public RestfulResponse<Integer> feedbackNotice(HttpServletRequest request,
			@RequestBody TaskRequest entity) {
		UserInfo info = TokenUtil.getUser(request);
		entity.setUserName(info.getName());
		entity.setSectionId(info.getSectionId());
		int rs = noticeFlowService.feedbackNotice(entity);
		return new RestfulResponse<Integer>(rs);
	}
}
