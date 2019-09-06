package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.model.resposne.RestfulResponse;

import io.swagger.annotations.ApiOperation;

@FeignClient(value = "jcwBase-service", fallback = JcwServiceErrorClient.class)
public interface JcwServiceClient {
							
	@RequestMapping(value = "/supplyPowerSections/names/ids", method = RequestMethod.GET)
	@ApiOperation(value = "通过供电臂名称获取供电臂id")
	public RestfulResponse<List<Map<String, String>>> getIdByNames(@RequestParam("name") String name);
			
	@RequestMapping(value = "/pavilions/names/ids", method = RequestMethod.GET)
	@ApiOperation(value = "通过变电所名称获取变电所id")
	public RestfulResponse<List<Map<String, String>>> getPavilionsIdByNames(@RequestParam("name") String name);
	
	
}
