package com.togest.web;

import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.CheckTrainDTO;
import com.togest.domain.Page;
import com.togest.model.request.FileUploadDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.CheckTrainService;
import com.togest.util.TokenUtil;

@RestController
public class CheckTrainResource {

	@Autowired
	private CheckTrainService checkTrainService;

	@AddDataSectionPut
	@RequestMapping(value = "checkTrain", method = RequestMethod.POST)
	@ApiOperation(value = "更新机车数据信息")
	public RestfulResponse<Boolean> updateCheckTrain(
			HttpServletRequest request,  CheckTrainDTO entity) {
		checkTrainService.save(entity);
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "checkTrain", method = RequestMethod.GET)
	@ApiOperation(value = "获取机车数据信息")
	public RestfulResponse<CheckTrainDTO> getPlanDTO(
			HttpServletRequest request, String id) {
		CheckTrainDTO CheckTrainDTO = checkTrainService.get(id);
		return new RestfulResponse<CheckTrainDTO>(CheckTrainDTO);
	}

	@RequestMapping(value = "checkTrain", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除机车数据信息")
	public RestfulResponse<Integer> deleteCheckTrain(
			HttpServletRequest request, String id, String deleteBy) {
		int i = checkTrainService.deleteFalses(id, deleteBy,
				request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}

	@RequestMapping(value = "checkTrain/pages", method = RequestMethod.GET)
	@ApiOperation(value = "机车数据分页数据")
	public RestfulResponse<Page<CheckTrainDTO>> findCheckTrainPages(
			HttpServletRequest request, Page page, CheckTrainDTO entity) {
		
		Page<CheckTrainDTO> pg = checkTrainService.findPages(page, entity);
		return new RestfulResponse<Page<CheckTrainDTO>>(pg);
	}

	@RequestMapping(value = "checkTrain/lists", method = RequestMethod.GET)
	@ApiOperation(value = "机车数据集合数据")
	public RestfulResponse<List<CheckTrainDTO>> findCheckTrainLists(
			HttpServletRequest request, Page page, CheckTrainDTO entity) {
		
		List<CheckTrainDTO> pg = checkTrainService.findList(entity);
		return new RestfulResponse<List<CheckTrainDTO>>(pg);
	}
	
	@RequestMapping(value = "checkTrain/templet/data", method = RequestMethod.POST)
	@ApiOperation(value = "导入机车数据")
	public RestfulResponse<Map<String, Object>> importDefect(
			HttpServletRequest request, MultipartFile file, String templetId,
			String fileName,String createBy) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file != null ) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = file.getOriginalFilename();
				}
				map = checkTrainService.importData(fileName, file
						.getInputStream(), templetId, createBy,
						TokenUtil.getUser(request).getSectionId());
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}
}
