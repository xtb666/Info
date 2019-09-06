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
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.dto.BdEmerRepairDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdEmerRepairService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdEmerRepairResource {
    @Autowired
    private BdEmerRepairService bdEmerRepairService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode =ResourcesCodeUtil.BDEMERREPAIR_RESOURCESCODE;
  	

    @RequestMapping(value = "bdEmerRepair", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdEmerRepairDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        BdEmerRepairDTO entity = bdEmerRepairService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdEmerRepairDTO>(entity);
    }
    
    @RequestMapping(value = "bdEmerRepair/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdEmerRepairDTO entity) throws Exception {
        
        return new RestfulResponse<Boolean>(bdEmerRepairService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdEmerRepair", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdEmerRepairDTO entity) throws Exception {
		check(entity);
        bdEmerRepairService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdEmerRepair/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdEmerRepairDTO entity = bdEmerRepairService.get(id, 1);
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
    
    @RequestMapping(value = "bdEmerRepair/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdEmerRepairDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdEmerRepairService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdEmerRepair/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdEmerRepairService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
   	@DataControlPut(authCode="LOOK_BDEMERREPAIR", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdEmerRepair/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdEmerRepairDTO>> list(BdInfoQueryFilter entity) throws Exception {
   		Page<BdEmerRepairDTO> pg = new Page<BdEmerRepairDTO>();
   		List<BdEmerRepairDTO> list = bdEmerRepairService.findEmerRepairList(entity);
        if(StringUtil.isNotEmpty(list)){
	      	pg.setList(list);
	      	pg.setTotal((long)list.size());
//	        polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdEmerRepairDTO>>(pg);
    }
    
   	@DataControlPut(authCode="LOOK_BDEMERREPAIR", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdEmerRepair/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdEmerRepairDTO>> list(Page<BdEmerRepairDTO> page,BdInfoQueryFilter entity) throws Exception {
        Page<BdEmerRepairDTO> pg = bdEmerRepairService.findEmerRepairPage(page,entity);
//        if(StringUtil.isNotEmpty(pg.getList())){
//           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
//        }
        return new RestfulResponse<Page<BdEmerRepairDTO>>(pg);
    }
 	
 	//校验名称是否重复
   	private void check(BdEmerRepairDTO entity) {
		if(null == entity.getName()) {
			Shift.fatal(StatusCode.NAME_EMPTY);
		}
		BdEmerRepairDTO bdEmerRepairDTO = new BdEmerRepairDTO();
		bdEmerRepairDTO.setId(entity.getId());
		bdEmerRepairDTO.setName(entity.getName());
		boolean unique = bdEmerRepairService.unique(bdEmerRepairDTO);
		if(!unique) {
			Shift.fatal(StatusCode.NAME_REPEAT);
		}
	}
}
