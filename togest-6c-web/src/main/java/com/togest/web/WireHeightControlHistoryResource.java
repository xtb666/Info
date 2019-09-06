package com.togest.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.code.client.ExportClient;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.WireHeightControlHistory;
import com.togest.domain.WireHeightControlHistoryDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.WireHeightControlHistoryService;

import io.swagger.annotations.ApiOperation;


@RestController
public class WireHeightControlHistoryResource {
    @Autowired
    private WireHeightControlHistoryService wireHeightControlHistoryService;
    @Autowired
	private MetadataUtil metadataUtil;
    @Autowired
    private ExportClient exportClient;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode ="c1WireHeightControlHistory";
  	

    @RequestMapping(value = "wireHeightControlHistory", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<WireHeightControlHistory> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        WireHeightControlHistoryDTO entity = wireHeightControlHistoryService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<WireHeightControlHistory>(entity);
    }
    
    @RequestMapping(value = "wireHeightControlHistory/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(WireHeightControlHistoryDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(wireHeightControlHistoryService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "wireHeightControlHistory", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(WireHeightControlHistoryDTO entity) throws Exception {
        wireHeightControlHistoryService.save(entity, 1);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "wireHeightControlHistory/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	WireHeightControlHistoryDTO entity = wireHeightControlHistoryService.get(id);
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
    
    @RequestMapping(value = "wireHeightControlHistory/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(WireHeightControlHistoryDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        wireHeightControlHistoryService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "wireHeightControlHistory/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        wireHeightControlHistoryService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_STAND_HISTORY", isToken = true, isDataControl = true, deptFieldName="workAreaId")
    @RequestMapping(value = "wireHeightControlHistory/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<WireHeightControlHistoryDTO>> list(WireHeightControlHistoryDTO entity) throws Exception {
        List<WireHeightControlHistoryDTO> list = wireHeightControlHistoryService.findList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<List<WireHeightControlHistoryDTO>>(list);
    }
    
    @DataControlPut(authCode="LOOK_WIRE_HEIGHT_STAND_HISTORY", isToken = true, isDataControl = true, deptFieldName="workAreaId")
    @RequestMapping(value = "wireHeightControlHistory/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<WireHeightControlHistoryDTO>> list(Page<WireHeightControlHistoryDTO> page,WireHeightControlHistoryDTO entity) throws Exception {
        Page<WireHeightControlHistoryDTO> pg = wireHeightControlHistoryService.findPage(page,entity);
        if(StringUtil.isNotEmpty(pg.getList())){
           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
        }
        return new RestfulResponse<Page<WireHeightControlHistoryDTO>>(pg);
    }
}
