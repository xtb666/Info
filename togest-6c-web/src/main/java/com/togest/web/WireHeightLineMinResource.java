package com.togest.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.authority.annotation.UserDataScope;
import com.togest.authority.aspect.TokenUtil;
import com.togest.authority.aspect.UserInfo;
import com.togest.authority.config.PropFieldConstant;
import com.togest.code.client.ExportClient;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.common.util.web.ServletContextHolder;
import com.togest.domain.Page;
import com.togest.domain.WireHeightLineMin;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.WireHeightLineMinService;

import io.swagger.annotations.ApiOperation;


@RestController
public class WireHeightLineMinResource {
    @Autowired
    private WireHeightLineMinService wireHeightLineMinService;
	@Autowired
    private ExportClient exportClient;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode ="c1WireHeightLineMin";
  	

    @RequestMapping(value = "wireHeightLineMin", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<WireHeightLineMin> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
    	WireHeightLineMin entity = wireHeightLineMinService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<WireHeightLineMin>(entity);
    }
    
    @RequestMapping(value = "wireHeightLineMin/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(WireHeightLineMin entity) throws Exception {
        
        return new RestfulResponse<Boolean>(wireHeightLineMinService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "wireHeightLineMin", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(WireHeightLineMin entity) throws Exception {
        wireHeightLineMinService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "wireHeightLineMin/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        WireHeightLineMin entity = wireHeightLineMinService.get(id);
        if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        RestfulResponse<SystemResoucesResponse> response = metadataConfigClient.getSystemResources(resourcesCode);
        if(StringUtil.isEmpty(response)){
        	Shift.fatal(StatusCode.EXCEPTION);
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
    
    @RequestMapping(value = "wireHeightLineMin/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(WireHeightLineMin entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        wireHeightLineMinService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "wireHeightLineMin/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        wireHeightLineMinService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_LINE_MIN", deptFieldName="workAreaId", isToken=true, isDataControl = true)
    @RequestMapping(value = "wireHeightLineMin/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<WireHeightLineMin>> list(WireHeightLineMin entity) throws Exception {
        List<WireHeightLineMin> list = wireHeightLineMinService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<List<WireHeightLineMin>>(list);
    }
    
   	@DataControlPut(authCode="LOOK_WIRE_HEIGHT_LINE_MIN", deptFieldName="workAreaId", isToken=true, isDataControl = true)
    @RequestMapping(value = "wireHeightLineMin/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<WireHeightLineMin>> list(Page<WireHeightLineMin> page,WireHeightLineMin entity) throws Exception {
        Page<WireHeightLineMin> pg = wireHeightLineMinService.findPage(page,entity);
        if(StringUtil.isNotEmpty(pg.getList())){
           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
        }
        return new RestfulResponse<Page<WireHeightLineMin>>(pg);
    }

    @RequestMapping(value = "wireHeightLineMin/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,String createIp) throws Exception{
         Map<String, Object> map = null;
         if(StringUtil.isBlank(templetId)){
     		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
     	}
 		if(fileName == null){
 	      	fileName = file.getOriginalFilename();
 	    }
 	    UserInfo info = TokenUtil.getUser(ServletContextHolder.getRequest());
 	    Map<String, String> propMap = new HashMap<String,String>();
 	    propMap.put(PropFieldConstant.createIp.getStatus(),createIp);
 	    propMap.put(PropFieldConstant.createBy.getStatus(),info.getId());
 	    propMap.put(PropFieldConstant.sectionId.getStatus(),info.getSectionId());
 		map = wireHeightLineMinService.importData(fileName, file.getInputStream(), templetId, null, propMap);
 		return new RestfulResponse<Map<String,Object>>(map);
    }

    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_LINE_MIN", deptFieldName="workAreaId", isToken=true, isDataControl = true)
    @RequestMapping(value = "wireHeightLineMin/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(WireHeightLineMin entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<WireHeightLineMin> list = wireHeightLineMinService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        String errorMsg = "";
		byte[] b = exportClient.exportDataByTemplet(list, templateId, errorMsg, 1);
		
		return FileDownload.fileDownload(b, "导高线路最新导高值.xlsx");
    }
}
