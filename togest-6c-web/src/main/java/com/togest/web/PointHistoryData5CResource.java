package com.togest.web;

import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.domain.Page;
import com.togest.domain.PointHisotryData5CDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.PointHisotryData5CService;

@RequestMapping("5C")
@RestController
public class PointHistoryData5CResource {

	@Autowired
	private PointHisotryData5CService service;

	@RequestMapping(value = "pointHistoryData", method = RequestMethod.GET)
	@ApiOperation(value = "获取监测数据")
	public RestfulResponse<PointHisotryData5CDTO> getPointHistoryData(HttpServletRequest request,
			String id) {
		PointHisotryData5CDTO entity = service.get(id);
		return new RestfulResponse<PointHisotryData5CDTO>(entity);
	}
	
	@RequestMapping(value = "pointHistoryData", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除监测数据")
	public RestfulResponse<Integer> deletePointHistoryData(HttpServletRequest request,
			String id) {
		int i = service.deletes(id);
		return new RestfulResponse<Integer>(i);
	}
	
	@RequestMapping(value = "pointHistoryData", method = RequestMethod.POST)
	@ApiOperation(value = "监测保存")
	public RestfulResponse<Boolean> savePointHistoryData(HttpServletRequest request,@RequestBody PointHisotryData5CDTO entity) {

		service.save(entity);
		return new RestfulResponse<Boolean>(true);
	}
	
	@RequestMapping(value = "pointHistoryData/lists", method = RequestMethod.GET)
	@ApiOperation(value = "获取监测数据")
	public RestfulResponse<List<PointHisotryData5CDTO>> findPointHistoryDataLists(HttpServletRequest request,
			@ModelAttribute PointHisotryData5CDTO entity) {
		List<PointHisotryData5CDTO> list = service.findList(entity);
		return new RestfulResponse<List<PointHisotryData5CDTO>>(list);
	}
	
	@RequestMapping(value = "pointHistoryData/pages", method = RequestMethod.GET)
	@ApiOperation(value = "监测分页数据")
	public RestfulResponse<Page<PointHisotryData5CDTO>> findPointHistoryDataPages(
			HttpServletRequest request,@ModelAttribute  Page page, @ModelAttribute PointHisotryData5CDTO entity) {
		// DataRightUtil.look(request, code, entity);
		Page<PointHisotryData5CDTO> pg = service.findList(page, entity);
		return new RestfulResponse<Page<PointHisotryData5CDTO>>(pg);
	}
	
	@RequestMapping(value = "pointHistoryData/imports", method = RequestMethod.POST)
	@ApiOperation(value = "导入监测数据")
	public RestfulResponse<Boolean> importDefectData(HttpServletRequest request,MultipartFile file,String createBy){
		try {
			service.analyzeZipData(file.getOriginalFilename(), file.getInputStream(), createBy);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return new RestfulResponse<Boolean>(true);
	}
	

}
