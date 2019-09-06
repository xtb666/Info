package com.togest.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.AddDataSectionPut;
import com.togest.authority.annotation.DataControlPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DataScope6CCode;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.Page;
import com.togest.model.request.FileUploadDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC1Request;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC1Response;
import com.togest.response.DefectFormDTO;
import com.togest.response.statistics.DefectLevelStatistics;
import com.togest.response.statistics.DefectTypeStatistics;
import com.togest.response.statistics.LineStatistics;
import com.togest.service.Defect1CService;
import com.togest.service.DefectConfService;
import com.togest.service.DefectStatisticsService;
import com.togest.util.DataRightUtil;
import com.togest.util.TokenUtil;

import io.swagger.annotations.ApiOperation;

@RequestMapping("1C")
@RestController
public class C1Resource {

	@Autowired
	private Defect1CService c1Service;
	@Autowired
	private DefectStatisticsService statisticsService;
	@Autowired
	private DefectConfService defectConfService;
	@Value("${my.defect.isOpenDefectLevel}")
	private Boolean isOpenDefectLevel = false;

	@DataControlPut(authCode = "LOOK_DEFECT_REGISTRATION_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/register/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷登记的分页数据")
	public RestfulResponse<Page<DefectFormDTO>> registerDefectPages(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1, @ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		if (isOpenDefectLevel) {
			c1.setDefectLevel("2");
		}
		// String code = DataScope6CCode.REGISTRATION_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_DEFECT_ISSUED_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/assign/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷下达的分页数据")
	public RestfulResponse<Page<DefectFormDTO>> assignDefectPages(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1, @ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		// String code = DataScope6CCode.LOOK_DEFECT_ISSUED_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_DEFECT_AUDIT_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/audit/pages", method = RequestMethod.GET)
	@ApiOperation(value = "审核缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> audit(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			@ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.DefectAudit.getStatus());
		// String code = DataScope6CCode.AUDIT_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_SHOP_RECEIVE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/receive/pages", method = RequestMethod.GET)
	@ApiOperation(value = "车间接收缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> receive(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			@ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.ShopReception.getStatus());
		// String code = DataScope6CCode.SHOP_RECEIVE_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_REVIEW_RECTIFICATION_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/reviewRectification/pages", method = RequestMethod.GET)
	@ApiOperation(value = "复核整改缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> reviewRectification(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1, @ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		if (StringUtil.isBlank(c1.getDefectStatus())) {
			c1.setDefectStatus(
					DefectStatus.ReviewRectification.getStatus() + "," + DefectStatus.CHECKHANDLE.getStatus());
		}
		// String code = DataScope6CCode.REVIEW_RECTIFICATION_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_REFORM_AND_EXTENSION_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/delay/pages", method = RequestMethod.GET)
	@ApiOperation(value = "延期缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> delay(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			@ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.DELAY.getStatus());
		// String code = DataScope6CCode.REFORM_AND_EXTENSION_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);

	}

	@DataControlPut(authCode = "LOOK_RECTIFICATION_VERIFICATION_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/rectificationVerification/pages", method = RequestMethod.GET)
	@ApiOperation(value = "复核整改确认缺陷分页数据")
	public RestfulResponse<Page<DefectFormDTO>> rectificationVerification(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1, @ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.RectificationVerification.getStatus());
		// String code =
		// DataScope6CCode.RECTIFICATION_VERIFICATION_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷信息分页数据")
	public RestfulResponse<Page<DefectFormDTO>> cancel(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			@ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		// if (StringUtil.isBlank(c1.getDefectStatus())) {
		// c1.setDefectStatus(
		// DefectStatus.ShopReception.getStatus() + "," +
		// DefectStatus.ReviewRectification.getStatus() + ","
		// + DefectStatus.RectificationVerification.getStatus() + "," +
		// DefectStatus.DELAY.getStatus()
		// + "," + DefectStatus.Cancel.getStatus() + "," +
		// DefectStatus.CHECKHANDLE.getStatus());
		// }
		if (DefectStatus.NotCancel.getStatus().equals(c1.getDefectStatus())) {
			c1.setDefectStatus(
					DefectStatus.ShopReception.getStatus() + "," + DefectStatus.ReviewRectification.getStatus() + ","
							+ DefectStatus.RectificationVerification.getStatus() + "," + DefectStatus.DELAY.getStatus()
							+ "," + DefectStatus.CHECKHANDLE.getStatus() + "," + DefectStatus.DefectRegister.getStatus()
							+ "," + DefectStatus.DefectAudit.getStatus());
		}
		// String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();
		// DataRightUtil.look(request, code, c1);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@DataControlPut(authCode = "DEFECT_FILING_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/archive/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷归档信息分页数据")
	public RestfulResponse<Page<DefectFormDTO>> archive(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			@ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.Cancel.getStatus());
		c1.setArchiveFlag(true);
		Page<DefectFormDTO> pg = c1Service.findDefectFormPageV2(page, c1);
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/infoId", method = RequestMethod.GET)
	@ApiOperation(value = "通过检测作业获取缺陷")
	public RestfulResponse<Page<DefectC1Response>> infoId(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			@ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();
		// DataRightUtil.look(request, code, c1);
		Page<DefectC1Response> pg = c1Service.findDefectPackageResponsePage(page, c1);
		return new RestfulResponse<Page<DefectC1Response>>(pg);
	}

	@RequestMapping(value = "defect", method = RequestMethod.DELETE)
	@ApiOperation(value = "缺陷删除")
	public RestfulResponse<Boolean> registerDeleteDefect(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		if (TokenUtil.isToken(TokenUtil.getToken(request))) {
			Defect1CDTO c1 = new Defect1CDTO();
			c1.setId(id);
			c1.setDeleteBy(TokenUtil.getUserTokenData(request, "id"));
			c1.setDeleteIp(request.getRemoteHost());
			c1Service.deleteFalses(c1);
		} else {
			Shift.fatal(StatusCode.FAIL);
		}
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "defect", method = RequestMethod.GET)
	@ApiOperation(value = "获取缺陷")
	public RestfulResponse<DefectC1Response> getDefect(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectC1Response entity = c1Service.getDefectPackageResponseByKey(id);
		return new RestfulResponse<DefectC1Response>(entity);
	}

	@RequestMapping(value = "defect/{resouceCode}/enclosure", method = RequestMethod.GET)
	@ApiOperation(value = "去往缺陷编辑页面")
	public RestfulResponse<Map<String, List<Map<String, Object>>>> enclosure(String id,
			@PathVariable("resouceCode") String resouceCode) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		DefectC1Response entity = c1Service.getDefectPackageResponseByKey(id);
		Map<String, List<Map<String, Object>>> map = defectConfService.handleEnclosure(resouceCode, entity);
		return new RestfulResponse<Map<String, List<Map<String, Object>>>>(map);
	}

	@AddDataSectionPut
	@RequestMapping(value = "defect", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷保存")
	public RestfulResponse<Boolean> saveDefect(HttpServletRequest request, @RequestBody DefectC1Request c1) {
		if (c1 != null) {
			if (StringUtil.isBlank(c1.getId())) {
				c1.setCreateIp(request.getRemoteHost());
				// c1.setSectionId(TokenUtil.getUser(request).getSectionId());
			} else {
				c1.setUpdateIp(request.getRemoteHost());
			}
		}
		if (StringUtil.isEmpty(c1.getSystemId())) {
			c1.setSystemId(DefectSystem.defectC1.getStatus());
		}
		c1Service.updateDefectC1Request(c1);
		return new RestfulResponse<Boolean>(true);
	}

	/*
	 * @RequestMapping(value = "defect/total", method = RequestMethod.POST)
	 * 
	 * @ApiOperation(value = "缺陷保存(包括复测整改信息)") public RestfulResponse<Boolean>
	 * updateDefectC1Request(HttpServletRequest request,
	 * 
	 * @RequestBody DefectC1Request c1) { if (c1 != null) {
	 * c1.setUpdateIp(request.getRemoteHost()); }
	 * c1Service.updateDefectC1Request(c1); return new
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
				map = c1Service.pareseData(fileName, fud.getFile().getInputStream(), templetId, fud.getCreateBy(),
						TokenUtil.getUser(request).getSectionId(), DefectSystem.defectC1.getStatus(), Defect1CDTO.class,infoId);
			} catch (IOException e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		return new RestfulResponse<Map<String, Object>>(map);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/line/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷线路统计数据")
	public RestfulResponse<List<LineStatistics>> findLineStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		List<LineStatistics> list = statisticsService.findLineStatistics(c1);
		return new RestfulResponse<List<LineStatistics>>(list);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/defectType/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷类型统计数据")
	public RestfulResponse<List<DefectTypeStatistics>> findDefectTypeStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		List<DefectTypeStatistics> list = statisticsService.findDefectTypeStatistics(c1);
		return new RestfulResponse<List<DefectTypeStatistics>>(list);
	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/defectLevel/counts", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷等级统计数据")
	public RestfulResponse<List<DefectLevelStatistics>> findDefectLevelStatistics(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		// String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();//
		// DataRightUtil.look(request, code, c1);
		List<DefectLevelStatistics> list = statisticsService.findDefectLevelStatistics(c1);
		return new RestfulResponse<List<DefectLevelStatistics>>(list);
	}

	@RequestMapping(value = "defect/register/notice/pages", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷登记的分页数据(通知书用)")
	public RestfulResponse<Page<DefectC1Form>> registerDefect(HttpServletRequest request,
			@ModelAttribute CQueryFilter c1, @ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		c1.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		c1.setDefectLevel("1");
		Page<DefectC1Form> pg = c1Service.findC1FormForNotice(page, c1);
		return new RestfulResponse<Page<DefectC1Form>>(pg);

	}

	@DataControlPut(authCode = "DEFECT_INFORMATION_HANDLE_1C", deptFieldName = "did", isToken = true, isDataControl = true)
	@RequestMapping(value = "defect/export/data", method = RequestMethod.GET)
	@ApiOperation(value = "导出缺陷")
	public ResponseEntity importDefect(HttpServletRequest request, @ModelAttribute CQueryFilter c1, String templetId) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		// if (StringUtil.isBlank(c1.getDefectStatus())) {
		// c1.setDefectStatus(
		// DefectStatus.ShopReception.getStatus() + "," +
		// DefectStatus.ReviewRectification.getStatus() + ","
		// + DefectStatus.RectificationVerification.getStatus() + "," +
		// DefectStatus.DELAY.getStatus()
		// + "," + DefectStatus.Cancel.getStatus() + "," +
		// DefectStatus.CHECKHANDLE.getStatus());
		// }
		if (DefectStatus.NotCancel.getStatus().equals(c1.getDefectStatus())) {
			c1.setDefectStatus(
					DefectStatus.ShopReception.getStatus() + "," + DefectStatus.ReviewRectification.getStatus() + ","
							+ DefectStatus.RectificationVerification.getStatus() + "," + DefectStatus.DELAY.getStatus()
							+ "," + DefectStatus.CHECKHANDLE.getStatus() + "," + DefectStatus.DefectRegister.getStatus()
							+ "," + DefectStatus.DefectAudit.getStatus());
		}
		// String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();
		// DataRightUtil.look(request, code, c1);
		List<DefectC1Response> list = null;
		if (StringUtil.isNotEmpty(c1) && StringUtil.isNotBlank(c1.getId())) {
			list = c1Service.getDefectPackageResponseByKeys(Arrays.asList(c1.getId().split(",")));
		} else {
			list = c1Service.findDefectPackageResponseList(c1);
		}
		ResponseEntity re = null;
		try {
			byte[] bt = c1Service.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "1C缺陷导出数据.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return re;

	}

	@RequestMapping(value = "defect/repeat/pages", method = RequestMethod.GET)
	@ApiOperation(value = "重复缺陷信息分页数据")
	public RestfulResponse<Page<DefectC1Form>> repeatDefect(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			@ModelAttribute Page page) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();
		DataRightUtil.look(request, code, c1);
		Page<DefectC1Form> pg = c1Service.findDefectRepeatForm2(page, c1);
		return new RestfulResponse<Page<DefectC1Form>>(pg);
	}

	@RequestMapping(value = "defect/repeat/export", method = RequestMethod.GET)
	@ApiOperation(value = "导出重复缺陷信息")
	public ResponseEntity repeatDefectExport(HttpServletRequest request, @ModelAttribute CQueryFilter c1,
			String templetId) {
		c1.setSystemId(DefectSystem.defectC1.getStatus());
		String code = DataScope6CCode.DEFECT_INFORMATION_1C.getStatus();
		DataRightUtil.look(request, code, c1);

		List<DefectC1Response> list = c1Service.findDefectPackageRepeatResponseList(c1);
		ResponseEntity re = null;
		try {
			byte[] bt = c1Service.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "1C缺陷导出数据.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
}
