package com.togest.web;

import java.util.Arrays;
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
import com.togest.code.client.ExportClient;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Page;
import com.togest.domain.dto.BdTripVideoMenuDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdTripVideoMenuImportService;
import com.togest.service.BdTripVideoMenuService;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;


@RestController
public class BdTripVideoMenuResource {
    @Autowired
    private BdTripVideoMenuService bdTripVideoMenuService;
	@Autowired
    private ExportClient exportClient;
    @Autowired
    private BdTripVideoMenuImportService bdTripVideoMenuImportService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    
    private static final String resourcesCode ="bdTripVideoMenu";
  	

    @RequestMapping(value = "bdTripVideoMenu", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdTripVideoMenuDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
    	BdTripVideoMenuDTO entity = bdTripVideoMenuService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdTripVideoMenuDTO>(entity);
    }
    
    @RequestMapping(value = "bdTripVideoMenu/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdTripVideoMenuDTO entity) throws Exception {
        
        return new RestfulResponse<Boolean>(bdTripVideoMenuService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdTripVideoMenu", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdTripVideoMenuDTO entity) throws Exception {
        bdTripVideoMenuService.save(entity, null, 0);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdTripVideoMenu/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdTripVideoMenuDTO entity = bdTripVideoMenuService.get(id);
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
    
    @RequestMapping(value = "bdTripVideoMenu/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdTripVideoMenuDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdTripVideoMenuService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdTripVideoMenu/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdTripVideoMenuService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
   	@DataControlPut(authCode="LOOK_BDTRIPVIDEOMENU", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTripVideoMenu/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdTripVideoMenuDTO>> list(BdInfoQueryFilter entity) throws Exception {
   		Page<BdTripVideoMenuDTO> pg = new Page<>();
        List<BdTripVideoMenuDTO> list = bdTripVideoMenuService.findTripVideoMenuList(entity);
        if(StringUtil.isNotEmpty(list)){
        	pg.setList(list);
        	pg.setTotal((long)list.size());
//          polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdTripVideoMenuDTO>>(pg);
    }
    
   	@DataControlPut(authCode="LOOK_BDTRIPVIDEOMENU", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTripVideoMenu/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdTripVideoMenuDTO>> page(Page<BdTripVideoMenuDTO> page, BdInfoQueryFilter entity) throws Exception {
        Page<BdTripVideoMenuDTO> pg = bdTripVideoMenuService.findTripVideoMenuPage(page,entity);
        if(StringUtil.isNotEmpty(pg.getList())){
           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
        }
        return new RestfulResponse<Page<BdTripVideoMenuDTO>>(pg);
    }

    @RequestMapping(value = "bdTripVideoMenu/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,String createIp) throws Exception{
    	 Map<String, Object> map = null;
    	if(StringUtil.isBlank(templetId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
		if(fileName == null){
	      	fileName = file.getOriginalFilename();
	     }
		map = bdTripVideoMenuService.importDataByConfig(fileName,file.getInputStream(),templetId, null, null);
		return new RestfulResponse<Map<String,Object>>(map);
    }

    @DataControlPut(authCode="LOOK_BDTRIPVIDEOMENU", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTripVideoMenu/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<BdTripVideoMenuDTO> list = bdTripVideoMenuService.findTripVideoMenuList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		
		return FileDownload.fileDownload(b, "段管内综合视频目录.xlsx");
    }
    
	
}
