package com.togest.web;

import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.BatchDrawingData;
import com.togest.domain.DataDrawingDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.DataDrawingRequest;
import com.togest.service.DataDrawingService;
import com.togest.utils.PageUtils;

@RestController
public class DataDrawingResource {
	@Autowired
	private DataDrawingService dataDrawingService;

	@RequestMapping(value = "dataDrawing", method = RequestMethod.GET)
	@ApiOperation(value = "获取图纸数据信息")
	public RestfulResponse<DataDrawingDTO> getDataDrawing(String id)
			throws Exception {
		DataDrawingDTO DataDrawing = dataDrawingService.get(id);
		if (DataDrawing == null) {
			Shift.fatal(StatusCode.ID_DATA_EMPTY);
		}
		return new RestfulResponse<DataDrawingDTO>(DataDrawing);
	}

	@RequestMapping(value = "dataDrawing/detail", method = RequestMethod.GET)
	@ApiOperation(value = "获取图纸数据详细信息")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> getDataDrawingForDetail(
			String id, String resourcesCode, String dictName) {
		DataDrawingDTO DataDrawing = dataDrawingService.get(id);
		if (DataDrawing == null) {
			Shift.fatal(StatusCode.ID_DATA_EMPTY);
		}
		Map<String, List<Map<String, Object>>> map = dataDrawingService
				.editDataDrawingDataConfig(DataDrawing, resourcesCode,
						dictName, 0);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);

	}

	/*
	 * @RequestMapping(value = "dataDrawing/configure", method =
	 * RequestMethod.GET)
	 * 
	 * @ApiOperation(value = "获取图纸数据信息配置") public RestfulResponse<Map<String,
	 * List<Map<String, Object>>>> getDataDrawingConfigure() { Map<String,
	 * List<Map<String, Object>>> map = dataDrawingService
	 * .editDataDrawingDataConfig(null, "drawing_data", "drawing_group"); return
	 * new RestfulResponse<Map<String, List<Map<String, Object>>>>(map); }
	 */

	@AddDataPut
	@RequestMapping(value = "dataDrawing", method = RequestMethod.POST)
	@ApiOperation(value = "新增图纸数据信息")
	public RestfulResponse<String> insertDataDrawing(
			@ModelAttribute DataDrawingDTO entity) {
		/*if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else*/ if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		dataDrawingService.save(entity);
		return new RestfulResponse<String>(entity.getId());
	}

	@RequestMapping(value = "dataDrawing/batch", method = RequestMethod.POST)
	@ApiOperation(value = "新增图纸数据信息")
	public RestfulResponse<Integer> insertBatchDataDrawing(
			@ModelAttribute BatchDrawingData entity) {

		if (entity == null) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		dataDrawingService.batchSave(entity);
		return new RestfulResponse<Integer>(1);
	}

	@RequestMapping(value = "dataDrawing", method = RequestMethod.PUT)
	@ApiOperation(value = "更新图纸数据信息")
	public RestfulResponse<Integer> updateDataDrawing(
			@ModelAttribute DataDrawingDTO entity) {
		/*if (entity != null && StringUtil.isBlank(entity.getName())) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		} else */if (entity != null && StringUtil.isBlank(entity.getCode())) {
			Shift.fatal(StatusCode.CODE_EMPTY);
		}
		int rs = dataDrawingService.save(entity);
		return new RestfulResponse<Integer>(rs);
	}

	@RequestMapping(value = "dataDrawing", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除图纸数据信息")
	public RestfulResponse<Integer> deleteDataDrawing(String id, String deleteBy) {

		DataDrawingDTO DataDrawing = new DataDrawingDTO();
		DataDrawing.setId(id);
		DataDrawing.setDeleteBy(deleteBy);
		int rs = dataDrawingService.deleteFalse(DataDrawing);
		return new RestfulResponse<Integer>(rs);
	}
	
	@DataControlPut(isDataControl=true)
	@RequestMapping(value = "dataDrawing/lists", method = RequestMethod.GET)
	@ApiOperation(value = "获取图纸数据信息集合")
	public RestfulResponse<List<DataDrawingDTO>> findDataDrawingLists(
			DataDrawingRequest entity) {
		List<DataDrawingDTO> list = dataDrawingService
				.getListByDataDrawingRequest(entity);
		return new RestfulResponse<List<DataDrawingDTO>>(list);
	}
	
	@DataControlPut(isDataControl=true)
	@RequestMapping(value = "dataDrawing/pages", method = RequestMethod.GET)
	@ApiOperation(value = "获取图纸分页数据信息")
	public RestfulResponse<Page<DataDrawingDTO>> findDataDrawingPages(
			Page<DataDrawingDTO> page, DataDrawingRequest entity) {

		PageUtils.setPage(page);
		Page<DataDrawingDTO> pg = PageUtils.buildPage(dataDrawingService
				.getListByDataDrawingRequest(entity));

		return new RestfulResponse<Page<DataDrawingDTO>>(pg);
	}

}
