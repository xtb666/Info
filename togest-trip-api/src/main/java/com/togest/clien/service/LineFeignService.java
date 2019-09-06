package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.model.resposne.RestfulResponse;

@FeignClient(value = "jcwBase-service", fallback = LineErrorService.class)
public interface LineFeignService {
	
	@RequestMapping(value = "lines/name/id", method = RequestMethod.GET)
	public RestfulResponse<String> getIdByName(@RequestParam("name") String name);
	
	@RequestMapping(value = "lines/id/psaName", method = RequestMethod.GET)
	public RestfulResponse<List<String>> getPsaNamesByLineId(@RequestParam("lineId") String lineId);
	
	@RequestMapping(value = "lines/ids/names", method = RequestMethod.GET)
	public RestfulResponse<List<Map<String, String>>> getNameByIds(@RequestParam("id") String id);

	@RequestMapping(value = "lines/names/ids", method = RequestMethod.GET)
	public RestfulResponse<List<Map<String, String>>> getIdByNames(@RequestParam("name") String name);
}
