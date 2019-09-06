package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.common.annotation.RequestLogging;
import com.togest.domain.Naming;
import com.togest.model.resposne.RestfulResponse;

@FeignClient(value = "base-service", fallback = DeptFeignErrorServiceImpl.class)
public interface DeptFeignService {
	
	@RequestMapping(value = "dept/ids/names", method = RequestMethod.GET)
	public RestfulResponse<List<Map<String, String>>> getNameById(
			@RequestParam("id") String id);

	@RequestMapping(value = "dept/names/ids", method = RequestMethod.GET)
	public RestfulResponse<List<Map<String, String>>> getIdByName(
			@RequestParam("name") String name,@RequestParam("sectionId")String sectionId);
	
	@RequestMapping(value = "dept/parents", method = RequestMethod.GET)
	public RestfulResponse<Naming> getParentDept(@RequestParam("id") String id);
	
	@RequestMapping(value = "dept/ids/parents", method = RequestMethod.GET)
	@RequestLogging
	public RestfulResponse<List<Map<String, String>>> getParentDepts(@RequestParam("ids") String ids);
	
	@RequestMapping(value = "dept/id/code", method = RequestMethod.GET)
	@RequestLogging
	public RestfulResponse<String> checkDeptType(@RequestParam("id") String id, @RequestParam("code") String code);
	
	@RequestMapping(value = "dept/deptTypeCode", method = RequestMethod.GET)
	@RequestLogging
	public RestfulResponse<List<Map<String,String>>> getDeptByDeptTypeCode(@RequestParam("deptTypeCode") String deptTypeCode);
}
