package com.togest.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.code.client.ExportClient;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.BdLineBridge;
import com.togest.domain.Page;
import com.togest.domain.dto.BdLineBridgeDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdLineBridgeService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdLineBridgeResource {
    @Autowired
    private BdLineBridgeService bdLineBridgeService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    @Autowired
    private ExportClient exportClient;
    
    private static final String resourcesCode =ResourcesCodeUtil.BDLINEBRIDGE_RESOURCESCODE;
  	

    @RequestMapping(value = "bdLineBridge", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdLineBridge> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
    	BdLineBridgeDTO entity = bdLineBridgeService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdLineBridge>(entity);
    }
    
    @RequestMapping(value = "bdLineBridge/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdLineBridgeDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(bdLineBridgeService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdLineBridge", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdLineBridgeDTO entity) throws Exception {
//		check(entity);
        bdLineBridgeService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdLineBridge/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdLineBridgeDTO entity = bdLineBridgeService.get(id, 1);
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
    
    @RequestMapping(value = "bdLineBridge/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdLineBridgeDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdLineBridgeService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdLineBridge/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdLineBridgeService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
    
    @DataControlPut(authCode="LOOK_BDLINEBRIDGE", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdLineBridge/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdLineBridgeDTO>> list(BdInfoQueryFilter entity) throws Exception {
    	Page<BdLineBridgeDTO> pg = new Page<BdLineBridgeDTO>();
    	List<BdLineBridgeDTO> list = bdLineBridgeService.findLineBridgeList(entity);
        if(StringUtil.isNotEmpty(list)){
        	pg.setList(list);
        	pg.setTotal((long)list.size());
//          polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdLineBridgeDTO>>(pg);
    }
    
    @DataControlPut(authCode="LOOK_BDLINEBRIDGE", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdLineBridge/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdLineBridgeDTO>> list(Page<BdLineBridgeDTO> page,BdInfoQueryFilter entity) throws Exception {
        Page<BdLineBridgeDTO> pg = bdLineBridgeService.findLineBridgePage(page,entity);
//        if(StringUtil.isNotEmpty(pg.getList())){
//           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
//        }
        return new RestfulResponse<Page<BdLineBridgeDTO>>(pg);
    }
    
    //校验线路是否重复
   	private void check(BdLineBridgeDTO entity) {
		if(null == entity.getLineId()) {
			Shift.fatal(StatusCode.LINE_NAME_EMPTY);
		}
		BdLineBridgeDTO bdLineBridgeDTO = new BdLineBridgeDTO();
		bdLineBridgeDTO.setId(entity.getId());
		bdLineBridgeDTO.setLineId(entity.getLineId());
		boolean unique = bdLineBridgeService.unique(bdLineBridgeDTO);
		if(!unique) {
			Shift.fatal(StatusCode.LINE_NAME_REPEAT);
		}
	}
   	
   	@RequestMapping(value = "bdLineBridge/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(HttpServletRequest request, MultipartFile file, 
    		String templetId,String fileName,String createIp) throws Exception{
         Map<String, Object> map = null;
        if(StringUtil.isBlank(templetId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
		if(fileName == null){
	      	fileName = file.getOriginalFilename();
	     }
		map = bdLineBridgeService.importDataByConfig(fileName,file.getInputStream(),templetId,
				null, null);
		return new RestfulResponse<Map<String,Object>>(map);
    }
   	
   	@DataControlPut(authCode="LOOK_BDLINEBRIDGE", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdLineBridge/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<BdLineBridgeDTO> list = bdLineBridgeService.findLineBridgeList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		
		return FileDownload.fileDownload(b, "上跨线、桥分布.xlsx");
    }
}
