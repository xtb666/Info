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
import com.togest.domain.CheckParamDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.CheckParamService;

//@CrossOrigin(origins="*",allowCredentials="true",allowedHeaders={"x-requested-with,content-type"},methods={RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
//@RequestMapping("/6C")
public class CheckParamResource {
	@Autowired
	private CheckParamService checkParamService;

	// 新增，更新检测参数 V
	@RequestMapping(value = "checkParams", method = RequestMethod.POST)
	@ApiOperation(value = "新增检测参数数据信息")
	public RestfulResponse<Integer> isnertCheckParam(HttpServletRequest request,
			@ModelAttribute CheckParamDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		entity.setCreateIp(request.getRemoteHost());
		entity.setUpdateIp(request.getRemoteHost());
		int i = checkParamService.save(entity);
		return new RestfulResponse<>(i);
	}

	// 新增，更新检测参数 V
	@RequestMapping(value = "checkParams", method = RequestMethod.PUT)
	@ApiOperation(value = "更新检测参数数据信息")
	public RestfulResponse<Integer> updateCheckParam(HttpServletRequest request,
			@ModelAttribute CheckParamDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		entity.setUpdateIp(request.getRemoteHost());
		int i = checkParamService.save(entity);
		return new RestfulResponse<>(i);
	}

	// 假删检测参数 V
	@RequestMapping(value = "checkParams", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除检测参数数据信息")
	public RestfulResponse<Integer> deleteCheckParamFalse(
			HttpServletRequest request, String id, String deleteBy) {
		if (StringUtil.isBlank(id) || StringUtil.isBlank(deleteBy)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		String ip = request.getRemoteHost();
		int i = checkParamService.deleteFalses(id, deleteBy, ip);
		return new RestfulResponse<>(i);

	}

	// 根据id查询检测参数信息
	@RequestMapping(value = "checkParams", method = RequestMethod.GET)
	@ApiOperation(value = "获取检测参数数据信息")
	public RestfulResponse<CheckParamDTO> getCheckParam(String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		CheckParamDTO entity = checkParamService.get(id);
		return new RestfulResponse<>(entity);

	}

	@RequestMapping(value = "checkParams/lists", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有检测参数数据信息集合")
	public RestfulResponse<List<CheckParamDTO>> findCheckParamList(
			@ModelAttribute CheckParamDTO entity) {
		List<CheckParamDTO> list = checkParamService.findList(entity);
		return new RestfulResponse<List<CheckParamDTO>>(list);
	}

	// 查询分页 V
	@RequestMapping(value = "checkParams/pages", method = RequestMethod.GET)
	@ApiOperation(value = "获取检测参数分页数据信息")
	public RestfulResponse<Page<CheckParamDTO>> findCheckParamPage(
			@ModelAttribute CheckParamDTO entity,
			@ModelAttribute Page<CheckParamDTO> page) {
		Page<CheckParamDTO> pageInfo = checkParamService.findPage(page, entity);
		return new RestfulResponse<Page<CheckParamDTO>>(pageInfo);

	}
}
