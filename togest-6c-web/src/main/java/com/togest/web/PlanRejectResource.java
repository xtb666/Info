package com.togest.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.request.PlanQueryFilter;
import com.togest.service.C2PlanService;
import com.togest.service.PlanCommonService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * <p>Title: DefectRejectResource.java</p>
 * <p>Description: 缺陷审核列表归档 至登记</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2019年3月4日下午2:47:32
 * @version 1.0
 */
@RequestMapping("plan/archive")
@RestController
public class PlanRejectResource {

	@Autowired
	private C2PlanService c2PlanService;
	
	@RequestMapping(value = "audit/register", method = RequestMethod.GET)
	@ApiOperation(value = "审核计划分页数据")
	public String auditRegister(HttpServletRequest request) {
		PlanQueryFilter entity = new PlanQueryFilter();
		entity.setExecuteStatus("2");
		entity.setSystemId("C2");
		c2PlanService.auditRegister(entity, "34c9084cf615455182649aa9830cc3e8", "李枫", "7c5e13b4dd8d4f599bfb11a9a8ef4d16");
		return "susscess";
	}
}
