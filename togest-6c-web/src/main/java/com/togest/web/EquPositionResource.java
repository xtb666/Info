package com.togest.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.aspect.TokenUtil;
import com.togest.authority.aspect.UserInfo;
import com.togest.authority.config.PropFieldConstant;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.common.util.web.ServletContextHolder;
import com.togest.domain.EquPosition;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.EquPositionImportService;
import com.togest.service.EquPositionService;

import io.swagger.annotations.ApiOperation;


@RestController
public class EquPositionResource {
    @Autowired
    private EquPositionService equPositionService;
    @Autowired
    private EquPositionImportService equPositionImportService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode ="equPosition";
  	

    @RequestMapping(value = "equPosition", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<EquPosition> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        EquPosition entity = equPositionService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<EquPosition>(entity);
    }
    
    @RequestMapping(value = "equPosition/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(EquPosition entity) throws Exception {
        
        return new RestfulResponse<Boolean>(equPositionService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "equPosition", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(EquPosition entity) throws Exception {
        equPositionService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "equPosition/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        EquPosition entity = equPositionService.get(id);
        if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        RestfulResponse<SystemResoucesResponse> response = metadataConfigClient.getSystemResources(resourcesCode);
        if(StringUtil.isEmpty(response)){
        	Shift.fatal(StatusCode.FAIL);
        }
        SystemResoucesResponse systemResoucesResponse = response.getData();
        if(StringUtil.isEmpty(response)){
        	Shift.fatal(StatusCode.PARAM_DATA_EMPTY);
        }
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), systemResoucesResponse.getProps());
		Map<String, Object> map = (Map<String, Object>) ObjectUtils.objectToMap(entity);
        Map<String, List<Map<String, Object>>> maps = metadataUtil.enclosure(map, systemResoucesResponse.getProps());
        return new RestfulResponse<Map<String, List<Map<String, Object>>>>(maps);
    }
    
    @RequestMapping(value = "equPosition/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(EquPosition entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        equPositionService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "equPosition/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        equPositionService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
  
    @RequestMapping(value = "equPosition/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<EquPosition>> list(EquPosition entity) throws Exception {
        List<EquPosition> list = equPositionService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<List<EquPosition>>(list);
    }
    

    @RequestMapping(value = "equPosition/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<EquPosition>> list(Page<EquPosition> page,EquPosition entity) throws Exception {
        Page<EquPosition> pg = equPositionService.findPage(page,entity);
        if(StringUtil.isNotEmpty(pg.getList())){
           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
        }
        return new RestfulResponse<Page<EquPosition>>(pg);
    }

    @RequestMapping(value = "equPosition/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,String createIp) throws Exception{
         Map<String, Object> map = null;
        if(StringUtil.isBlank(templetId)){
    		Shift.fatal(StatusCode.FAIL);
    	}
		if(fileName == null){
	      	fileName = file.getOriginalFilename();
	     }
	    UserInfo info = TokenUtil.getUser(ServletContextHolder.getRequest());
	    Map<String, Object> propMap = new HashMap<String,Object>();
	    propMap.put(PropFieldConstant.createIp.getStatus(),createIp);
	    propMap.put(PropFieldConstant.createBy.getStatus(),info.getId());
	    propMap.put(PropFieldConstant.sectionId.getStatus(),info.getSectionId());
		map = equPositionImportService.importData(fileName,file.getInputStream(),templetId,EquPosition.class,propMap);
		return new RestfulResponse<Map<String,Object>>(map);
    }


    

}
