package com.togest.clien.sevice;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.model.resposne.RestfulResponse;

@FeignClient(value = "base-service")
public interface FlowAuditFeignService {

	@RequestMapping(value = "flowAudits/code", method = RequestMethod.GET)
	public RestfulResponse<String> getFlowAuditByCodeAndSectionId(
			@RequestParam("code") String code,
			@RequestParam("sectionId") String sectionId);

	@RequestMapping(value = "flowAudits/node", method = RequestMethod.GET)
	public RestfulResponse<String> getFlowAuditByNodeAndSectionId(
			@RequestParam("node") String node,
			@RequestParam("keyId") String keyId,
			@RequestParam("code") String code,
			@RequestParam("sectionId") String sectionId);
}
