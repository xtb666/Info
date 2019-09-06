package com.togest.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.DefectTypeDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.DefectTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
public class DefectTypeResource {
	@Autowired
	private DefectTypeService defectTypeService;

	// 新增，更新缺陷类型 V
	@RequestMapping(value = "defectTypes", method = RequestMethod.POST)
	@ApiOperation(value = "新增缺陷类型数据信息")
	public RestfulResponse<Integer> isnertDefectType(
			HttpServletRequest request,@ModelAttribute DefectTypeDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		entity.setCreateIp(request.getRemoteHost());
		entity.setUpdateIp(request.getRemoteHost());
		int i = defectTypeService.save(entity);
		return new RestfulResponse<>(i);
	}

	// 新增，更新缺陷类型 V
	@RequestMapping(value = "defectTypes", method = RequestMethod.PUT)
	@ApiOperation(value = "更新缺陷类型数据信息")
	public RestfulResponse<Integer> updateDefectType(HttpServletRequest request,
			@ModelAttribute DefectTypeDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		entity.setCreateIp(request.getRemoteHost());
		int i = defectTypeService.save(entity);
		return new RestfulResponse<>(i);
	}

	// 假删缺陷类型 V
	@RequestMapping(value = "defectTypes", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除缺陷类型数据信息")
	public RestfulResponse<Integer> deleteDefectTypeFalse(
			HttpServletRequest request, String id, String deleteBy) {
		if (StringUtil.isBlank(id) || StringUtil.isBlank(deleteBy)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectTypeDTO entity = new DefectTypeDTO();
		entity.setId(id);
		entity.setDeleteBy(deleteBy);
		entity.setDeleteIp(request.getRemoteHost());
		int i = defectTypeService.deleteFalse(entity);
		return new RestfulResponse<>(i);

	}

	// 根据id查询缺陷类型信息
	@RequestMapping(value = "defectTypes", method = RequestMethod.GET)
	@ApiOperation(value = "获取缺陷类型数据信息")
	public RestfulResponse<DefectTypeDTO> getDefectType(String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectTypeDTO entity = defectTypeService.get(id);
		return new RestfulResponse<>(entity);

	}

	@RequestMapping(value = "defectTypes/lists", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有缺陷类型数据信息集合")
	public RestfulResponse<List<DefectTypeDTO>> findDefectTypeList(
			@ModelAttribute DefectTypeDTO entity) {
		List<DefectTypeDTO> list = defectTypeService.findList(entity);
		return new RestfulResponse<List<DefectTypeDTO>>(list);
	}
	@RequestMapping(value = "defectTypes/systemCode", method = RequestMethod.GET)
	@ApiOperation(value = "获取系統缺陷类型数据信息集合")
	public RestfulResponse<List<DefectTypeDTO>> findDefectTypeLists(String systemCode) {
		List<DefectTypeDTO> list = defectTypeService.getListBySystemCode(systemCode);
		return new RestfulResponse<List<DefectTypeDTO>>(list);
	}
	@RequestMapping(value = "defectTypes/trees/systemCode", method = RequestMethod.GET)
	@ApiOperation(value = "获取系統缺陷类型树数据信息")
	public RestfulResponse<DefectTypeDTO> getTreeBySystemCode(String systemCode) {
		DefectTypeDTO entity = defectTypeService.getTreeBySystemCode(systemCode);
		return new RestfulResponse<DefectTypeDTO>(entity);
	}
	
	@RequestMapping(value = "defectTypes/trees", method = RequestMethod.GET)
	@ApiOperation(value = "获取缺陷类型树数据信息")
	public RestfulResponse<DefectTypeDTO> findDefectTypePage(HttpServletRequest request,String id) {
		
		if(StringUtil.isBlank(id)){
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectTypeDTO entity = defectTypeService.findChildsByParent(id);
		return new RestfulResponse<DefectTypeDTO>(entity);

	}
}
