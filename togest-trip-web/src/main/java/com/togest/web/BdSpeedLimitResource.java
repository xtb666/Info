package com.togest.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataPut;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.dto.BdSpeedLimitDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdSpeedLimitService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdSpeedLimitResource {
    @Autowired
    private BdSpeedLimitService bdSpeedLimitService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode =ResourcesCodeUtil.BDSPEEDLIMIT_RESOURCESCODE;
  	

    @RequestMapping(value = "bdSpeedLimit", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdSpeedLimitDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        BdSpeedLimitDTO entity = bdSpeedLimitService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdSpeedLimitDTO>(entity);
    }
    
    @RequestMapping(value = "bdSpeedLimit/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdSpeedLimitDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(bdSpeedLimitService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdSpeedLimit", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdSpeedLimitDTO entity) throws Exception {
		check(entity);
        bdSpeedLimitService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdSpeedLimit/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdSpeedLimitDTO entity = bdSpeedLimitService.get(id, 1);
        if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        RestfulResponse<SystemResoucesResponse> response = metadataConfigClient.getSystemResources(resourcesCode);
        if(StringUtil.isEmpty(response)){
        	Shift.fatal(StatusCode.EXCEPTION);
        }
        SystemResoucesResponse systemResoucesResponse = response.getData();
        if(StringUtil.isEmpty(systemResoucesResponse)){
        	Shift.fatal(StatusCode.PARAM_DATA_EMPTY);
        }
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), systemResoucesResponse.getProps());
		Map<String, Object> map = (Map<String, Object>) ObjectUtils.objectToMap(entity);
        Map<String, List<Map<String, Object>>> maps = metadataUtil.enclosure(map, systemResoucesResponse.getProps());
        return new RestfulResponse<Map<String, List<Map<String, Object>>>>(maps);
    }
    
    @RequestMapping(value = "bdSpeedLimit/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdSpeedLimitDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdSpeedLimitService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdSpeedLimit/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdSpeedLimitService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @RequestMapping(value = "bdSpeedLimit/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdSpeedLimitDTO>> list(BdInfoQueryFilter entity) throws Exception {
    	Page<BdSpeedLimitDTO> pg = new Page<BdSpeedLimitDTO>();
    	List<BdSpeedLimitDTO> list = bdSpeedLimitService.findSpeedLimitList(entity);
        if(StringUtil.isNotEmpty(list)){
        	pg.setList(list);
        	pg.setTotal((long)list.size());
//          polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdSpeedLimitDTO>>(pg);
    }
    
    @RequestMapping(value = "bdSpeedLimit/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdSpeedLimitDTO>> list(Page<BdSpeedLimitDTO> page, BdInfoQueryFilter entity) throws Exception {
        Page<BdSpeedLimitDTO> pg = bdSpeedLimitService.findSpeedLimitPage(page,entity);
//        if(StringUtil.isNotEmpty(pg.getList())){
//           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
//        }
        return new RestfulResponse<Page<BdSpeedLimitDTO>>(pg);
    }
 	
 	//校验名称是否重复
   	private void check(BdSpeedLimitDTO entity) {
		if(null == entity.getName()) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		}
		BdSpeedLimitDTO bdSpeedLimitDTO = new BdSpeedLimitDTO();
		bdSpeedLimitDTO.setId(entity.getId());
		bdSpeedLimitDTO.setName(entity.getName());
		boolean unique = bdSpeedLimitService.unique(bdSpeedLimitDTO);
		if(!unique) {
			Shift.fatal(StatusCode.NAME_REPEAT);
		}
	}
}
