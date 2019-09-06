package com.togest.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.authority.annotation.DataControlSectionPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.dto.RulesRegulationDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.RulesRegulationRequest;
import com.togest.service.RulesRegulationService;
import com.togest.utils.PageUtils;

import io.swagger.annotations.ApiOperation;

@RestController
public class RulesRegulationResource {

	@Autowired
	private RulesRegulationService rulesRegulationService;

	@RequestMapping(value = "rulesRegulation", method = RequestMethod.GET)
	@ApiOperation(value = "获取规章制度信息")
	public RestfulResponse<RulesRegulationDTO> getBasicData(String id) {

		RulesRegulationDTO rulesRegulation = rulesRegulationService.get(id);
		if (rulesRegulation == null) {
			Shift.fatal(StatusCode.ID_DATA_EMPTY);
		}
		return new RestfulResponse<RulesRegulationDTO>(rulesRegulation);
	}

	@RequestMapping(value = "rulesRegulation/detail", method = RequestMethod.GET)
	@ApiOperation(value = "获取规章制度详细信息")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> getBasicDataForDetail(
			String id, String resourcesCode, String dictName) {

		RulesRegulationDTO BasicData = rulesRegulationService.get(id);
		if (BasicData == null) {
			Shift.fatal(StatusCode.ID_DATA_EMPTY);
		}
		Map<String, List<Map<String, Object>>> map = rulesRegulationService
				.editRulesRegulationDataConfig(BasicData, resourcesCode,
						dictName, 0);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);

	}

	@AddDataSectionPut
	@RequestMapping(value = "rulesRegulation", method = RequestMethod.POST)
	@ApiOperation(value = "新增规章制度信息")
	public RestfulResponse<String> insertBasicData(
			@ModelAttribute RulesRegulationDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		int i = rulesRegulationService.save(entity);
		return new RestfulResponse<String>(entity.getId());
	}

	@RequestMapping(value = "rulesRegulation", method = RequestMethod.PUT)
	@ApiOperation(value = "更新规章制度信息")
	public RestfulResponse<Integer> updateBasicData(
			@ModelAttribute RulesRegulationDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		int i = rulesRegulationService.save(entity);
		return new RestfulResponse<Integer>(i);
	}

	@RequestMapping(value = "rulesRegulation", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除规章制度信息")
	public RestfulResponse<Integer> deleteBasicData(String id, String deleteBy) {

		RulesRegulationDTO BasicData = new RulesRegulationDTO();
		BasicData.setId(id);
		if (deleteBy != null) {
			BasicData.setDeleteBy(deleteBy);
		} else {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		int i = rulesRegulationService.deleteFalse(BasicData);
		return new RestfulResponse<Integer>(i);
	}
	
	@ApiOperation(value = "获取规章制度信息集合")
	@RequestMapping(value = "rulesRegulation/lists", method = RequestMethod.GET)
	public RestfulResponse<List<RulesRegulationDTO>> findBasicDataLists(
			RulesRegulationRequest entity) {
		List<RulesRegulationDTO> list = rulesRegulationService
				.getListByRulesRegulationRequest(entity);
		return new RestfulResponse<List<RulesRegulationDTO>>(list);
	}
	
	@DataControlSectionPut(authCode="LOOK_CONFIG_MANAGE_RULES_REGULAR_NC")
	@RequestMapping(value = "rulesRegulation/pages", method = RequestMethod.GET)
	@ApiOperation(value = "获取规章制度分页信息")
	public RestfulResponse<Page<RulesRegulationDTO>> findBasicDataPages(
			Page<RulesRegulationDTO> page, RulesRegulationRequest entity) {
		PageUtils.setPage(page);
		List<RulesRegulationDTO> list = rulesRegulationService.getListByRulesRegulationRequest(entity);
		Page<RulesRegulationDTO> pg = PageUtils.buildPage(list);

		return new RestfulResponse<Page<RulesRegulationDTO>>(pg);
	}

}
