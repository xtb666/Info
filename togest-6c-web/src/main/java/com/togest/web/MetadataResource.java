package com.togest.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.togest.service.MetadataService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;
import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.Metadata;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;

import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@RestController
public class MetadataResource {
    @Autowired
    private MetadataService MetadataService;


    @RequestMapping(value = "Metadata", method = RequestMethod.GET)
    @ApiOperation(value = "查看信息")
    public RestfulResponse<Metadata> get(HttpServletRequest request,String id){
    	if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
    	Metadata entity = MetadataService.get(id);
        return new RestfulResponse<Metadata>(entity);
    }
    
    
    @RequestMapping(value = "Metadata", method = RequestMethod.POST)
    @ApiOperation(value = "保存信息")
    public RestfulResponse<Boolean> save(HttpServletRequest request,@RequestBody Metadata entity){
    	UserInfo info = TokenUtil.getUser(request);
    	Date date = new Date();
    	if (entity != null ) {
    		if( StringUtil.isBlank(entity.getName()))
    			Shift.fatal(StatusCode.NAME_EMPTY);
    		if (StringUtil.isBlank(entity.getCode()))
    			Shift.fatal(StatusCode.TYPE_EMPTY);
    		 if (StringUtil.isBlank(entity.getFileId())||StringUtil.isBlank(entity.getFileName())) 
    			Shift.fatal(StatusCode.UPLOAD_DATA_EMPTY);
    	    	
    		 entity.setUploadDate(date);
    		 String ip = request.getRemoteHost();
    	    	if(StringUtil.isNotEmpty(entity.getId())){
    	    		entity.setUpdateBy(info.getName());
    	    		entity.setUpdateDate(date);
    	    		entity.setUpdateIp(ip);
    	    		
    	    	} else{
    	    		entity.setCreateBy(info.getName());
    	    		entity.setCreateDate(date);
    	    		entity.setCreateIp(ip);
    	    		entity.setUploader(info.getDeptName()+":"+info.getName());
    	    	}
    	    	MetadataService.save(entity);
    	        return new RestfulResponse<Boolean>(true);
    		}
    	 return new RestfulResponse<Boolean>(false);
    	}
    	 
    @RequestMapping(value = "Metadata/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除信息")
    public RestfulResponse<Integer> delete(HttpServletRequest request,String id){
    	UserInfo info = TokenUtil.getUser(request);
    	String deleteBy = info.getName();
    	if (StringUtil.isBlank(id) || StringUtil.isBlank(deleteBy)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
    	String ip = request.getRemoteHost();
		int n = MetadataService.deleteFalses(id, deleteBy, ip);
        return new RestfulResponse<Integer>(n);
    }    
   
    @RequestMapping(value = "Metadata/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看集合")
    public RestfulResponse<List<Metadata>> list(HttpServletRequest request, Metadata entity){
        List<Metadata> list = MetadataService.findList(entity);
        return new RestfulResponse<List<Metadata>>(list);
    }
    
    @RequestMapping(value = "Metadata/pages", method = RequestMethod.GET)
    @ApiOperation(value = "查看分页数据")
    public RestfulResponse<Page<Metadata>> list(HttpServletRequest request,Page<Metadata> page,  Metadata entity){
        Page<Metadata> pg = MetadataService.findPage(page,entity);
        return new RestfulResponse<Page<Metadata>>(pg);
    }  
    @RequestMapping(value = "Metadata/allList", method = RequestMethod.GET)
    @ApiOperation(value = "查看全部集合")
    public RestfulResponse<List<Metadata>> allList(HttpServletRequest request){
    	 List<Metadata> list = MetadataService.findAllList();
    	return new RestfulResponse<List<Metadata>>(list);
    }
}
