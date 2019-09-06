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
import com.togest.domain.BatchTechnicalData;
import com.togest.domain.Page;
import com.togest.domain.TechnicalDataDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.TechnicalDataRequest;
import com.togest.service.TechnicalDataService;
import com.togest.utils.PageUtils;

import io.swagger.annotations.ApiOperation;

@RestController
public class TechnicalDataResource {

	@Autowired
	private TechnicalDataService technicalDataService;

	@RequestMapping(value = "technicalData", method = RequestMethod.GET)
	@ApiOperation(value = "获取资料数据信息")
	public RestfulResponse<TechnicalDataDTO> getBasicData(String id) {

		TechnicalDataDTO BasicData = technicalDataService.get(id);
		if (BasicData == null) {
			Shift.fatal(StatusCode.ID_DATA_EMPTY);
		}
		return new RestfulResponse<TechnicalDataDTO>(BasicData);
	}

	@RequestMapping(value = "technicalData/detail", method = RequestMethod.GET)
	@ApiOperation(value = "获取资料数据详细信息")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> getBasicDataForDetail(
			String id, String resourcesCode, String dictName) {

		TechnicalDataDTO BasicData = technicalDataService.get(id);
		if (BasicData == null) {
			Shift.fatal(StatusCode.ID_DATA_EMPTY);
		}
		Map<String, List<Map<String, Object>>> map = technicalDataService
				.editTechnicalDataDataConfig(BasicData, resourcesCode,
						dictName, 0);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);

	}

	/*
	 * @RequestMapping(value = "technicalData/configure", method =
	 * RequestMethod.GET)
	 * 
	 * @ApiOperation(value = "获取资料数据配置信息") public RestfulResponse<Map<String,
	 * List<Map<String, Object>>>> getBasicDataConfigure() { Map<String,
	 * List<Map<String, Object>>> map = technicalDataService
	 * .editTechnicalDataDataConfig(null, "technical_data", "drawing_group");
	 * return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);
	 * }
	 */

	@AddDataSectionPut
	@RequestMapping(value = "technicalData", method = RequestMethod.POST)
	@ApiOperation(value = "新增资料数据信息")
	public RestfulResponse<String> insertBasicData(
			@ModelAttribute TechnicalDataDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		int i = technicalDataService.save(entity);
		return new RestfulResponse<String>(entity.getId());
	}

	@RequestMapping(value = "technicalData/batch", method = RequestMethod.POST)
	@ApiOperation(value = "新增资料数据信息")
	public RestfulResponse<Integer> insertBatchBasicData(
			@ModelAttribute BatchTechnicalData entity) {

		if (entity == null) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		technicalDataService.batchSave(entity);
		return new RestfulResponse<Integer>(1);
	}

	@RequestMapping(value = "technicalData", method = RequestMethod.PUT)
	@ApiOperation(value = "更新资料数据信息")
	public RestfulResponse<Integer> updateBasicData(
			@ModelAttribute TechnicalDataDTO entity) {
		if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		int i = technicalDataService.save(entity);
		return new RestfulResponse<Integer>(i);
	}

	@RequestMapping(value = "technicalData", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除资料数据信息")
	public RestfulResponse<Integer> deleteBasicData(String id, String deleteBy) {

		TechnicalDataDTO BasicData = new TechnicalDataDTO();
		BasicData.setId(id);
		if (deleteBy != null) {
			BasicData.setDeleteBy(deleteBy);
		} else {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		int i = technicalDataService.deleteFalse(BasicData);
		return new RestfulResponse<Integer>(i);
	}
	
	//@DataControlSectionPut(authCode="JCW_DRAW_DATA_DETAIL")
	@ApiOperation(value = "获取资料数据信息集合")
	@RequestMapping(value = "technicalData/lists", method = RequestMethod.GET)
	public RestfulResponse<List<TechnicalDataDTO>> findBasicDataLists(
			TechnicalDataRequest entity) {
		List<TechnicalDataDTO> list = technicalDataService
				.getListByTechnicalDataRequest(entity);
		return new RestfulResponse<List<TechnicalDataDTO>>(list);
	}
	
	@DataControlSectionPut(authCode="JCW_DRAW_DATA_DETAIL")
	@RequestMapping(value = "technicalData/pages", method = RequestMethod.GET)
	@ApiOperation(value = "获取资料数据分页信息")
	public RestfulResponse<Page<TechnicalDataDTO>> findBasicDataPages(
			Page<TechnicalDataDTO> page, TechnicalDataRequest entity) {
		PageUtils.setPage(page);
		Page<TechnicalDataDTO> pg = PageUtils.buildPage(technicalDataService
				.getListByTechnicalDataRequest(entity));

		return new RestfulResponse<Page<TechnicalDataDTO>>(pg);
	}

}
