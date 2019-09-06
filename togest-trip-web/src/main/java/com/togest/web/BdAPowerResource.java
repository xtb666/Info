
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
import com.togest.domain.dto.BdAPowerDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.BdInfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdAPowerService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdAPowerResource {
    @Autowired
    private BdAPowerService bdAPowerService;
    @Autowired
	private MetadataUtil metadataUtil;
	@Autowired
    private MetadataConfigClient metadataConfigClient;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    @Autowired
    private ExportClient exportClient;
    
    public static final String resourcesCode = ResourcesCodeUtil.BDAPOWER_RESOURCESCODE;
  	

    @RequestMapping(value = "bdAPower", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<BdAPowerDTO> get(String id) throws Exception{
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
		}
        BdAPowerDTO entity = bdAPowerService.get(id);
       	if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        return new RestfulResponse<BdAPowerDTO>(entity);
    }
    
    @RequestMapping(value = "bdAPower/unique", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性判重")
    public RestfulResponse<Boolean> unique(BdAPowerDTO entity) throws Exception {
        return new RestfulResponse<Boolean>(bdAPowerService.unique(entity));
    }
    
	@AddDataPut
    @RequestMapping(value = "bdAPower", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(BdAPowerDTO entity) throws Exception {
		check(entity);
        bdAPowerService.save(entity);
        return new RestfulResponse<Boolean>(true);
    }

	@SuppressWarnings("unchecked")
    @RequestMapping(value = "bdAPower/enclosure", method = RequestMethod.GET)
    @ApiOperation(value = "配置信息")
    public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdAPowerDTO entity = bdAPowerService.get(id, 1);
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
    
    @RequestMapping(value = "bdAPower/delete", method = RequestMethod.POST)
    @ApiOperation(value = "假删除信息")
    public RestfulResponse<Boolean> delete(BdAPowerDTO entity) throws Exception {
    	if(StringUtil.isBlank(entity.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdAPowerService.delete(entity);
        return new RestfulResponse<Boolean>(true);
    }
    
    @RequestMapping(value = "bdAPower/delete/id", method = RequestMethod.POST)
    @ApiOperation(value = "真删除信息")
    public RestfulResponse<Boolean> delete(String id) throws Exception {
    	if(StringUtil.isBlank(id)){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
        bdAPowerService.delete(id);
        return new RestfulResponse<Boolean>(true);
    }
   
    @DataControlPut(authCode="LOOK_BDAPOWER", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdAPower/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看列表")
    public RestfulResponse<Page<BdAPowerDTO>> list(BdInfoQueryFilter entity) throws Exception {
    	Page<BdAPowerDTO> pg = new Page<BdAPowerDTO>();
        List<BdAPowerDTO> list = bdAPowerService.findAPowerList(entity);
        if(StringUtil.isNotEmpty(list)){
        	pg.setList(list);
        	pg.setTotal((long)list.size());
        }
        return new RestfulResponse<Page<BdAPowerDTO>>(pg);
    }
    
 	@DataControlPut(authCode="LOOK_BDAPOWER", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdAPower/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页列表")
    public RestfulResponse<Page<BdAPowerDTO>> list(Page<BdAPowerDTO> page, BdInfoQueryFilter entity) throws Exception {
        Page<BdAPowerDTO> pg = bdAPowerService.findAPowerPage(page,entity);
        return new RestfulResponse<Page<BdAPowerDTO>>(pg);
    }
 	
 	/**
 	 * <p>Title: check</p>
 	 * <p>Description: 数据校验</p>
 	 * <author>jiangzc</author>
 	 * 2018年11月8日下午4:13:48
 	 * @param entity
 	 */
 	private void check(BdAPowerDTO entity) {
 		if(null == entity.getLineId()) {
			Shift.fatal(StatusCode.LINE_NAME_EMPTY);
		}
 		if(null == entity.getPavilionId()) {
 			Shift.fatal(StatusCode.PAVILION_NAME_EMPTY);
 		}
 		if(null == entity.getPsPdId()) {
 			Shift.fatal(StatusCode.PS_PD_NAME_EMPTY);
 		}
 		BdAPowerDTO bdAPowerDTO = new BdAPowerDTO();
 		bdAPowerDTO.setId(entity.getId());
 		bdAPowerDTO.setLineId(entity.getLineId());
 		bdAPowerDTO.setPavilionId(entity.getPavilionId());
 		bdAPowerDTO.setPsPdId(entity.getPsPdId());
		boolean unique = bdAPowerService.unique(bdAPowerDTO);
		if(!unique) {
			Shift.fatal(StatusCode.LINE_PAVILION_PSPD_NAME_REPEAT);
		}
	}
 	
 	@RequestMapping(value = "bdAPower/imports", method = RequestMethod.POST)
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
//	    UserInfo info = TokenUtil.getUser(ServletContextHolder.getRequest());
//	    Map<String, Object> propMap = new HashMap<String,Object>();
//	    propMap.put(PropFieldConstant.createIp.getStatus(),createIp);
//	    propMap.put(PropFieldConstant.createBy.getStatus(),info.getId());
//	    propMap.put(PropFieldConstant.sectionId.getStatus(),info.getSectionId());
		map = bdAPowerService.importDataByConfig(fileName,file.getInputStream(),templetId,
				null, null);
		return new RestfulResponse<Map<String,Object>>(map);
    }

 	@DataControlPut(authCode="LOOK_BDAPOWER", deptFieldName = "deptId", isDataControl=true, isToken=true)
    @RequestMapping(value = "bdAPower/exports", method = RequestMethod.GET)
    @ApiOperation(value = "数据导出")
    public ResponseEntity<Resource> export(BdInfoQueryFilter entity, String templateId)  throws Exception{
        if(StringUtil.isBlank(templateId)){
    		Shift.fatal(StatusCode.TEMPLATEID_EMPTY);
    	}
        List<BdAPowerDTO> list = bdAPowerService.findAPowerList(entity);
		byte[] b = exportClient.exportDataByTemplet(list, templateId);
		return FileDownload.fileDownload(b, "故标对应.xlsx");
    }
}
