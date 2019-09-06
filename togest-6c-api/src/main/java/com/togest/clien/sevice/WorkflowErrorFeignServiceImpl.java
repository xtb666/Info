package com.togest.clien.sevice;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.togest.client.resquest.DefectFlowrRejectRequest;
import com.togest.client.resquest.FlowStartRequest;
import com.togest.client.resquest.ProcessInstanceDelRequest;
import com.togest.client.resquest.TaskHandleRequest;
import com.togest.model.resposne.RestfulResponse;
@Component
public class WorkflowErrorFeignServiceImpl implements WorkflowFeignService{

	@Override
	public RestfulResponse<List<Map<String, String>>> startProcessInstance(
			List<FlowStartRequest> list) {
		this.throwException();
				return null;
		
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> completeTask(
			List<TaskHandleRequest> list) {
		this.throwException();
		return null;
	}

	public void throwException(){
		throw new RuntimeException("调用工作流引擎异常");
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> deleteTask(
			List<ProcessInstanceDelRequest> list) {
		//this.throwException();
		return null;
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> rejectTask(List<DefectFlowrRejectRequest> dFR) {
		this.throwException();
		return null;
	}

}
