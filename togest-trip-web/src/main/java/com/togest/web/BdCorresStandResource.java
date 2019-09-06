package com.togest.web;

import java.util.ArrayList;
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
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.BdCorresStandDetail;
import com.togest.domain.Page;
import com.togest.domain.dto.BdCorresStandDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdCorresStandDetailService;
import com.togest.service.BdCorresStandImportService;
import com.togest.service.BdCorresStandService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdCorresStandResource {
    @Autowired
    private BdCorresStandService bdCorresStandService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    @Autowired
    private BdCorresStandImportService bdCorresStandImportService;
    @Autowired
    private ExportClient exportClient;
    @Autowired
    private BdCorresStandDetailService bdCorresStandDetailService;
    
    private static final String resourcesCode =ResourcesCodeUtil.BDCORRESSTAND_RESOURCESCODE;
  	
    @RequestMapping(value = "bdCorresStand", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdCorresStandDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
    	BdCorresStandDTO entity = bdCorresStandService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdCorresStandDTO>(entity);
    }
    
    @RequestMapping(value = "bdCorresStand/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdCorresStandDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(bdCorresStandService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdCorresStand", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdCorresStandDTO entity) throws Exception {
		check(entity);
        bdCorresStandService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdCorresStand/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdCorresStandDTO entity = bdCorresStandService.get(id);
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
    
    @RequestMapping(value = "bdCorresStand/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdCorresStandDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdCorresStandService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdCorresStand/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdCorresStandService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
   	@DataControlPut(authCode="LOOK_BDCORRESSTAND", deptFieldName = "deptId", isDataControl=true, isToken = true)
    @RequestMapping(value = "bdCorresStand/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdCorresStandDTO>> list(BdInfoQueryFilter entity) throws Exception {
   		Page<BdCorresStandDTO> pg = new Page<BdCorresStandDTO>();
   		List<BdCorresStandDTO> list = bdCorresStandService.findCorresStandList(entity);
        if(StringUtil.isNotEmpty(list)){
	      	pg.setList(list);
	      	pg.setTotal((long)list.size());
//	        polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdCorresStandDTO>>(pg);
    }
    
   	@DataControlPut(authCode="LOOK_BDCORRESSTAND", deptFieldName = "deptId", isDataControl=true, isToken = true)
    @RequestMapping(value = "bdCorresStand/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdCorresStandDTO>> list(Page<BdCorresStandDTO> page, BdInfoQueryFilter entity) throws Exception {
        Page<BdCorresStandDTO> pg = bdCorresStandService.findCorresStandPage(page,entity);
//        if(StringUtil.isNotEmpty(pg.getList())){
//           polymerizeDataService.setNamesByIds(pg.getList(), resourcesCode);
//        }
        return new RestfulResponse<Page<BdCorresStandDTO>>(pg);
    }
 	
 	@RequestMapping(value = "bdCorresStand/imports", method = RequestMethod.POST)
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
		map = bdCorresStandService.importDataByConfig(fileName, file.getInputStream(), templetId, null, null);
		return new RestfulResponse<Map<String,Object>>(map);
    }

 	@DataControlPut(authCode="LOOK_BDCORRESSTAND", deptFieldName = "deptId", isDataControl=true, isToken = true)
    @RequestMapping(value = "bdCorresStand/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.PARAM_DATA_EMPTY);
    	}
        BdCorresStandDTO bdCorresStandDTO = bdCorresStandService.get(entity.getId());
        if(null == bdCorresStandDTO){
    		Shift.fatal(StatusCode.PARAM_DATA_EMPTY);
    	}
        BdCorresStandDetail paramT = new BdCorresStandDetail();
        paramT.setStandId(entity.getId());
		List<BdCorresStandDetail> detailList = bdCorresStandDetailService.findList(paramT);
		List<Map<String, Object>> detailMaps = null;
		if(!CollectionUtils.isEmpty(detailList)) {
			detailMaps = new ArrayList<>();
			for(BdCorresStandDetail detail : detailList) {
				Map<String, Object> detailMap = (Map<String, Object>) ObjectUtils.objectToMap(detail);
				detailMaps.add(detailMap);
			}
		}
		Map<String, Object> standMap = (Map<String, Object>) ObjectUtils.objectToMap(bdCorresStandDTO);
		byte[] b = exportClient.exportDataDetailColumnByTemplet(standMap, detailMaps, templateId, false);
		return FileDownload.fileDownload(b, "故标对应导出.xls");
		
    }
 	
 	private void check(BdCorresStandDTO entity) {
 		if(null == entity.getLineId()) {
			Shift.fatal(StatusCode.LINE_NAME_EMPTY);
		}
 		if(null == entity.getPavilionId()) {
 			Shift.fatal(StatusCode.PAVILION_NAME_EMPTY);
 		}
 		if(null == entity.getPsPdId()) {
 			Shift.fatal(StatusCode.PS_PD_NAME_EMPTY);
 		}
 		BdCorresStandDTO bdCorresStandDTO = new BdCorresStandDTO();
 		bdCorresStandDTO.setId(entity.getId());
 		bdCorresStandDTO.setLineId(entity.getLineId());
 		bdCorresStandDTO.setPavilionId(entity.getPavilionId());
 		bdCorresStandDTO.setPsPdId(entity.getPsPdId());
		boolean unique = bdCorresStandService.unique(bdCorresStandDTO);
		if(!unique) {
			Shift.fatal(StatusCode.LINE_PAVILION_PSPD_NAME_REPEAT);
		}
	}
}
