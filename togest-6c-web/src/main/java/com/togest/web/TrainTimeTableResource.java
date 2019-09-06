package com.togest.web;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;

import com.togest.domain.TrainTimeTable;
import com.togest.service.TrainTimeTableService;
import com.togest.service.TrainTimeTableImportService;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.code.client.ExportClient;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.code.util.MetadataUtil;
import com.togest.code.client.MetadataConfigClient;
import com.togest.response.SystemResoucesResponse;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.annotation.UserDataScope;
import com.togest.authority.aspect.TokenUtil;
import com.togest.authority.aspect.UserInfo;
import com.togest.common.util.web.ServletContextHolder;
import com.togest.authority.config.PropFieldConstant;


@RestController
public class TrainTimeTableResource {
    @Autowired
    private TrainTimeTableService trainTimeTableService;
	@Autowired
    private ExportClient exportClient;
    @Autowired
    private TrainTimeTableImportService trainTimeTableImportService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode ="trainTimeTable";
  	

    @RequestMapping(value = "trainTimeTable", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<TrainTimeTable> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        TrainTimeTable entity = trainTimeTableService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<TrainTimeTable>(entity);
    }
    
    @RequestMapping(value = "trainTimeTable/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(TrainTimeTable entity) throws Exception {
        
        return new RestfulResponse<Boolean>(trainTimeTableService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "trainTimeTable", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(TrainTimeTable entity) throws Exception {
        trainTimeTableService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "trainTimeTable/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        TrainTimeTable entity = trainTimeTableService.get(id);
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
    
    @RequestMapping(value = "trainTimeTable/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(TrainTimeTable entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        trainTimeTableService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "trainTimeTable/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        trainTimeTableService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
   	@UserDataScope(authCode="LOOK_TRAINTIMETABLE")
    @RequestMapping(value = "trainTimeTable/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<TrainTimeTable>> list(TrainTimeTable entity) throws Exception {
        List<TrainTimeTable> list = trainTimeTableService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<List<TrainTimeTable>>(list);
    }
    
 	
    @RequestMapping(value = "trainTimeTable/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<TrainTimeTable>> list(Page<TrainTimeTable> page,TrainTimeTable entity) throws Exception {
        Page<TrainTimeTable> pg = trainTimeTableService.findPage(page,entity);
        if(StringUtil.isNotEmpty(pg.getList())){
           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
        }
        return new RestfulResponse<Page<TrainTimeTable>>(pg);
    }

    @RequestMapping(value = "trainTimeTable/imports", method = RequestMethod.POST)
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
		map = trainTimeTableImportService.importData(fileName,file.getInputStream(),templetId,TrainTimeTable.class,propMap);
		return new RestfulResponse<Map<String,Object>>(map);
    }
    
 	
    @RequestMapping(value = "trainTimeTable/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(TrainTimeTable entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.FAIL);
    	}
        List<TrainTimeTable> list = trainTimeTableService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		
		return FileDownload.fileDownload(b, "trainTimeTable.xlsx");
    }

}
