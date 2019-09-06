package com.togest.web;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DataScope;
import com.togest.domain.Page;
import com.togest.domain.Point5CDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.Point5CService;
import com.togest.util.Data;
import com.togest.util.DataRightUtil;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

@RequestMapping("5C")
@RestController
public class Point5CResource {

	@Autowired
	private Point5CService point5CService;

	@RequestMapping(value = "points", method = RequestMethod.GET)
	@ApiOperation(value = "获取监测点数据")
	public RestfulResponse<Point5CDTO> getPoint5C(HttpServletRequest request,
			String id) {
		Point5CDTO entity = point5CService.get(id);
		return new RestfulResponse<Point5CDTO>(entity);
	}

	@AddDataSectionPut
	@RequestMapping(value = "points", method = RequestMethod.POST)
	@ApiOperation(value = "更新监测点")
	public RestfulResponse<Boolean> savePoint5C(HttpServletRequest request,
			@RequestBody Point5CDTO entity) {
		if (entity != null) {
			UserInfo info = TokenUtil.getUser(request);
			entity.setSectionId(info.getSectionId());
		}
		point5CService.save(entity);
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "points", method = RequestMethod.DELETE)
	@ApiOperation(value = "刪除监测点数据")
	public RestfulResponse<Boolean> deletePoint5C(HttpServletRequest request,
			String id) {
		point5CService.deletes(id);
		return new RestfulResponse<Boolean>(true);
	}
	//@DataControlPut(authCode="LOOK_MONITORING_SITE_5C",isToken=true,isDataControl=true)
	@RequestMapping(value = "points/lists", method = RequestMethod.GET)
	@ApiOperation(value = "获取监测点数据")
	public RestfulResponse<List<Point5CDTO>> getPoint5CLists(
			HttpServletRequest request, Point5CDTO entity) {
		//String dataScope = TokenUtil.getDataScopeTokenData(request, authCode);
		/*if (!(StringUtil.isNotBlank(dataScope) && DataScope.AllScope
				.getStatus().equals(dataScope))) {
			UserInfo info = TokenUtil.getUser(request);
			entity.setSectionId(info.getSectionId());
		}*/
		List<Point5CDTO> list = point5CService.findList(entity);
		return new RestfulResponse<List<Point5CDTO>>(list);
	}

	//@DataControlPut(authCode="LOOK_MONITORING_SITE_5C",isToken=true,isDataControl=true)
	@RequestMapping(value = "points/pages", method = RequestMethod.GET)
	@ApiOperation(value = "获取监测点数据")
	public RestfulResponse<Page<Point5CDTO>> getPoint5CPages(
			HttpServletRequest request, Page page, Point5CDTO entity,
			String authCode) {
		/*String dataScope = TokenUtil.getDataScopeTokenData(request, authCode);
		if (!(StringUtil.isNotBlank(dataScope) && DataScope.AllScope
				.getStatus().equals(dataScope))) {
			UserInfo info = TokenUtil.getUser(request);
			entity.setSectionId(info.getSectionId());
		}*/
		Page<Point5CDTO> pg = point5CService.findPage(page, entity);
		return new RestfulResponse<Page<Point5CDTO>>(pg);
	}

}
