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
import com.togest.domain.WireHeightControl;
import com.togest.domain.WireHeightControlDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.WireHeightControlService;

import io.swagger.annotations.ApiOperation;


@RestController
public class WireHeightControlResource {
    @Autowired
    private WireHeightControlService wireHeightControlService;
    @Autowired
	private MetadataUtil metadataUtil;
    @Autowired
    private ExportClient exportClient;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode ="c1WireHeightControl";
  	

    @RequestMapping(value = "wireHeightControl", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<WireHeightControl> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        WireHeightControlDTO entity = wireHeightControlService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<WireHeightControl>(entity);
    }
    
    @RequestMapping(value = "wireHeightControl/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(WireHeightControlDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(wireHeightControlService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "wireHeightControl", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(WireHeightControlDTO entity) throws Exception {
        wireHeightControlService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "wireHeightControl/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	WireHeightControlDTO entity = wireHeightControlService.get(id);
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
    
    @RequestMapping(value = "wireHeightControl/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(WireHeightControlDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        wireHeightControlService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "wireHeightControl/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        wireHeightControlService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_STAND", isToken = true, isDataControl = true, deptFieldName="workAreaId")
    @RequestMapping(value = "wireHeightControl/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<WireHeightControlDTO>> list(WireHeightControlDTO entity) throws Exception {
        List<WireHeightControlDTO> list = wireHeightControlService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<List<WireHeightControlDTO>>(list);
    }
    
    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_STAND", isToken = true, isDataControl = true, deptFieldName="workAreaId")
    @RequestMapping(value = "wireHeightControl/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<WireHeightControlDTO>> list(Page<WireHeightControlDTO> page,WireHeightControlDTO entity) throws Exception {
        Page<WireHeightControlDTO> pg = wireHeightControlService.findPage(page,entity);
        if(StringUtil.isNotEmpty(pg.getList())){
           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
        }
        return new RestfulResponse<Page<WireHeightControlDTO>>(pg);
    }
    
    @RequestMapping(value = "wireHeightControl/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,String createIp, String systemId) throws Exception{
         Map<String, Object> map = null;
	    UserInfo info = TokenUtil.getUser(ServletContextHolder.getRequest());
	    Map<String, Object> propMap = new HashMap<String,Object>();
	    propMap.put(PropFieldConstant.createIp.getStatus(),createIp);
	    propMap.put(PropFieldConstant.createBy.getStatus(),info.getId());
	    propMap.put(PropFieldConstant.sectionId.getStatus(),info.getSectionId());
		map = wireHeightControlService.importData(fileName, file.getInputStream(), templetId, null, propMap);
		return new RestfulResponse<Map<String,Object>>(map);
    }

    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_STAND", isToken = true, isDataControl = true, deptFieldName="workAreaId")
    @RequestMapping(value = "wireHeightControl/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(WireHeightControlDTO entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<WireHeightControlDTO> list = wireHeightControlService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        String errorMsg = "";
		byte[] b = exportClient.exportDataByTemplet(list, templateId, errorMsg, 1);
		return FileDownload.fileDownload(b, "导高标准值.xlsx");
    }
}
