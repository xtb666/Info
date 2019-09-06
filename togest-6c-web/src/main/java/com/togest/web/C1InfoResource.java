package com.togest.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.clien.sevice.PillarFeginService;
import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.function.OptionalUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DataScope6CCode;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.C4InfoDTO;
import com.togest.domain.Check1CDTO;
import com.togest.domain.Check1CSectionDTO;
import com.togest.domain.Page;
import com.togest.model.request.FileUploadDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CheckQueryFilter;
import com.togest.request.InfoQueryFilter;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.C1InfoService;
import com.togest.service.Check1CSectionService;
import com.togest.service.Check1CService;
import com.togest.util.DataRightUtil;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;

import io.swagger.annotations.ApiOperation;

@RequestMapping("1C")
@RestController
public class C1InfoResource {

	@Autowired
	private C1InfoService c1InfoService;
	@Autowired
	private Check1CService check1CService;
	@Autowired
	private Check1CSectionService checkSectionService;
	@Autowired
	private PillarFeginService pillarFeginService;
	@Autowired
	private MetadataConfigClient metadataConfigClient;
	@Autowired
	private MetadataUtil metadataUtil;
	
	@RequestMapping(value="info",method =RequestMethod.GET)
	public RestfulResponse<C1InfoDTO> get(String id){
		
		if(StringUtil.isBlank(id)){
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		C1InfoDTO entity = c1InfoService.get(id);
		
		return new RestfulResponse<C1InfoDTO>(entity);
	}
	@RequestMapping(value="info",method =RequestMethod.POST)
	public RestfulResponse<Integer> save(C1InfoDTO entity){
		
		int i = c1InfoService.save(entity);
		
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value="info",method =RequestMethod.DELETE)
	public RestfulResponse<Integer> deleteFalses(String id){
		
		int i = c1InfoService.deleteFalses(id,null,null);
		
		return new RestfulResponse<Integer>(i);
	}
	@RequestMapping(value="info/lists",method =RequestMethod.GET)
	public RestfulResponse<List<C1InfoDTO>> findLists(InfoQueryFilter entity){
		
		List<C1InfoDTO> list = c1InfoService.findLists(entity);
		
		return new RestfulResponse<List<C1InfoDTO>>(list);
	}
	@RequestMapping(value="info/pages",method =RequestMethod.GET)
	public RestfulResponse<Page<C1InfoDTO>> findPages(Page page,InfoQueryFilter entity){
		
		Page<C1InfoDTO> pg = c1InfoService.findPages(page, entity);
		
		return new RestfulResponse<Page<C1InfoDTO>>(pg);
	}
	@RequestMapping(value = "check/templet/data", method = RequestMethod.POST)
	@ApiOperation(value = "导入检测作业-1C缺陷")
	public RestfulResponse<Map<String, Object>> importCheck(
			HttpServletRequest request, FileUploadDTO fud, String templetId,
			String fileName, String planId,String trainId,String infoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (fud != null && fud.getFile() != null) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = fud.getFile().getOriginalFilename();
				}
				map = check1CService.analyzeExcelData(fileName, fud.getFile().getInputStream(), fud.getCreateBy(),
						request.getRemoteHost(), planId, trainId, infoId);
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}
	
