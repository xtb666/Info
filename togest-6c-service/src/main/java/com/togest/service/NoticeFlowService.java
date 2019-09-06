package com.togest.service;

import java.util.List;

import com.togest.request.FlowStartUserDataRequest;
import com.togest.request.NoticeSignTaskRequest;
import com.togest.request.TaskRequest;

public interface NoticeFlowService {
	// 发布
	public int startNotice(List<FlowStartUserDataRequest> entitys);

	// 签收
	public int signNotice(NoticeSignTaskRequest entity);

	// 反馈
	public int feedbackNotice(TaskRequest entity);

	public int startNotice(FlowStartUserDataRequest entity);
}
