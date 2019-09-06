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
 * <p>Description: 缺陷审核列表归档 至登记</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2019年2月21日下午2:47:32
 * @version 1.0
 */
@RequestMapping("archive")
@RestController
public class DefectRejectResource {

	@Autowired
	private Defect1CService c1Service;
	
	@RequestMapping(value = "audit/register", method = RequestMethod.GET)
	@ApiOperation(value = "审核缺陷分页数据")
	public String auditRegister(HttpServletRequest request) {
		CQueryFilter entity = new CQueryFilter();
//		UserInfo info = TokenUtil.getUser(request);
		entity.setDefectStatus(DefectStatus.DefectAudit.getStatus());
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		c1Service.auditRegister(entity, "34c9084cf615455182649aa9830cc3e8", "李枫", "7c5e13b4dd8d4f599bfb11a9a8ef4d16");
		return "susscess";
	}
	
	@RequestMapping(value = "receive/register", method = RequestMethod.GET)
	@ApiOperation(value = "车间接收分页数据")
	public String receiveRegister(HttpServletRequest request) {
		CQueryFilter entity = new CQueryFilter();
//		UserInfo info = TokenUtil.getUser(request);
		entity.setDefectStatus(DefectStatus.ShopReception.getStatus());
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		c1Service.receiveRegister(entity, "34c9084cf615455182649aa9830cc3e8", "李枫", "7c5e13b4dd8d4f599bfb11a9a8ef4d16");
		return "susscess";
	}
	
	@RequestMapping(value = "rectification/register", method = RequestMethod.GET)
	@ApiOperation(value = "复核整改分页数据")
	public String rectificationRegister(HttpServletRequest request) {
		CQueryFilter entity = new CQueryFilter();
//		UserInfo info = TokenUtil.getUser(request);
		entity.setDefectStatus(DefectStatus.ReviewRectification.getStatus());
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		c1Service.rectificationRegister(entity, "34c9084cf615455182649aa9830cc3e8", "李枫", "7c5e13b4dd8d4f599bfb11a9a8ef4d16");
		return "susscess";
	}
}