	@RequestMapping(value = "check/section/data", method = RequestMethod.POST)
	@ApiOperation(value = "导入检测作业(段)-1C缺陷")
	public RestfulResponse<Map<String, Object>> importSectionCheck(
			HttpServletRequest request, FileUploadDTO fud, String templetId,
			String fileName, String planId,String trainId,String infoId) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (fud != null && fud.getFile() != null) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = fud.getFile().getOriginalFilename();
				}
				map = check1CService.analyzeSectionData(fileName, fud.getFile()
						.getInputStream(), fud.getCreateBy(), request.getRemoteHost(), 
						TokenUtil.getUser(request).getSectionId(), planId, trainId,infoId);
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}
	@RequestMapping(value = "check/handleSectionData", method = RequestMethod.POST)
	@ApiOperation(value = "导入检测作业(段)-1C缺陷")
	public RestfulResponse<Map<String, Object>> handleCheckSectionData(
			HttpServletRequest request, FileUploadDTO fud, String templetId,
			String fileName, String planId,String trainId,String infoId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (fud != null && fud.getFile() != null) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = fud.getFile().getOriginalFilename();
				}
				UserInfo user = TokenUtil.getUser(request);
				map = check1CService.handleCheckSectionData(fileName, fud.getFile()
						.getInputStream(), templetId, trainId, user.getSectionId(), user.getId());
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}
	
	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ApiOperation(value = "更新检测作业")
	public RestfulResponse<Boolean> updateCheck1C(HttpServletRequest request,
			@RequestBody Check1CDTO entity) {
		entity.setUpdateBy(TokenUtil.getUserId(request));
		entity.setUpdateIp(request.getRemoteHost());
		check1CService.update(entity);
		return new RestfulResponse<Boolean>(true);
	}
	
	@RequestMapping(value = "check", method = RequestMethod.GET)
	@ApiOperation(value = "获取检测作业")
	public RestfulResponse<Check1CDTO> getCheck1CDTO(HttpServletRequest request,
			String id) {
		Check1CDTO entity = check1CService.get(id);
		return new RestfulResponse<Check1CDTO>(entity);
	}
	@DataControlPut(authCode="LOOK_TESTING_TASK_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "check/checkList/pages", method = RequestMethod.GET)
	@ApiOperation(value = "检测作业分页数据")
	public RestfulResponse<Page<Check1CDTO>> check1CPages(
			HttpServletRequest request, @ModelAttribute CheckQueryFilter entity,
			@ModelAttribute Page page) {
		//String code  = DataScope6CCode.LOOK_TESTING_TASK_1C.getStatus();
		//DataRightUtil.look(request, code, entity);
		Page<Check1CDTO> pg = check1CService.findCheckListPages(page, entity);
		return new RestfulResponse<Page<Check1CDTO>>(pg);

	}
	@DataControlPut(authCode="LOOK_TESTING_TASK_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "check/checkNotice/pages", method = RequestMethod.GET)
	@ApiOperation(value = "获取没有关联通知书的检测作业分页数据")
	public RestfulResponse<Page<Check1CDTO>> check1CNoticePages(
			HttpServletRequest request, @ModelAttribute CheckQueryFilter entity,
			@ModelAttribute Page page) {
		//String code  = DataScope6CCode.LOOK_TESTING_TASK_1C.getStatus();
		//DataRightUtil.look(request, code, entity);
		Page<Check1CDTO> pg = check1CService.check1CNoticePages(page, entity);
		return new RestfulResponse<Page<Check1CDTO>>(pg);

	}
	
	@RequestMapping(value = "check", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除检测作业数据")
	public RestfulResponse<Integer> deleteCheck1C(
			HttpServletRequest request, String id, String deleteBy) {
		int i = check1CService.deleteFalses(id, deleteBy,
				request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	@AddDataSectionPut
	@RequestMapping(value = "checkSection", method = RequestMethod.POST)
	@ApiOperation(value = "更新数据")
	public RestfulResponse<Boolean> updateCheck1CSection(HttpServletRequest request,
			@RequestBody Check1CSectionDTO entity) {
		entity.setUpdateBy(TokenUtil.getUserId(request));
		entity.setUpdateIp(request.getRemoteHost());
		checkSectionService.update(entity);
		return new RestfulResponse<Boolean>(true);
	}
	
	@RequestMapping(value = "checkSection", method = RequestMethod.GET)
	@ApiOperation(value = "获取数据")
	public RestfulResponse<Check1CSectionDTO> getCheckSection(HttpServletRequest request,
			String id) {
		Check1CSectionDTO entity = checkSectionService.get(id);
		return new RestfulResponse<Check1CSectionDTO>(entity);
	}
	
	@DataControlPut(authCode="LOOK_TEST_SECTION_JOB_1C",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "checkSection/checkSectionList/pages", method = RequestMethod.GET)
	@ApiOperation(value = "分页数据")
	public RestfulResponse<Page<Check1CSectionDTO>> check1CSectionPages(
			HttpServletRequest request, @ModelAttribute CheckQueryFilter entity,
			@ModelAttribute Page page) {
//		String code  = DataScope6CCode.LOOK_TESTING_TASK_1C.getStatus();
//		DataRightUtil.look(request, code, entity);
		Page<Check1CSectionDTO> pg = checkSectionService.findCheckSectionList(page, entity);
		return new RestfulResponse<Page<Check1CSectionDTO>>(pg);

	}
	
	@RequestMapping(value = "checkSection", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除数据")
	public RestfulResponse<Integer> deleteCheck1CSection(
			HttpServletRequest request, String id, String deleteBy) {
		int i = checkSectionService.deleteFalses(id, deleteBy,
				request.getRemoteHost());
		return new RestfulResponse<Integer>(i);
	}
	
	@RequestMapping(value = "info/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> infoEnclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		C1InfoDTO info = c1InfoService.get(id);
		SystemResoucesResponse response = OptionalUtils.map(metadataConfigClient.getSystemResources(resouceCode));
		Map<String, Object> map =(Map<String, Object>) ObjectUtils.objectToHashMap(info);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(metadataUtil.enclosure(map, response.getProps()));
	}
	
}
