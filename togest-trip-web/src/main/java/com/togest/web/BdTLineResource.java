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
import com.togest.domain.dto.BdTLineDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdTLineService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdTLineResource {
    @Autowired
    private BdTLineService bdTLineService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    @Autowired
    private ExportClient exportClient;
    
    private static final String resourcesCode =ResourcesCodeUtil.BDTLINE_RESOURCESCODE;
  	

    @RequestMapping(value = "bdTLine", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdTLineDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        BdTLineDTO entity = bdTLineService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdTLineDTO>(entity);
    }
    
    @RequestMapping(value = "bdTLine/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdTLineDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(bdTLineService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdTLine", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdTLineDTO entity) throws Exception {
		check(entity);
        bdTLineService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdTLine/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdTLineDTO entity = bdTLineService.get(id);
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
    
    @RequestMapping(value = "bdTLine/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdTLineDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdTLineService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdTLine/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdTLineService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlPut(authCode="LOOK_BDTLINE", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTLine/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdTLineDTO>> list(BdInfoQueryFilter entity) throws Exception {
    	Page<BdTLineDTO> pg = new Page<BdTLineDTO>();
   		List<BdTLineDTO> list = bdTLineService.findTLineList(entity);
        if(StringUtil.isNotEmpty(list)){
        	pg.setList(list);
        	pg.setTotal((long)list.size());
//          polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdTLineDTO>>(pg);
    }
    
    @DataControlPut(authCode="LOOK_BDTLINE", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTLine/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdTLineDTO>> list(Page<BdTLineDTO> page, BdInfoQueryFilter entity) throws Exception {
        Page<BdTLineDTO> pg = bdTLineService.findTLinePage(page,entity);
//        if(StringUtil.isNotEmpty(pg.getList())){
//           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
//        }
        return new RestfulResponse<Page<BdTLineDTO>>(pg);
    }
 	
 	@RequestMapping(value = "bdTLine/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,String createIp) throws Exception{
 		Map<String, Object> map = null;
        if(StringUtil.isBlank(templetId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
         }
 		if(fileName == null){
 	      	fileName = file.getOriginalFilename();
 	     }
 		map = bdTLineService.importDataByConfig(fileName,file.getInputStream(),templetId, null, null);
 		return new RestfulResponse<Map<String,Object>>(map);
    }

 	@DataControlPut(authCode="LOOK_BDTLINE", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTLine/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<BdTLineDTO> list = bdTLineService.findTLineList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		return FileDownload.fileDownload(b, "供电臂T线电流标准.xlsx");
    }
 	
 	private void check(BdTLineDTO entity) {
 		if(null == entity.getLineId()) {
			Shift.fatal(StatusCode.LINE_NAME_EMPTY);
		}
 		if(null == entity.getPavilionId()) {
 			Shift.fatal(StatusCode.PAVILION_NAME_EMPTY);
 		}
 		if(null == entity.getPsPdId()) {
 			Shift.fatal(StatusCode.PS_PD_NAME_EMPTY);
 		}
 		BdTLineDTO bdTLineDTO = new BdTLineDTO();
 		bdTLineDTO.setId(entity.getId());
 		bdTLineDTO.setLineId(entity.getLineId());
 		bdTLineDTO.setPavilionId(entity.getPavilionId());
 		bdTLineDTO.setPsPdId(entity.getPsPdId());
		boolean unique = bdTLineService.unique(bdTLineDTO);
		if(!unique) {
			Shift.fatal(StatusCode.LINE_PAVILION_PSPD_NAME_REPEAT);
		}
	}
}
