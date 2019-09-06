package com.togest.web;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;

import com.togest.domain.CInfo;
import com.togest.service.CInfoService;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.InfoQueryFilter;
import com.togest.code.client.ExportClient;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.code.util.MetadataUtil;
import com.togest.code.client.MetadataConfigClient;
import com.togest.response.SystemResoucesResponse;
import com.togest.response.statistics.InfoCAndSize;
import com.togest.common.annotation.Shift;
import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.annotation.DataControlSectionPut;
import com.togest.authority.annotation.UserDataScope;
import com.togest.authority.aspect.TokenUtil;
import com.togest.authority.aspect.UserInfo;
import com.togest.common.util.web.ServletContextHolder;
import com.togest.authority.config.PropFieldConstant;


@RestController
public class CInfoResource {
    @Autowired
    private CInfoService cInfoService;
	@Autowired
    private ExportClient exportClient;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    
    private static final String resourcesCode ="cInfo";
  	

    @RequestMapping(value = "cInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<CInfo> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        CInfo entity = cInfoService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        return new RestfulResponse<CInfo>(entity);
    }
    
    @RequestMapping(value = "cInfo/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(CInfo entity) throws Exception {
        
        return new RestfulResponse<Boolean>(cInfoService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "cInfo", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(CInfo entity) throws Exception {
        cInfoService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
 	@SuppressWarnings("unchecked")
    @RequestMapping(value = "cInfo/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        CInfo entity = cInfoService.get(id);
        if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        RestfulResponse<SystemResoucesResponse> response = metadataConfigClient.getSystemResources(resourcesCode);
        if(StringUtil.isEmpty(response)){
        	Shift.fatal(StatusCode.FAIL);
        }
        SystemResoucesResponse systemResoucesResponse = response.getData();
        if(StringUtil.isEmpty(response)){
        	Shift.fatal(StatusCode.PARAM_DATA_EMPTY);
        }
		Map<String, Object> map = (Map<String, Object>) ObjectUtils.objectToMap(entity);
        Map<String, List<Map<String, Object>>> maps = metadataUtil.enclosure(map, systemResoucesResponse.getProps());
        return new RestfulResponse<Map<String, List<Map<String, Object>>>>(maps);
    }
    
    @RequestMapping(value = "cInfo/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(CInfo entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        cInfoService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "cInfo/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        cInfoService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlSectionPut(authCode="LOOK_CINFO")
    @RequestMapping(value = "cInfo/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<List<CInfo>> list(CInfo entity) throws Exception {
        List<CInfo> list = cInfoService.findList(entity);
        return new RestfulResponse<List<CInfo>>(list);
    }
    
   	@UserDataScope(authCode="LOOK_CINFO",deptField="did" )
    @RequestMapping(value = "cInfo/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<CInfo>> list(Page<CInfo> page,InfoQueryFilter entity) throws Exception {
        Page<CInfo> pg = cInfoService.findPages(page,entity);
        return new RestfulResponse<Page<CInfo>>(pg);
    }

    @RequestMapping(value = "cInfo/imports", method = RequestMethod.POST)
    @ApiOperation(value = "数据导入")
    public RestfulResponse<Map<String,Object>> imports(MultipartFile file, String templetId,String fileName,String createIp) throws Exception{
         Map<String, Object> map = null;
        if(StringUtil.isBlank(templetId)){
    		Shift.fatal(StatusCode.FAIL);
    	}
		if(fileName == null){
	      	fileName = file.getOriginalFilename();
	     }
	    UserInfo info = TokenUtil.getUser(ServletContextHolder.getRequest());
	    Map<String, Object> propMap = new HashMap<String,Object>();
	    propMap.put(PropFieldConstant.createIp.getStatus(),createIp);
	    propMap.put(PropFieldConstant.createBy.getStatus(),info.getId());
	    propMap.put(PropFieldConstant.sectionId.getStatus(),info.getSectionId());
		return new RestfulResponse<Map<String,Object>>(map);
    }

    @DataControlSectionPut(authCode="LOOK_CINFO")
    @RequestMapping(value = "cInfo/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(CInfo entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.FAIL);
    	}
        List<CInfo> list = cInfoService.findList(entity);
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		
		return FileDownload.fileDownload(b, "cInfo.xlsx");
    }
    
 	@DataControlSectionPut(authCode="LOOK_CINFO")
	@RequestMapping(value = "cInfo/exports/custom", method = RequestMethod.GET)
	@ApiOperation(value = "用户自定义导出")
	public ResponseEntity<Resource> exportByCustom(String userId, CInfo entity) throws Exception {
		List<CInfo> list = cInfoService.findList(entity);
		byte[] data = exportClient.exportByConfigure(list, resourcesCode, userId);
		ResponseEntity<Resource> re = null;
		try {
			re = FileDownload.fileDownload(data, "cInfo.xlsx");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	
	@DataControlSectionPut(authCode="LOOK_CINFO")
	@RequestMapping(value = "cInfo/exports/preparation", method = RequestMethod.GET)
	@ApiOperation(value = "插件导出")
	public ResponseEntity<Resource> exportByPreparation(String headers,
			CInfo entity) throws Exception {
		List<CInfo> list = cInfoService.findList(entity);
		byte[] data = exportClient.exportByPlugIn(list, null);
		ResponseEntity<Resource> re = null;
		try {
			re = FileDownload.fileDownload(data,"cInfo.xlsx");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	
	@UserDataScope(authCode="LOOK_CINFO",deptField="did")
	@RequestMapping(value = "cInfo/findCAndSize", method = RequestMethod.GET)
	@ApiOperation(value = "检测信息首页")
	public RestfulResponse<List<Object>> findCAndSize(InfoQueryFilter entity){
		List<Object> list = cInfoService.findCAndSize(entity);
		return  new RestfulResponse<List<Object>>(list);
	}
	//@UserDataScope(authCode="LOOK_CINFO",deptField="did")
	@RequestMapping(value = "cInfo/findYAndMon", method = RequestMethod.GET)
	@ApiOperation(value = "检测信息首页2")
	public RestfulResponse<List<Object>> findYAndMon(InfoQueryFilter entity){
		List<Object> list = cInfoService.findYAndMon(entity);
		return  new RestfulResponse<List<Object>>(list);
	}
	
}
