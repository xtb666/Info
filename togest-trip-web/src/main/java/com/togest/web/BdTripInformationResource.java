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
import com.togest.domain.dto.BdTripInformationDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdTripInformationService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdTripInformationResource {
    @Autowired
    private BdTripInformationService bdTripInformationService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    @Autowired
    private ExportClient exportClient;
    
    private static final String resourcesCode =ResourcesCodeUtil.BDTRIPINFORMATION_RESOURCESCODE;

    @RequestMapping(value = "bdTripInformation", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdTripInformationDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
    	BdTripInformationDTO entity = bdTripInformationService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
//        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdTripInformationDTO>(entity);
    }
    
    @RequestMapping(value = "bdTripInformation/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdTripInformationDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(bdTripInformationService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdTripInformation", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdTripInformationDTO entity) throws Exception {
		check(entity);
        bdTripInformationService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdTripInformation/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdTripInformationDTO entity = bdTripInformationService.get(id);
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
    
    @RequestMapping(value = "bdTripInformation/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdTripInformationDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdTripInformationService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdTripInformation/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdTripInformationService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
   	@DataControlPut(authCode="LOOK_BDTRIPINFORMATION", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTripInformation/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdTripInformationDTO>> list(BdInfoQueryFilter entity) throws Exception {
   		Page<BdTripInformationDTO> pg = new Page<BdTripInformationDTO>();
   		List<BdTripInformationDTO> list = bdTripInformationService.findTripInformationList(entity);
        if(StringUtil.isNotEmpty(list)){
        	pg.setList(list);
        	pg.setTotal((long)list.size());
//          polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdTripInformationDTO>>(pg);
    }
    
	@DataControlPut(authCode="LOOK_BDTRIPINFORMATION", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTripInformation/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdTripInformationDTO>> list(Page<BdTripInformationDTO> page,BdInfoQueryFilter entity) throws Exception {
        Page<BdTripInformationDTO> pg = bdTripInformationService.findTripInformationPage(page,entity);
        return new RestfulResponse<Page<BdTripInformationDTO>>(pg);
    }
 	
 	@RequestMapping(value = "bdTripInformation/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,String createIp) throws Exception{
 	   Map<String, Object> map = null;
       if(StringUtil.isBlank(templetId)){
   		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
        }
		if(fileName == null){
	      	fileName = file.getOriginalFilename();
	     }
		map = bdTripInformationService.importDataByConfig(fileName,file.getInputStream(),templetId, null, null);
		return new RestfulResponse<Map<String,Object>>(map);
    }

	@DataControlPut(authCode="LOOK_BDTRIPINFORMATION", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdTripInformation/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<BdTripInformationDTO> list = bdTripInformationService.findTripInformationList(entity);
//        if(StringUtil.isNotEmpty(list)){
//            polymerizeDataService.setNamesByIds(list, resourcesCode);
//        }
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		
		return FileDownload.fileDownload(b, "综合管理信息.xlsx");
    }
	
 	private void check(BdTripInformationDTO entity) {
 		if(null == entity.getLineId()) {
			Shift.fatal(StatusCode.LINE_NAME_EMPTY);
		}
 		if(null == entity.getPavilionId()) {
 			Shift.fatal(StatusCode.PAVILION_NAME_EMPTY);
 		}
 		if(null == entity.getPsPdId()) {
 			Shift.fatal(StatusCode.PS_PD_NAME_EMPTY);
 		}
 		BdTripInformationDTO bdTripInformationDTO = new BdTripInformationDTO();
 		bdTripInformationDTO.setId(entity.getId());
 		bdTripInformationDTO.setLineId(entity.getLineId());
 		bdTripInformationDTO.setPavilionId(entity.getPavilionId());
 		bdTripInformationDTO.setPsPdId(entity.getPsPdId());
 		bdTripInformationDTO.setTime(entity.getTime());
		boolean unique = bdTripInformationService.unique(bdTripInformationDTO);
		if(!unique) {
			Shift.fatal(StatusCode.LINE_PAVILION_PSPD_TIME_NAME_REPEAT);
		}
	}
 	
}
