package com.togest.service;

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

public interface FlowService {

	public int start(FlowStartUserDataRequest entity);

	public int reStart(ReTaskC1Request entity);

	public int reStart(ReTaskC2Request entity);

	public int reStart(ReTaskC3Request entity);

	public int reStart(ReTaskC4Request entity);
	
	public int reStart(ReTaskC5Request entity);

	public int auditDefect(TaskRequest entity);

	public int receiveDefect(TaskRequest entity);

	public int reviewRectificationDefect(CheckReformRequest entity);

	public int cancelDefect(TaskRequest entity);

	public int delayAuditDefect(TaskRequest entity);
	
	public int applyDelay(ApplyDelayRequest delay,String sectionId);

	public int archiveDefect(TaskRejectRequest entity);
	
	//缺陷复核整改信息保存
	public int saveDefectReviewRectification(CheckReformRequest entity);
}
