package com.togest.web;

import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.AddDataPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.PathUtil;
import com.togest.common.util.function.OptionalUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectSystem;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.C2InfoDTO;
import com.togest.domain.C2PicIndexDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.InfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.C2InfoService;
import com.togest.service.C2PicIndexService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

@RequestMapping("2C")
@RestController
public class C2InfoResource {

	@Autowired
	private C2InfoService c2Infoservice;
	@Autowired
	private C2PicIndexService c2PicIndexservice;
	@Autowired
	private MetadataConfigClient metadataConfigClient;
	@Autowired
	private MetadataUtil metadataUtil;
	
	@RequestMapping(value = "info", method = RequestMethod.GET)
	@ApiOperation(value = "获取2C检测作业数据")
	public RestfulResponse<C2InfoDTO> getC2InfoDTO(HttpServletRequest request,
			String id) {
		C2InfoDTO entity = c2Infoservice.get(id);
		return new RestfulResponse<C2InfoDTO>(entity);
	}

	@AddDataPut
	@RequestMapping(value = "info", method = RequestMethod.POST)
	@ApiOperation(value = "更新2C检测作业")
	public RestfulResponse<Boolean> updateInfo(HttpServletRequest request,
			@RequestBody C2InfoDTO entity) {
		entity.setSystemId(DefectSystem.defectC2.getStatus());
		c2Infoservice.save(entity);
		return new RestfulResponse<Boolean>(true);
	}

	@DataControlPut(authCode="LOOK_TESTING_TASK_2C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "info/pages", method = RequestMethod.GET)
	@ApiOperation(value = "2C检测作业分页数据")
	public RestfulResponse<Page<C2InfoDTO>> findInfoPages(
			HttpServletRequest request, @ModelAttribute Page page,
			@ModelAttribute InfoQueryFilter entity) {
		//String code = DataScope6CCode.LOOK_TESTING_TASK_2C.getStatus();
		//DataRightUtil.look(request, code, entity);
		Page<C2InfoDTO> pg = c2Infoservice.findC2InfoPages(page, entity);
		return new RestfulResponse<Page<C2InfoDTO>>(pg);
	}

	@RequestMapping(value = "info", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除2C检测作业数据")
	public RestfulResponse<Integer> deleteInfo(HttpServletRequest request,
			String id, String deleteBy) {
		int i = c2Infoservice.deleteFalses(id, deleteBy,
				request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	
	@RequestMapping(value = "info/indexs", method = RequestMethod.GET)
	@ApiOperation(value = "获取2C检测作业索引数据")
	public RestfulResponse<C2PicIndexDTO> getPicIndex(
			HttpServletRequest request, String id) {
		C2PicIndexDTO entity = c2PicIndexservice.get(id);
		return new RestfulResponse<C2PicIndexDTO>(entity);
	}
	@RequestMapping(value = "info/indexs", method = RequestMethod.POST)
	@ApiOperation(value = "批量修改2C检测作业索引数据")
	public RestfulResponse<Boolean> updateBatchPicIndex(
			HttpServletRequest request, @RequestBody C2PicIndexDTO entity) {
		c2PicIndexservice.updateBatch(entity);;
		return new RestfulResponse<Boolean>(true);
	}
	@RequestMapping(value = "info/indexs", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除2C检测作业索引数据")
	public RestfulResponse<Boolean> deletePicIndex(
			HttpServletRequest request, String id,String deleteBy) {
		c2PicIndexservice.deleteFalses(id, deleteBy, request.getRemoteHost());
		return new RestfulResponse<Boolean>(true);
	}
	@RequestMapping(value = "info/indexs/lists", method = RequestMethod.GET)
	@ApiOperation(value = "2C检测作业索引集合数据")
	public RestfulResponse<List<C2PicIndexDTO>> findIndexLists(
			HttpServletRequest request, @ModelAttribute C2PicIndexDTO entity) {

		List<C2PicIndexDTO> pg = c2PicIndexservice.findList(entity);
		return new RestfulResponse<List<C2PicIndexDTO>>(pg);
	}
	
	@RequestMapping(value = "info/indexs/pages", method = RequestMethod.GET)
	@ApiOperation(value = "2C检测作业索引分页数据")
	public RestfulResponse<Page<C2PicIndexDTO>> findInfoPages(
			HttpServletRequest request, @ModelAttribute Page page,
			@ModelAttribute C2PicIndexDTO entity) {
		Page<C2PicIndexDTO> pg = c2PicIndexservice.findPage(page, entity);
		return new RestfulResponse<Page<C2PicIndexDTO>>(pg);
	}

	@RequestMapping(value = "info/indexs/imports", method = RequestMethod.POST)
	public RestfulResponse<Boolean> upload(MultipartFile infoFile,
			MultipartFile indexFile, HttpServletRequest request,
			String packageName, String packagePath, String source,
			String sectionId,String infoId) {
		File info = new File(PathUtil.getDirectoryPath() + "/"
				+ UUID.randomUUID() + "_info.xml");
		File index = new File(PathUtil.getDirectoryPath() + "/"
				+ UUID.randomUUID() + "_index.mdb");
		try {

			FileCopyUtils.copy(infoFile.getBytes(), info);
			FileCopyUtils.copy(indexFile.getBytes(), index);
			if (StringUtil.isBlank(sectionId)) {
				UserInfo user = TokenUtil.getUser(request);
				sectionId = user.getSectionId();
			}
			c2PicIndexservice.analyzeData(info.getAbsolutePath(),
					index.getAbsolutePath(), packageName, packagePath,
					"PICINDEX", sectionId,source,infoId);
			info.delete();
			index.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL, e.getMessage());
		}

		return new RestfulResponse<Boolean>(true);
	}
	
	@RequestMapping(value = "info/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> infoEnclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		C2InfoDTO info = c2Infoservice.get(id);
		SystemResoucesResponse response = OptionalUtils.map(metadataConfigClient.getSystemResources(resouceCode));
		Map<String, Object> map =(Map<String, Object>) ObjectUtils.objectToHashMap(info);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(metadataUtil.enclosure(map, response.getProps()));
	}
	
}
