package com.togest.web;

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
import com.togest.domain.C4InfoDTO;
import com.togest.domain.C4PicIndexDTO;
import com.togest.domain.Naming;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.InfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.C4InfoService;
import com.togest.service.C4PicIndexService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

import io.swagger.annotations.ApiOperation;

@RequestMapping("4C")
@RestController
public class C4InfoResource {

	@Autowired
	private C4InfoService c4Infoservice;
	@Autowired
	private C4PicIndexService c4PicIndexservice;
	@Autowired
	private MetadataConfigClient metadataConfigClient;
	@Autowired
	private MetadataUtil metadataUtil;

	@RequestMapping(value = "info", method = RequestMethod.GET)
	@ApiOperation(value = "获取4C检测作业数据")
	public RestfulResponse<C4InfoDTO> getC4InfoDTO(HttpServletRequest request,
			String id) {
		C4InfoDTO entity = c4Infoservice.get(id);
		return new RestfulResponse<C4InfoDTO>(entity);
	}
	
	@AddDataPut
	@RequestMapping(value = "info", method = RequestMethod.POST)
	@ApiOperation(value = "更新4C检测作业")
	public RestfulResponse<Boolean> updateInfo(HttpServletRequest request,
			@RequestBody C4InfoDTO entity) {
		entity.setSystemId(DefectSystem.defectC4.getStatus());
		entity.setUpdateIp(request.getRemoteHost());
		c4Infoservice.save(entity);
		return new RestfulResponse<Boolean>(true);
	}

	@DataControlPut(authCode="LOOK_TESTING_TASK_4C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "info/pages", method = RequestMethod.GET)
	@ApiOperation(value = "4C检测作业分页数据")
	public RestfulResponse<Page<C4InfoDTO>> findInfoPages(
			HttpServletRequest request, @ModelAttribute Page page,
			@ModelAttribute InfoQueryFilter entity) {
		//String code = DataScope6CCode.LOOK_TESTING_TASK_4C.getStatus();
		//DataRightUtil.look(request, code, entity);
		Page<C4InfoDTO> pg = c4Infoservice.findC4InfoPages(page, entity);
		return new RestfulResponse<Page<C4InfoDTO>>(pg);
	}

	@RequestMapping(value = "info", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除4C检测作业数据")
	public RestfulResponse<Integer> deleteInfo(HttpServletRequest request,
			String id, String deleteBy) {
		int i = c4Infoservice.deleteFalses(id, deleteBy,
				request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value = "info/indexs", method = RequestMethod.GET)
	@ApiOperation(value = "获取4C检测作业索引数据")
	public RestfulResponse<C4PicIndexDTO> getPicIndex(
			HttpServletRequest request, String id) {
		C4PicIndexDTO entity = c4PicIndexservice.get(id);
		return new RestfulResponse<C4PicIndexDTO>(entity);
	}
	@RequestMapping(value = "info/indexs", method = RequestMethod.POST)
	@ApiOperation(value = "批量修改4C检测作业索引数据")
	public RestfulResponse<Boolean> updateBatchPicIndex(
			HttpServletRequest request, @RequestBody C4PicIndexDTO entity) {
		c4PicIndexservice.updateBatch(entity);;
		return new RestfulResponse<Boolean>(true);
	}
	@RequestMapping(value = "info/indexs", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除4C检测作业索引数据")
	public RestfulResponse<Boolean> deletePicIndex(
			HttpServletRequest request, String id,String deleteBy) {
		c4PicIndexservice.deleteFalses(id, deleteBy, request.getRemoteHost());
		return new RestfulResponse<Boolean>(true);
	}
	@RequestMapping(value = "info/indexs/lists", method = RequestMethod.GET)
	@ApiOperation(value = "4C检测作业索引集合数据")
	public RestfulResponse<List<C4PicIndexDTO>> findIndexLists(
			HttpServletRequest request, @ModelAttribute C4PicIndexDTO entity) {

		List<C4PicIndexDTO> pg = c4PicIndexservice.findList(entity);
		return new RestfulResponse<List<C4PicIndexDTO>>(pg);
	}

	@RequestMapping(value = "info/indexs/pages", method = RequestMethod.GET)
	@ApiOperation(value = "4C检测作业索引分页数据")
	public RestfulResponse<Page<C4PicIndexDTO>> findInfoPages(
			HttpServletRequest request, @ModelAttribute Page page,
			@ModelAttribute C4PicIndexDTO entity) {
		Page<C4PicIndexDTO> pg = c4PicIndexservice.findPage(page, entity);
		return new RestfulResponse<Page<C4PicIndexDTO>>(pg);
	}

	@RequestMapping(value = "info/indexs/imports", method = RequestMethod.POST)
	public RestfulResponse<Boolean> upload(MultipartFile infoFile,
			MultipartFile indexFile, HttpServletRequest request,
			String packageName, String packagePath, String sectionId,
			String source,String infoId) {
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
			c4PicIndexservice.analyzeData(info.getAbsolutePath(),
					index.getAbsolutePath(), packageName, packagePath,
					"PICINDEX", sectionId, source,infoId);
			info.delete();
			index.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL, e.getMessage());
		}

		return new RestfulResponse<Boolean>(true);

	}
	
	@RequestMapping(value = "info/canmerNo/lists", method = RequestMethod.GET)
	@ApiOperation(value = "4C检测作业索引相机编号集合数据")
	public RestfulResponse<List<Naming>> getCanmerNo(
			HttpServletRequest request, String infoId) {

		List<Naming> list = c4PicIndexservice.getCanmerNoByInfoId(infoId);
		return new RestfulResponse<List<Naming>>(list);
	}
	@RequestMapping(value = "info/station/lists", method = RequestMethod.GET)
	@ApiOperation(value = "4C检测作业索引站区集合数据")
	public RestfulResponse<List<Naming>> getStation(
			HttpServletRequest request, String infoId) {
		
		List<Naming> list = c4PicIndexservice.getStationByInfoId(infoId);
		return new RestfulResponse<List<Naming>>(list);
	}
	@RequestMapping(value = "info/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> infoEnclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		C4InfoDTO info = c4Infoservice.get(id);
		SystemResoucesResponse response = OptionalUtils.map(metadataConfigClient.getSystemResources(resouceCode));
		Map<String, Object> map =(Map<String, Object>) ObjectUtils.objectToHashMap(info);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(metadataUtil.enclosure(map, response.getProps()));
	}
}
