package com.togest.clien.sevice;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.client.resquest.DefectFlowrRejectRequest;
import com.togest.client.resquest.FlowStartRequest;
import com.togest.client.resquest.ProcessInstanceDelRequest;
import com.togest.client.resquest.TaskHandleRequest;
import com.togest.model.resposne.RestfulResponse;

@FeignClient(name = "workflow-service", fallback = WorkflowErrorFeignServiceImpl.class)
public interface WorkflowFeignService {

	@PostMapping(value = "/workflow/v2.0/processInstances/keys/start")
	public RestfulResponse<List<Map<String, String>>> startProcessInstance(
			@RequestBody List<FlowStartRequest> list);

	@PostMapping(value = "/workflow/v2.0/processinstances/tasks")
	public RestfulResponse<List<Map<String, String>>> completeTask(
			@RequestBody List<TaskHandleRequest> list);

	@DeleteMapping(value = "/workflow/v2.0/processinstances")
	public RestfulResponse<List<Map<String, String>>> deleteTask(@RequestBody List<ProcessInstanceDelRequest> list);
	
	@PostMapping(value = "/workflow/v2.0/tasks/rejection-to-node")
	public RestfulResponse<List<Map<String, String>>> rejectTask(@RequestBody List<DefectFlowrRejectRequest> dFR);

}
