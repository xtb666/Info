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
import org.springframework.web.bind.annotation.RequestParam;
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
import com.togest.domain.PillarInfoDTO;
import com.togest.domain.PillarInfoSelectorV2DTO;
import com.togest.domain.WireHeightDataDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.WireHeightDataService;

import io.swagger.annotations.ApiOperation;


@RestController
public class WireHeightDataResource {
    @Autowired
    private WireHeightDataService wireHeightDataService;
	@Autowired
    private ExportClient exportClient;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode ="c1WireHeight";
  	

    @RequestMapping(value = "wireHeight", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<WireHeightDataDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        WireHeightDataDTO entity = wireHeightDataService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<WireHeightDataDTO>(entity);
    }
    
    @RequestMapping(value = "wireHeight/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(WireHeightDataDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(wireHeightDataService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "wireHeight", method = RequestMethod.POST)//
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(WireHeightDataDTO entity) throws Exception {
		wireHeightDataService.save(entity, 1);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "wireHeight/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	WireHeightDataDTO entity = wireHeightDataService.get(id);
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

    @RequestMapping(value = "wireHeight/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(WireHeightDataDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	wireHeightDataService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "wireHeight/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	wireHeightDataService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_DATA", deptFieldName="workAreaId", isToken=true)
    @RequestMapping(value = "wireHeight/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<WireHeightDataDTO>> list(WireHeightDataDTO entity) throws Exception {
        List<WireHeightDataDTO> list = wireHeightDataService.findList(entity);
        return new RestfulResponse<List<WireHeightDataDTO>>(list);
    }
    
 	@DataControlPut(authCode="LOOK_WIRE_HEIGHT_DATA", deptFieldName="workAreaId", isToken=true, isDataControl = true)
    @RequestMapping(value = "wireHeight/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<WireHeightDataDTO>> list(Page<WireHeightDataDTO> page,WireHeightDataDTO entity) throws Exception {
        Page<WireHeightDataDTO> pg = wireHeightDataService.findPage(page,entity);
        return new RestfulResponse<Page<WireHeightDataDTO>>(pg);
    }
 	
 	@DataControlPut(authCode="LOOK_WIRE_HEIGHT_DATA", deptFieldName="workAreaId", isToken=true, isDataControl = true)
    @RequestMapping(value = "wireHeight/analyseData", method = RequestMethod.GET)
    @ApiOperation(value = "同一天数据分析")
    public RestfulResponse<Map<String,Object>> findDataAnaliayByDateList(WireHeightDataDTO entity) throws Exception {
        Map<String, Object> map = wireHeightDataService.findDataAnaliayByDateList(entity);
        return new RestfulResponse<Map<String,Object>>(map);
    }
    
    @RequestMapping(value = "wireHeight/selector", method = RequestMethod.GET)
    @ApiOperation(value = "支柱选择器")
    public RestfulResponse<List<PillarInfoSelectorV2DTO>> pillarSelector(PillarInfoDTO pillarInfoDTO) throws Exception {
        List<PillarInfoSelectorV2DTO> pillarSelectors= wireHeightDataService.pillarSelector(pillarInfoDTO);
        return new RestfulResponse<List<PillarInfoSelectorV2DTO>>(pillarSelectors);
    }
    
    @RequestMapping(value = "wireHeight/history", method = RequestMethod.GET)
    @ApiOperation(value = "单个杆号历史数据")
    public RestfulResponse<Map<String,Object>> history(WireHeightDataDTO wireHeightDTO) throws Exception {
        Map<String, Object> map = wireHeightDataService.history(wireHeightDTO);
        return new RestfulResponse<Map<String,Object>>(map);
    }
    
    @RequestMapping(value = "wireHeight/findUpMonthAnchorPointByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "根据杆号获取上次导高值")
    public RestfulResponse<WireHeightDataDTO> findUpMonthAnchorPointByCondition(WireHeightDataDTO wireHeightDTO) throws Exception {
    	WireHeightDataDTO dto = wireHeightDataService.findUpWireHeightData(wireHeightDTO);
        return new RestfulResponse<WireHeightDataDTO>(dto);
    }

	@RequestMapping(value = "wireHeight/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,
    			String createIp, String systemId, @RequestParam(value="dymType",defaultValue="1") String dymType) throws Exception{
		 Map<String, Object> map = null;
        if(StringUtil.isBlank(templetId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
		if(fileName == null){
	      	fileName = file.getOriginalFilename();
	    }
		if(StringUtil.isNotBlank(systemId)) {
			systemId = "C1";
		}
	    UserInfo info = TokenUtil.getUser(ServletContextHolder.getRequest());
	    Map<String, String> propMap = new HashMap<String,String>();
	    propMap.put(PropFieldConstant.createIp.getStatus(),createIp);
	    propMap.put(PropFieldConstant.createBy.getStatus(),info.getId());
	    propMap.put(PropFieldConstant.sectionId.getStatus(),info.getSectionId());
		map = wireHeightDataService.importData(fileName, file.getInputStream(), templetId, null, propMap, systemId, dymType);
		return new RestfulResponse<Map<String,Object>>(map);
    }

    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_DATA", deptFieldName="workAreaId", isToken=true, isDataControl = true)
    @RequestMapping(value = "wireHeight/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(WireHeightDataDTO entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<WireHeightDataDTO> list = wireHeightDataService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            String errorMsg = "";
    		byte[] b = exportClient.exportDataByTemplet(list, templateId, errorMsg , 1);
    		return FileDownload.fileDownload(b, "导高数据.xlsx");
        }else {
        	 String errorMsg = "";
     		byte[] b = exportClient.exportDataByTemplet(list, "a17e862596ce4efea416b63bfa9cc215", errorMsg , 1);
     		return FileDownload.fileDownload(b, "导高数据.xlsx");
        }
    }
}
