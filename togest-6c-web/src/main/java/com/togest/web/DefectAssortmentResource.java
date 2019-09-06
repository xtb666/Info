package com.togest.web;

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

import com.togest.authority.aspect.TokenUtil;
import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.DefectAssortmentDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.DefectAssortmentService;

import io.swagger.annotations.ApiOperation;

@RestController
public class DefectAssortmentResource {

	@Autowired
	private DefectAssortmentService defectAssortmentService;
	
	@RequestMapping(value="defectAssortments",method =RequestMethod.GET)
	@ApiOperation(value = "获取缺陷分类数据")
	public RestfulResponse<DefectAssortmentDTO> get(String id){
		
		if(StringUtil.isBlank(id)){
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectAssortmentDTO entity = defectAssortmentService.get(id);
		
		return new RestfulResponse<DefectAssortmentDTO>(entity);
	}
	@RequestMapping(value="defectAssortments",method =RequestMethod.POST)
	@ApiOperation(value = "保存缺陷分类数据")
	public RestfulResponse<Integer> save(DefectAssortmentDTO entity){
		
		int i = defectAssortmentService.save(entity);
		
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value="defectAssortments",method =RequestMethod.DELETE)
	@ApiOperation(value = "删除缺陷分类数据")
	public RestfulResponse<Integer> deleteFalses(String id){
		
		int i = defectAssortmentService.deleteFalses(id,null,null);
		
		return new RestfulResponse<Integer>(i);
	}
	@DictAggregation
	@RequestMapping(value="defectAssortments/lists",method =RequestMethod.GET)
	@ApiOperation(value = "获取缺陷分类集合数据")
	public RestfulResponse<List<DefectAssortmentDTO>> findLists(DefectAssortmentDTO entity){
		
		List<DefectAssortmentDTO> list = defectAssortmentService.findList(entity);
		
		return new RestfulResponse<List<DefectAssortmentDTO>>(list);
	}
	@DictAggregation
	@RequestMapping(value="defectAssortments/pages",method =RequestMethod.GET)
	@ApiOperation(value = "获取缺陷分类分页数据")
	public RestfulResponse<Page<DefectAssortmentDTO>> findPages(Page<DefectAssortmentDTO> page,DefectAssortmentDTO entity){
		
		Page<DefectAssortmentDTO> pg = defectAssortmentService.findPage(page, entity);
		
		return new RestfulResponse<Page<DefectAssortmentDTO>>(pg);
	}
	
	@RequestMapping(value = "defectAssortments/templet/data", method = RequestMethod.POST)
	@ApiOperation(value = "导入缺陷分类数据")
	public RestfulResponse<Map<String, Object>> importPlanData(
			HttpServletRequest request, MultipartFile file,String systemId, String templetId,
			String fileName,String createBy) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file != null ) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = file.getOriginalFilename();
				}
				map = defectAssortmentService.importData(fileName, file
						.getInputStream(),systemId, templetId, createBy,
						TokenUtil.getUser(request).getSectionId()); 
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}
}
