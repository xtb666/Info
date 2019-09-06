package com.togest.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.PathUtil;
import com.togest.common.util.file.FileCopyUtils;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.file.StreamUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DataScope6CCode;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.domain.Defect3CDTO;
import com.togest.domain.Page;
import com.togest.model.request.FileUploadDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC3Request;
import com.togest.request.FileForm;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectC3Response;
import com.togest.response.DefectFormDTO;
import com.togest.response.statistics.DefectLevelStatistics;
import com.togest.response.statistics.DefectTypeStatistics;
import com.togest.response.statistics.LineStatistics;
import com.togest.service.C3InfoService;
import com.togest.service.Defect3CService;
import com.togest.service.DefectConfService;
import com.togest.service.DefectStatisticsService;
import com.togest.service.impl.StatusCode;
import com.togest.util.DataRightUtil;
import com.togest.util.TokenUtil;

import io.swagger.annotations.ApiOperation;

@RequestMapping("3C")
@RestController
public class C3Resource {

	@Autowired
	private Defect3CService c3Service;
	@Autowired
	private DefectStatisticsService statisticsService;
	@Autowired
	private C3InfoService c3InfoService;
	@Autowired
	private DefectConfService defectConfService;

	@DataControlPut(authCode = "LOOK_DEFECT_REGISTRATION_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/register/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷登记的分页数据")
	public RestfulResponse<Page<DefectFormDTO>> registerDefectPages(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3, @ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		// String code = DataScope6CCode.REGISTRATION_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_DEFECT_ISSUED_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/assign/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷下达的分页数据")
	public RestfulResponse<Page<DefectFormDTO>> assignDefectPages(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3, @ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		// String code = DataScope6CCode.LOOK_DEFECT_ISSUED_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_DEFECT_AUDIT_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/audit/pages", method = RequestMethod.GET)
	@ApiOperation(value = "审核缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> audit(HttpServletRequest request, @ModelAttribute CQueryFilter c3,
			@ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.DefectAudit.getStatus());
		// String code = DataScope6CCode.AUDIT_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_SHOP_RECEIVE_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/receive/pages", method = RequestMethod.GET)
	@ApiOperation(value = "车间接收缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> receive(HttpServletRequest request, @ModelAttribute CQueryFilter c3,
			@ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.ShopReception.getStatus());
		// String code = DataScope6CCode.SHOP_RECEIVE_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_REVIEW_RECTIFICATION_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/reviewRectification/pages", method = RequestMethod.GET)
	@ApiOperation(value = "复核整改缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> reviewRectification(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3, @ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		if (StringUtil.isBlank(c3.getDefectStatus())) {
			c3.setDefectStatus(
					DefectStatus.ReviewRectification.getStatus() + "," + DefectStatus.CHECKHANDLE.getStatus());
		}
		// String code = DataScope6CCode.REVIEW_RECTIFICATION_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_REFORM_AND_EXTENSION_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/delay/pages", method = RequestMethod.GET)
	@ApiOperation(value = "延期缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> delay(HttpServletRequest request, @ModelAttribute CQueryFilter c3,
			@ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.DELAY.getStatus());
		// String code = DataScope6CCode.REFORM_AND_EXTENSION_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_RECTIFICATION_VERIFICATION_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/rectificationVerification/pages", method = RequestMethod.GET)
	@ApiOperation(value = "复核整改确认缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> rectificationVerification(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3, @ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.RectificationVerification.getStatus());
		// String code =
		// DataScope6CCode.RECTIFICATION_VERIFICATION_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷信息分页数据")
	public RestfulResponse<Page<DefectFormDTO>> cancel(HttpServletRequest request, @ModelAttribute CQueryFilter c3,
			@ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		// if (StringUtil.isBlank(c3.getDefectStatus())) {
		// c3.setDefectStatus(DefectStatus.ShopReception.getStatus() + ","
		// +
		// DefectStatus.ReviewRectification.getStatus()+","+DefectStatus.CHECKHANDLE.getStatus()
		// + ","
		// + DefectStatus.RectificationVerification.getStatus() + ","
		// + DefectStatus.DELAY.getStatus() + ","
		// + DefectStatus.Cancel.getStatus()+ "," +
		// DefectStatus.CHECKHANDLE.getStatus());
		// }
		if (DefectStatus.NotCancel.getStatus().equals(c3.getDefectStatus())) {
			c3.setDefectStatus(
					DefectStatus.ShopReception.getStatus() + "," + DefectStatus.ReviewRectification.getStatus() + ","
							+ DefectStatus.RectificationVerification.getStatus() + "," + DefectStatus.DELAY.getStatus()
							+ "," + DefectStatus.CHECKHANDLE.getStatus() + "," + DefectStatus.DefectRegister.getStatus()
							+ "," + DefectStatus.DefectAudit.getStatus());
		}
		// String code = DataScope6CCode.DEFECT_INFORMATION_3C.getStatus();
		// @RequestLogging(request, code, c3);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@DataControlPut(authCode = "DEFECT_FILING_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/archive/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷归档信息分页数据")
	public RestfulResponse<Page<DefectFormDTO>> archive(HttpServletRequest request, @ModelAttribute CQueryFilter c3,
			@ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.Cancel.getStatus());
		c3.setArchiveFlag(true);
		Page<DefectFormDTO> pg = c3Service.findDefectFormPageV2(page, c3);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@RequestMapping(value = "defect", method = RequestMethod.DELETE)
	@ApiOperation(value = "缺陷删除")
	public RestfulResponse<Boolean> registerDeleteDefect(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		if (TokenUtil.isToken(TokenUtil.getToken(request))) {
			Defect3CDTO c3 = new Defect3CDTO();
			c3.setId(id);
			c3.setDeleteBy(TokenUtil.getUserTokenData(request, "id"));
			c3.setDeleteIp(request.getRemoteHost());
			c3Service.deleteFalses(c3);
		} else {
			Shift.fatal(StatusCode.FAIL);
		}
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "defect", method = RequestMethod.GET)
	@ApiOperation(value = "获取缺陷")
	public RestfulResponse<DefectC3Response> getDefect(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectC3Response entity = c3Service.getDefectPackageResponseByKey(id);
		return new RestfulResponse<DefectC3Response>(entity);
	}

	@AddDataSectionPut
	@RequestMapping(value = "defect", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷保存")
	public RestfulResponse<Boolean> saveDefect(HttpServletRequest request, @RequestBody DefectC3Request c3) {
		if (c3 != null) {
			if (StringUtil.isBlank(c3.getId())) {
				c3.setCreateIp(request.getRemoteHost());
				// c3.setSectionId(TokenUtil.getUser(request).getSectionId());
			} else {
				c3.setUpdateIp(request.getRemoteHost());
			}
		}
		if (StringUtil.isEmpty(c3.getSystemId())) {
			c3.setSystemId(DefectSystem.defectC3.getStatus());
		}
		c3Service.updateDefectC3Request(c3);
		return new RestfulResponse<Boolean>(true);
	}

	/*
	 * @RequestMapping(value = "defect/total", method = RequestMethod.POST)
	 * 
	 * @ApiOperation(value = "缺陷保存(包括复测整改信息)") public RestfulResponse<Boolean>
	 * updateDefectC3Request(HttpServletRequest request,
	 * 
	 * @RequestBody DefectC3Request c3) { if (c3 != null) {
	 * c3.setUpdateIp(request.getRemoteHost()); }
	 * c3Service.updateDefectC3Request(c3); return new
	 * RestfulResponse<Boolean>(true); }
	 */

	@RequestMapping(value = "defect/templet/data", method = RequestMethod.POST)
	@ApiOperation(value = "导入缺陷")
	public RestfulResponse<Map<String, Object>> importDefect(HttpServletRequest request, FileUploadDTO fud,
			String templetId, String fileName, String infoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (fud != null && fud.getFile() != null) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = fud.getFile().getOriginalFilename();
				}
				/*
				 * map = c3Service.analyzeExcelData(fileName, fud.getFile()
				 * .getInputStream(), templetId, fud.getCreateBy(),
				 * TokenUtil.getUser(request).getSectionId());
				 */
				map = c3Service.pareseData(fileName, fud.getFile().getInputStream(), templetId, fud.getCreateBy(),
						TokenUtil.getUser(request).getSectionId(), DefectSystem.defectC3.getStatus(), Defect3CDTO.class,
						infoId);
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/line/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷线路统计数据")
	public RestfulResponse<List<LineStatistics>> findLineStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		List<LineStatistics> list = statisticsService.findLineStatistics(c3);
		return new RestfulResponse<List<LineStatistics>>(list);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/defectType/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷类型统计数据")
	public RestfulResponse<List<DefectTypeStatistics>> findDefectTypeStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		List<DefectTypeStatistics> list = statisticsService.findDefectTypeStatistics(c3);
		return new RestfulResponse<List<DefectTypeStatistics>>(list);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/defectLevel/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷等级统计数据")
	public RestfulResponse<List<DefectLevelStatistics>> findDefectLevelStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_3C.getStatus();//
		// @RequestLogging(request, code, c3);
		List<DefectLevelStatistics> list = statisticsService.findDefectLevelStatistics(c3);
		return new RestfulResponse<List<DefectLevelStatistics>>(list);
	}

	@RequestMapping(value = "info/importXML", method = RequestMethod.POST)
	public RestfulResponse<Map<String, String>> importXML(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile xmlFile, @RequestParam MultipartFile[] imgFile, String createBy) {
		File src = null;
		Map<String, FileForm> imgMap = new HashMap<String, FileForm>();
		Map<String, String> msg = null;
		try {
			request.setCharacterEncoding("utf-8");
			String savePath = PathUtil.getSystempPath();
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String xmlName = xmlFile.getOriginalFilename();
			src = new File(dir, UUID.randomUUID() + xmlName.substring(xmlName.lastIndexOf(".")));
			FileCopyUtils.copy(xmlFile.getBytes(), src);
			if (imgFile != null) {
				for (int i = 0; i < imgFile.length; i++) {
					FileForm fileForm = new FileForm();
					String imgName = imgFile[i].getOriginalFilename().replaceAll("che", "车");
					InputStream imgInput = imgFile[i].getInputStream();
					fileForm.setOriginalName(imgName);
					fileForm.setData(StreamUtils.InputStreamTOByte(imgInput));
					if (imgName.contains("车_2_")) {
						imgMap.put("hw", fileForm);
					} else if (imgName.contains("车_3_")) {
						imgMap.put("kjg", fileForm);
					}
				}
			}
			msg = c3InfoService.saveXML(src, imgMap, createBy);
		} catch (Exception e) {
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL, e.getMessage());
		}
		return new RestfulResponse<Map<String, String>>(msg);
	}

	@RequestMapping(value = "info/importZip", method = RequestMethod.POST)
	public RestfulResponse<Map<String, String>> importXML(HttpServletRequest request, MultipartFile file,
			String fileName, String createBy) {
		Map<String, String> msg = null;
		if (StringUtil.isEmpty(file)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		if (StringUtil.isBlank(fileName)) {
			fileName = file.getOriginalFilename();
		}
		try {
			msg = c3InfoService.saveZip(file.getInputStream(), fileName, createBy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return new RestfulResponse<Map<String, String>>(msg);
	}

	@RequestMapping(value = "defect/register/notice/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷登记的分页数据(通知书用)")
	public RestfulResponse<Page<DefectC3Form>> registerDefect(HttpServletRequest request,
			@ModelAttribute CQueryFilter c3, @ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		c3.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		// c3.setDefectLevel("1");
		Page<DefectC3Form> pg = c3Service.findC3FormForNotice(page, c3);
		return new RestfulResponse<Page<DefectC3Form>>(pg);

	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_3C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/export/data", method = RequestMethod.GET)
	@ApiOperation(value = "导出缺陷")
	public ResponseEntity importDefect(HttpServletRequest request, @ModelAttribute CQueryFilter c3, String templetId) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		// if (StringUtil.isBlank(c3.getDefectStatus())) {
		// c3.setDefectStatus(DefectStatus.ShopReception.getStatus() + ","
		// +
		// DefectStatus.ReviewRectification.getStatus()+","+DefectStatus.CHECKHANDLE.getStatus()
		// + ","
		// + DefectStatus.RectificationVerification.getStatus() + ","
		// + DefectStatus.DELAY.getStatus() + ","
		// + DefectStatus.Cancel.getStatus()+ "," +
		// DefectStatus.CHECKHANDLE.getStatus());
		// }
		if (DefectStatus.NotCancel.getStatus().equals(c3.getDefectStatus())) {
			c3.setDefectStatus(
					DefectStatus.ShopReception.getStatus() + "," + DefectStatus.ReviewRectification.getStatus() + ","
							+ DefectStatus.RectificationVerification.getStatus() + "," + DefectStatus.DELAY.getStatus()
							+ "," + DefectStatus.CHECKHANDLE.getStatus() + "," + DefectStatus.DefectRegister.getStatus()
							+ "," + DefectStatus.DefectAudit.getStatus());
		}
		// String code = DataScope6CCode.DEFECT_INFORMATION_3C.getStatus();
		// @RequestLogging(request, code, c3);
		List<DefectC3Response> list = null;
		if (StringUtil.isNotEmpty(c3) && StringUtil.isNotBlank(c3.getId())) {
			list = c3Service.getDefectPackageResponseByKeys(Arrays.asList(c3.getId().split(",")));
		} else {
			list = c3Service.findDefectPackageResponseList(c3);
		}
		ResponseEntity re = null;
		try {
			byte[] bt = c3Service.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "3C缺陷导出数据.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Shift.fatal(StatusCode.FAIL);
		}
		return re;

	}

	@RequestMapping(value = "defect/repeat/pages", method = RequestMethod.GET)
	@ApiOperation(value = "重复缺陷信息分页数据")
	public RestfulResponse<Page<DefectC3Form>> repeatDefect(HttpServletRequest request, @ModelAttribute CQueryFilter c3,
			@ModelAttribute Page page) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		String code = DataScope6CCode.DEFECT_INFORMATION_3C.getStatus();
		DataRightUtil.look(request, code, c3);
		Page<DefectC3Form> pg = c3Service.findDefectRepeatForm2(page, c3);
		return new RestfulResponse<Page<DefectC3Form>>(pg);
	}

	@RequestMapping(value = "defect/repeat/export", method = RequestMethod.GET)
	@ApiOperation(value = "导出重复缺陷信息")
	public ResponseEntity repeatDefectExport(HttpServletRequest request, @ModelAttribute CQueryFilter c3,
			String templetId) {
		c3.setSystemId(DefectSystem.defectC3.getStatus());
		String code = DataScope6CCode.DEFECT_INFORMATION_3C.getStatus();
		DataRightUtil.look(request, code, c3);

		List<DefectC3Response> list = c3Service.findDefectPackageRepeatResponseList(c3);
		ResponseEntity re = null;
		try {
			byte[] bt = c3Service.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "3C缺陷导出数据.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}

	@RequestMapping(value = "defect/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往缺陷编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectC3Response entity = c3Service.getDefectPackageResponseByKey(id);
		Map<String, List<Map<String, Object>>> map = defectConfService.handleEnclosure(resouceCode, entity);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);
	}
}
