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
import com.togest.domain.BdFastRepaired;
import com.togest.domain.Page;
import com.togest.domain.dto.BdFastRepairedDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdFastRepairedService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdFastRepairedResource {
    @Autowired
    private BdFastRepairedService bdFastRepairedService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    @Autowired
    private ExportClient exportClient;
    
    private static final String resourcesCode =ResourcesCodeUtil.BDFASTREPAIRED_RESOURCESCODE;
  	

    @RequestMapping(value = "bdFastRepaired", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdFastRepairedDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        BdFastRepairedDTO entity = bdFastRepairedService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        
        polymerizeDataService.setNamesByIds(Arrays.asList(entity), resourcesCode);
        return new RestfulResponse<BdFastRepairedDTO>(entity);
    }
    
    @RequestMapping(value = "bdFastRepaired/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdFastRepairedDTO entity) throws Exception {
        
        return new RestfulResponse<Boolean>(bdFastRepairedService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdFastRepaired", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdFastRepairedDTO entity) throws Exception {
		check(entity);
        bdFastRepairedService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdFastRepaired/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdFastRepairedDTO entity = bdFastRepairedService.get(id, 1);
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
    
    @RequestMapping(value = "bdFastRepaired/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdFastRepairedDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdFastRepairedService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdFastRepaired/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdFastRepairedService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
   	@DataControlPut(authCode="LOOK_BDFASTREPAIRED", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdFastRepaired/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdFastRepairedDTO>> list(BdInfoQueryFilter entity) throws Exception {
   		Page<BdFastRepairedDTO> pg = new Page<BdFastRepairedDTO>();
        List<BdFastRepairedDTO> list = bdFastRepairedService.findFastReaireList(entity);
        if(StringUtil.isNotEmpty(list)){
        	pg.setList(list);
        	pg.setTotal((long)list.size());
//          polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
        return new RestfulResponse<Page<BdFastRepairedDTO>>(pg);
    }
   	
   	@DataControlPut(authCode="LOOK_BDFASTREPAIRED", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdFastRepaired/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdFastRepairedDTO>> pages(Page<BdFastRepairedDTO> page, BdInfoQueryFilter entity) throws Exception {
		Page<BdFastRepairedDTO> pg = bdFastRepairedService.findFastReairePage(page, entity);
//        if(StringUtil.isNotEmpty(page.getList())){
//            polymerizeDataService.setNamesByIds(page.getList(), resourcesCode);
//        }
        return new RestfulResponse<Page<BdFastRepairedDTO>>(pg);
    }
   	
   	//校验线路是否重复
   	private void check(BdFastRepaired entity) {
		if(null == entity.getLineId()) {
			Shift.fatal(StatusCode.LINE_NAME_EMPTY);
		}
		BdFastRepairedDTO bdFastRepaired = new BdFastRepairedDTO();
		bdFastRepaired.setId(entity.getId());
		bdFastRepaired.setLineId(entity.getLineId());
		boolean unique = bdFastRepairedService.unique(bdFastRepaired);
		if(!unique) {
			Shift.fatal(StatusCode.LINE_NAME_REPEAT);
		}
	}
   	
   	@RequestMapping(value = "bdFastRepaired/imports", method = RequestMethod.POST)
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
		map = bdFastRepairedService.importDataByConfig(fileName,file.getInputStream(),templetId,
				null, null);
		return new RestfulResponse<Map<String,Object>>(map);
    }
   	
   	@DataControlPut(authCode="LOOK_BDFASTREPAIRED", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdFastRepaired/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<BdFastRepairedDTO> list = bdFastRepairedService.findFastReaireList(entity);
        if(StringUtil.isNotEmpty(list)){
            polymerizeDataService.setNamesByIds(list, resourcesCode);
        }
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		
		return FileDownload.fileDownload(b, "抢修路径图.xlsx");
    }
}
