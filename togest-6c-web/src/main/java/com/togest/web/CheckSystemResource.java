package com.togest.web;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.CheckSystemDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.CheckSystemService;
//@CrossOrigin(origins="*",allowCredentials="true",allowedHeaders={"x-requested-with,content-type"},methods={RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
//@RequestMapping("/6C")
public class CheckSystemResource {
	@Autowired
	private CheckSystemService checkSystemService;

	// 新增，更新系统 V
	@RequestMapping(value = "checkSystems", method = RequestMethod.POST)
	@ApiOperation(value = "新增系统数据信息")
	public RestfulResponse<Integer> isnertCheckSystem(HttpServletRequest request,
			@ModelAttribute CheckSystemDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null
				&& StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		entity.setCreateIp(request.getRemoteHost());
		entity.setUpdateIp(request.getRemoteHost());
		int i = checkSystemService.save(entity);
		return new RestfulResponse<>(i);
	}

	// 新增，更新系统 V
	@RequestMapping(value = "checkSystems", method = RequestMethod.PUT)
	@ApiOperation(value = "更新系统数据信息")
	public RestfulResponse<Integer> updateCheckSystem(HttpServletRequest request,
			@ModelAttribute CheckSystemDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null
				&& StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		entity.setUpdateIp(request.getRemoteHost());
		int i = checkSystemService.save(entity);
		return new RestfulResponse<>(i);
	}

	// 假删系统 V
	@RequestMapping(value = "checkSystems", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除系统数据信息")
	public RestfulResponse<Integer> deleteCheckSystemFalse(
			HttpServletRequest request, String id, String deleteBy) {
		if (StringUtil.isBlank(id) || StringUtil.isBlank(deleteBy)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		String ip = request.getRemoteHost();
		int i = checkSystemService.deleteFalses(id, deleteBy, ip);
		return new RestfulResponse<>(i);

	}

	// 根据id查询系统信息
	@RequestMapping(value = "checkSystems", method = RequestMethod.GET)
	@ApiOperation(value = "获取系统数据信息")
	public RestfulResponse<CheckSystemDTO> getCheckSystem(String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		CheckSystemDTO entity = checkSystemService.get(id);
		return new RestfulResponse<>(entity);

	}

	@RequestMapping(value = "checkSystems/lists", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有系统数据信息集合")
	public RestfulResponse<List<CheckSystemDTO>> findCheckSystemList(
			@ModelAttribute CheckSystemDTO entity) {
		List<CheckSystemDTO> list = checkSystemService.findList(entity);
		return new RestfulResponse<List<CheckSystemDTO>>(list);
	}

	// 查询分页 V
	@RequestMapping(value = "checkSystems/pages", method = RequestMethod.GET)
	@ApiOperation(value = "获取系统分页数据信息")
	public RestfulResponse<Page<CheckSystemDTO>> findCheckSystemPage(
			@ModelAttribute CheckSystemDTO entity,
			@ModelAttribute Page<CheckSystemDTO> page) {
		Page<CheckSystemDTO> pageInfo = checkSystemService.findPage(page,
				entity);
		return new RestfulResponse<Page<CheckSystemDTO>>(pageInfo);

	}
}
