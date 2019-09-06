package com.togest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.DefectHandleDeadline;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.DefectHandleDeadlineService;

import io.swagger.annotations.ApiOperation;


@RestController
public class DefectHandleDeadlineResource {
    @Autowired
    private DefectHandleDeadlineService defectHandleDeadlineService;
  	
    @DictAggregation
    @RequestMapping(value = "defectHandleDeadline", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<DefectHandleDeadline> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        DefectHandleDeadline entity = defectHandleDeadlineService.get(id);
      
        return new RestfulResponse<DefectHandleDeadline>(entity);
    }
    
    @RequestMapping(value = "defectHandleDeadline/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(DefectHandleDeadline entity) throws Exception {
        
        return new RestfulResponse<Boolean>(defectHandleDeadlineService.unique(entity));
    }
    
	
    @RequestMapping(value = "defectHandleDeadline", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(DefectHandleDeadline entity) throws Exception {
        defectHandleDeadlineService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	
    
    @RequestMapping(value = "defectHandleDeadline/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(DefectHandleDeadline entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        defectHandleDeadlineService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "defectHandleDeadline/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        defectHandleDeadlineService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DictAggregation
    @RequestMapping(value = "defectHandleDeadline/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<DefectHandleDeadline>> list(DefectHandleDeadline entity) throws Exception {
        List<DefectHandleDeadline> list = defectHandleDeadlineService.findList(entity);
        return new RestfulResponse<List<DefectHandleDeadline>>(list);
    }
    
    @DictAggregation
    @RequestMapping(value = "defectHandleDeadline/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<DefectHandleDeadline>> list(Page<DefectHandleDeadline> page,DefectHandleDeadline entity) throws Exception {
        Page<DefectHandleDeadline> pg = defectHandleDeadlineService.findPage(page,entity);
        return new RestfulResponse<Page<DefectHandleDeadline>>(pg);
    }

}
