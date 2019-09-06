package com.togest.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.config.DefectStatus;
import com.togest.request.CQueryFilter;
import com.togest.service.Defect1CService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * <p>Title: DefectRejectResource.java</p>
 * <p>Description: 缺陷数据下发</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2019年2月21日下午2:47:32
 * @version 1.0
 */
@RequestMapping("handle")
@RestController
public class HandleDefectResource {

	@Autowired
	private Defect1CService c1Service;
	
	@RequestMapping(value = "defectStart", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷下发数据")
	public String handleDefectStart(HttpServletRequest request) {
		c1Service.handleDefectStart();
		return "susscess";
	}
	
	@RequestMapping(value = "defectAudit", method = RequestMethod.GET)
	@ApiOperation(value = "审核数据")
	public String handleDefectAudit(HttpServletRequest request) {
		c1Service.handleDefectAudit();
		return "susscess";
	}
	
	@RequestMapping(value = "defectefectReceive", method = RequestMethod.GET)
	@ApiOperation(value = "接受数据")
	public String handleDefectReceive(HttpServletRequest request) {
		c1Service.handleDefectReceive();
		return "susscess";
	}
}
