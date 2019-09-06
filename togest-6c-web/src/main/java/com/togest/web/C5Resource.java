package com.togest.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DataScope6CCode;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.domain.Defect5CDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC5Request;
import com.togest.response.DefectC5Form;
import com.togest.response.DefectC5Response;
import com.togest.response.DefectFormDTO;
import com.togest.response.statistics.DefectLevelStatistics;
import com.togest.response.statistics.DefectTypeStatistics;
import com.togest.response.statistics.LineStatistics;
import com.togest.service.Defect5CService;
import com.togest.service.DefectConfService;
import com.togest.service.DefectStatisticsService;
import com.togest.service.impl.StatusCode;
import com.togest.util.DataRightUtil;
import com.togest.util.TokenUtil;

import io.swagger.annotations.ApiOperation;

@RequestMapping("5C")
@RestController
public class C5Resource {

	@Autowired
	private Defect5CService c5Service;
	@Autowired
	private DefectStatisticsService statisticsService;
	@Autowired
	private DefectConfService defectConfService;
	private static final Logger LOGGER = LoggerFactory.getLogger(C5Resource.class);

	@DataControlPut(authCode = "LOOK_DEFECT_REGISTRATION_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/register/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷登记的分页数据")
	public RestfulResponse<Page<DefectFormDTO>> registerDefectPages(HttpServletRequest request,
			@ModelAttribute CQueryFilter c5, @ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		c5.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		// String code = DataScope6CCode.REGISTRATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_DEFECT_ISSUED_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/assign/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷下达的分页数据")
	public RestfulResponse<Page<DefectFormDTO>> assignDefectPages(HttpServletRequest request,
			@ModelAttribute CQueryFilter c5, @ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		c5.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		// String code = DataScope6CCode.LOOK_DEFECT_ISSUED_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_DEFECT_AUDIT_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/audit/pages", method = RequestMethod.GET)
	@ApiOperation(value = "审核缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> audit(HttpServletRequest request, @ModelAttribute CQueryFilter c5,
			@ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		c5.setDefectStatus(DefectStatus.DefectAudit.getStatus());
		// String code = DataScope6CCode.AUDIT_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_SHOP_RECEIVE_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/receive/pages", method = RequestMethod.GET)
	@ApiOperation(value = "车间接收缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> receive(HttpServletRequest request, @ModelAttribute CQueryFilter c5,
			@ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		c5.setDefectStatus(DefectStatus.ShopReception.getStatus());
		// String code = DataScope6CCode.SHOP_RECEIVE_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_REVIEW_RECTIFICATION_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/reviewRectification/pages", method = RequestMethod.GET)
	@ApiOperation(value = "复核整改缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> reviewRectification(HttpServletRequest request,
			@ModelAttribute CQueryFilter c5, @ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		if (StringUtil.isBlank(c5.getDefectStatus())) {
			c5.setDefectStatus(
					DefectStatus.ReviewRectification.getStatus() + "," + DefectStatus.CHECKHANDLE.getStatus());
		}
		// String code = DataScope6CCode.REVIEW_RECTIFICATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_REFORM_AND_EXTENSION_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/delay/pages", method = RequestMethod.GET)
	@ApiOperation(value = "延期缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> delay(HttpServletRequest request, @ModelAttribute CQueryFilter c5,
			@ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		c5.setDefectStatus(DefectStatus.DELAY.getStatus());
		// String code = DataScope6CCode.REFORM_AND_EXTENSION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_RECTIFICATION_VERIFICATION_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/rectificationVerification/pages", method = RequestMethod.GET)
	@ApiOperation(value = "复核整改确认缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> rectificationVerification(HttpServletRequest request,
			@ModelAttribute CQueryFilter c5, @ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		c5.setDefectStatus(DefectStatus.RectificationVerification.getStatus());
		// String code =
		// DataScope6CCode.RECTIFICATION_VERIFICATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_5C", operationMode = "remote", deptFieldName = "did", isDataControl = true)
	@RequestMapping(value = "defect/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷信息分页数据")
	public RestfulResponse<Page<DefectFormDTO>> cancel(HttpServletRequest request, @ModelAttribute CQueryFilter c5,
			@ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		// if (StringUtil.isBlank(c5.getDefectStatus())) {
		// c5.setDefectStatus(DefectStatus.ShopReception.getStatus() + ","
		// +
		// DefectStatus.ReviewRectification.getStatus()+","+DefectStatus.CHECKHANDLE.getStatus()
		// + ","
		// + DefectStatus.RectificationVerification.getStatus() + ","
		// + DefectStatus.DELAY.getStatus() + ","
		// +
		// DefectStatus.Cancel.getStatus()+","+DefectStatus.CHECKHANDLE.getStatus());
		// }
		if (DefectStatus.NotCancel.getStatus().equals(c5.getDefectStatus())) {
			c5.setDefectStatus(
					DefectStatus.ShopReception.getStatus() + "," + DefectStatus.ReviewRectification.getStatus() + ","
							+ DefectStatus.RectificationVerification.getStatus() + "," + DefectStatus.DELAY.getStatus()
							+ "," + DefectStatus.CHECKHANDLE.getStatus() + "," + DefectStatus.DefectRegister.getStatus()
							+ "," + DefectStatus.DefectAudit.getStatus());
		}
		// String code = DataScope6CCode.DEFECT_INFORMATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@DataControlPut(authCode = "DEFECT_FILING_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/archive/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷归档信息分页数据")
	public RestfulResponse<Page<DefectFormDTO>> archive(HttpServletRequest request, @ModelAttribute CQueryFilter c5,
			@ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		c5.setDefectStatus(DefectStatus.Cancel.getStatus());
		c5.setArchiveFlag(true);
		Page<DefectFormDTO> pg = c5Service.findDefectFormPageV2(page, c5);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@RequestMapping(value = "defect", method = RequestMethod.DELETE)
	@ApiOperation(value = "缺陷删除")
	public RestfulResponse<Boolean> registerDeleteDefect(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		if (TokenUtil.isToken(TokenUtil.getToken(request))) {
			Defect5CDTO c5 = new Defect5CDTO();
			c5.setId(id);
			c5.setDeleteBy(TokenUtil.getUserTokenData(request, "id"));
			c5.setDeleteIp(request.getRemoteHost());
			c5Service.deleteFalses(c5);
		} else {
			Shift.fatal(StatusCode.FAIL);
		}
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "defect", method = RequestMethod.GET)
	@ApiOperation(value = "获取缺陷")
	public RestfulResponse<DefectC5Response> getDefect(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectC5Response entity = c5Service.getDefectPackageResponseByKey(id);
		return new RestfulResponse<DefectC5Response>(entity);
	}

	@AddDataSectionPut
	@RequestMapping(value = "defect", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷保存")
	public RestfulResponse<Boolean> saveDefect(HttpServletRequest request, @RequestBody DefectC5Request c5) {
		if (c5 != null) {
			if (StringUtil.isBlank(c5.getId())) {
				c5.setCreateIp(request.getRemoteHost());
				// c5.setSectionId(TokenUtil.getUser(request).getSectionId());
			} else {
				c5.setUpdateIp(request.getRemoteHost());
			}
		}
		if (StringUtil.isEmpty(c5.getSystemId())) {
			c5.setSystemId(DefectSystem.defectC5.getStatus());
		}
		c5Service.updateDefectC5Request(c5);
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "defect/total", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷保存(包括复测整改信息)")
	public RestfulResponse<Boolean> updateDefectC5Request(HttpServletRequest request, @RequestBody DefectC5Request c5) {
		if (c5 != null) {
			c5.setUpdateIp(request.getRemoteHost());
		}
		c5Service.updateDefectC5Request(c5);
		return new RestfulResponse<Boolean>(true);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/line/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷线路统计数据")
	public RestfulResponse<List<LineStatistics>> findLineStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c5) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		List<LineStatistics> list = statisticsService.findLineStatistics(c5);
		return new RestfulResponse<List<LineStatistics>>(list);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/defectType/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷类型统计数据")
	public RestfulResponse<List<DefectTypeStatistics>> findDefectTypeStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c5) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		List<DefectTypeStatistics> list = statisticsService.findDefectTypeStatistics(c5);
		return new RestfulResponse<List<DefectTypeStatistics>>(list);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/defectLevel/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷等级统计数据")
	public RestfulResponse<List<DefectLevelStatistics>> findDefectLevelStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c5) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);
		List<DefectLevelStatistics> list = statisticsService.findDefectLevelStatistics(c5);
		return new RestfulResponse<List<DefectLevelStatistics>>(list);
	}

	@RequestMapping(value = "defect/imports", method = RequestMethod.POST)
	@ApiOperation(value = "导入缺陷数据")
	public RestfulResponse<Boolean> importDefectData(HttpServletRequest request, MultipartFile file, String fileName,
			String createBy) {
		try {
			if (fileName == null) {
				fileName = file.getOriginalFilename();
			}
			c5Service.analyzeZipData(file.getOriginalFilename(), file.getInputStream(), createBy);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "realData", method = RequestMethod.POST)
	@ApiOperation(value = "传输缺陷数据")
	public RestfulResponse<Boolean> import5CData(HttpServletRequest request, MultipartFile file, String fileName,
			String createBy, String dataType) {
		if (StringUtil.isBlank(dataType)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		if (StringUtil.isBlank(fileName)) {
			fileName = file.getOriginalFilename();
		}
		try {
			c5Service.import5CData(fileName, file.getInputStream(), createBy, dataType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}

		return new RestfulResponse<Boolean>(true);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_5C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/export/data", method = RequestMethod.GET)
	@ApiOperation(value = "导入缺陷")
	public ResponseEntity importDefect(HttpServletRequest request, @ModelAttribute CQueryFilter c5, String templetId) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		// if (StringUtil.isBlank(c5.getDefectStatus())) {
		// c5.setDefectStatus(DefectStatus.ShopReception.getStatus() + ","
		// +
		// DefectStatus.ReviewRectification.getStatus()+","+DefectStatus.CHECKHANDLE.getStatus()
		// + ","
		// + DefectStatus.RectificationVerification.getStatus() + ","
		// + DefectStatus.DELAY.getStatus() + ","
		// +
		// DefectStatus.Cancel.getStatus()+","+DefectStatus.CHECKHANDLE.getStatus());
		// }
		if (DefectStatus.NotCancel.getStatus().equals(c5.getDefectStatus())) {
			c5.setDefectStatus(
					DefectStatus.ShopReception.getStatus() + "," + DefectStatus.ReviewRectification.getStatus() + ","
							+ DefectStatus.RectificationVerification.getStatus() + "," + DefectStatus.DELAY.getStatus()
							+ "," + DefectStatus.CHECKHANDLE.getStatus() + "," + DefectStatus.DefectRegister.getStatus()
							+ "," + DefectStatus.DefectAudit.getStatus());
		}
		// String code = DataScope6CCode.DEFECT_INFORMATION_5C.getStatus();
		// DataRightUtil.look(request, code, c5);

		List<DefectC5Response> list = null;
		if (StringUtil.isNotEmpty(c5) && StringUtil.isNotBlank(c5.getId())) {
			list = c5Service.getDefectPackageResponseByKeys(Arrays.asList(c5.getId().split(",")));
		} else {
			list = c5Service.findDefectPackageResponseList(c5);
		}
		ResponseEntity re = null;
		try {
			byte[] bt = c5Service.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "5C缺陷导出数据.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Shift.fatal(StatusCode.FAIL);
		}
		return re;

	}

	@RequestMapping(value = "defect/repeat/pages", method = RequestMethod.GET)
	@ApiOperation(value = "重复缺陷信息分页数据")
	public RestfulResponse<Page<DefectC5Form>> repeatDefect(HttpServletRequest request, @ModelAttribute CQueryFilter c5,
			@ModelAttribute Page page) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		String code = DataScope6CCode.DEFECT_INFORMATION_5C.getStatus();
		DataRightUtil.look(request, code, c5);
		Page<DefectC5Form> pg = c5Service.findDefectRepeatForm(page, c5);
		return new RestfulResponse<Page<DefectC5Form>>(pg);
	}

	@RequestMapping(value = "defect/repeat/export", method = RequestMethod.GET)
	@ApiOperation(value = "导出重复缺陷信息")
	public ResponseEntity repeatDefectExport(HttpServletRequest request, @ModelAttribute CQueryFilter c5,
			String templetId) {
		c5.setSystemId(DefectSystem.defectC5.getStatus());
		String code = DataScope6CCode.DEFECT_INFORMATION_5C.getStatus();
		DataRightUtil.look(request, code, c5);

		List<DefectC5Response> list = c5Service.findDefectPackageRepeatResponseList(c5);
		ResponseEntity re = null;
		try {
			byte[] bt = c5Service.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "5C缺陷导出数据.xls");
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
		DefectC5Response entity = c5Service.getDefectPackageResponseByKey(id);
		Map<String, List<Map<String, Object>>> map = defectConfService.handleEnclosure(resouceCode, entity);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);
	}
}
