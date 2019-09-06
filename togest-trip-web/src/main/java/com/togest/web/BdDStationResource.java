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
import com.togest.domain.Page;
import com.togest.domain.dto.BdDStationDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdDStationService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;


@RestController
public class BdDStationResource {
    @Autowired
    private BdDStationService bdDStationService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    @Autowired
    private ExportClient exportClient;
    
    private static final String resourcesCode = ResourcesCodeUtil.BDDSTATION_RESOURCESCODE;
  	

    @RequestMapping(value = "bdDStation", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdDStationDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        BdDStationDTO entity = bdDStationService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdDStationDTO>(entity);
    }
    
    @RequestMapping(value = "bdDStation/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdDStationDTO entity) throws Exception {
        
        return new RestfulResponse<Boolean>(bdDStationService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdDStation", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdDStationDTO entity) throws Exception {
		check(entity);
        bdDStationService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdDStation/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdDStationDTO entity = bdDStationService.get(id, 1);
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
    
    @RequestMapping(value = "bdDStation/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdDStationDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdDStationService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdDStation/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdDStationService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlPut(authCode="LOOK_BDDSTATION", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdDStation/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdDStationDTO>> list(BdInfoQueryFilter entity) throws Exception {
    	Page<BdDStationDTO> pg = new Page<BdDStationDTO>();
    	List<BdDStationDTO> list = bdDStationService.findDStationList(entity);
        if(StringUtil.isNotEmpty(list)){
	      	pg.setList(list);
	      	pg.setTotal((long)list.size());
//	        polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdDStationDTO>>(pg);
    }
    
 	@DataControlPut(authCode="LOOK_BDDSTATION", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdDStation/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdDStationDTO>> list(Page<BdDStationDTO> page, BdInfoQueryFilter entity) throws Exception {
        Page<BdDStationDTO> pg = bdDStationService.findDStationPage(page,entity);
//        if(StringUtil.isNotEmpty(pg.getList())){
//           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
//        }
        return new RestfulResponse<Page<BdDStationDTO>>(pg);
    }
 	
 	//校验线路是否重复
   	private void check(BdDStationDTO entity) {
		if(null == entity.getLineId()) {
			Shift.fatal(StatusCode.LINE_NAME_EMPTY);
		}
		BdDStationDTO bdDStationDTO = new BdDStationDTO();
		bdDStationDTO.setId(entity.getId());
		bdDStationDTO.setLineId(entity.getLineId());
		boolean unique = bdDStationService.unique(bdDStationDTO);
		if(!unique) {
			Shift.fatal(StatusCode.LINE_NAME_REPEAT);
		}
	}
   	
   	@RequestMapping(value = "bdDStation/imports", method = RequestMethod.POST)
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
		map = bdDStationService.importDataByConfig(fileName,file.getInputStream(),templetId, null, null);
		return new RestfulResponse<Map<String,Object>>(map);
    }

   	@DataControlPut(authCode="LOOK_BDDSTATION", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdDStation/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<BdDStationDTO> list = bdDStationService.findDStationList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		
		return FileDownload.fileDownload(b, "各线站细图.xlsx");
    }
}
