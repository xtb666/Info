package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.model.resposne.RestfulResponse;

@FeignClient(value = "jcwBase-service", fallback = SupplyArmErrorFeignServiceImpl.class)
public interface SupplyArmFeignService {

	@RequestMapping(value = "supplyArms/names/ids", method = RequestMethod.GET)
	public RestfulResponse<List<Map<String, String>>> getIdByNames(@RequestParam("name") String name);
	
	@RequestMapping(value = "supplyArms/findByCondition", method = RequestMethod.GET)
	public RestfulResponse<List<Map<String, String>>> findByCondition(
			@RequestParam("plId") String plId,
			@RequestParam("direction") String direction, 
			@RequestParam("startStationName") String startStationName, 
			@RequestParam("endStationName") String endStationName,
			@RequestParam("type") int type);
}
