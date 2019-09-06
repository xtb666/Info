package com.togest.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.DataControlPut;
import com.togest.authority.annotation.DataControlSectionPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectSystem;
import com.togest.domain.NoticeDTO;
import com.togest.domain.NoticeSectionDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.NoticeQueryFilter;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC1Response;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectC3Response;
import com.togest.response.statistics.NoticeFlowCountData;
import com.togest.service.Defect1CService;
import com.togest.service.Defect3CService;
import com.togest.service.NoticeExportService;
import com.togest.service.NoticeSectionService;
import com.togest.service.NoticeService;
import com.togest.util.TokenUtil;
import com.togest.util.UserInfo;
import com.togest.utils.PageUtils;

import io.swagger.annotations.ApiOperation;

@RestController
public class NoticeResource {

	@Autowired
	private NoticeService servcie;
	@Autowired
	private NoticeSectionService noticeSectionService;
	@Autowired
	private NoticeExportService noticeExportService;
	@Autowired
	private Defect1CService defect1CService;
	@Autowired
	private Defect3CService defect3CService;

	@RequestMapping(value = "notice/number", method = RequestMethod.GET)
	@ApiOperation(value = "获取自动生成的通知书编号")
	public RestfulResponse<Map<String, Object>> getLatestNumber() {
		Map<String, Object> map = servcie.getLatestNumber();
		return new RestfulResponse<Map<String, Object>>(map);
	}

	@RequestMapping(value = "notice", method = RequestMethod.POST)
	@ApiOperation(value = "新增通知书")
	public RestfulResponse<Boolean> save(HttpServletRequest request, @RequestBody NoticeDTO entity) {
		if (DefectSystem.defectC1.getStatus().equals(entity.getSystemId())
				&& StringUtil.isBlank(entity.getCheckIds())) {
			Shift.fatal(StatusCode.DEFECT_EMPTY);
		} else if (DefectSystem.defectC3.getStatus().equals(entity.getSystemId())
				&& StringUtil.isBlank(entity.getDefectIds())) {
			Shift.fatal(StatusCode.DEFECT_EMPTY);
		}
		entity.setRectifyDate(null);
		servcie.save(entity);
		return new RestfulResponse<Boolean>(true);
	}
	
	@RequestMapping(value = "notice", method = RequestMethod.GET)
	@ApiOperation(value = "处级通知书信息")
	public RestfulResponse<NoticeDTO> noticeInfo(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		NoticeDTO noticeDTO = servcie.get(id);
		return new RestfulResponse<NoticeDTO>(noticeDTO);
	}
	
	
	@RequestMapping(value = "noticeSection", method = RequestMethod.GET)
	@ApiOperation(value = "段级通知书信息")
	public RestfulResponse<NoticeSectionDTO> noticeSectionInfo(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		NoticeSectionDTO entity = noticeSectionService.get(id);
		return new RestfulResponse<NoticeSectionDTO>(entity);
	}

	@RequestMapping(value = "notice/delete", method = RequestMethod.POST)
	@ApiOperation(value = "处级通知书删除(新建下发页使用)")
	public RestfulResponse<Boolean> delete(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		servcie.delete(id, request.getRemoteHost(), TokenUtil.getUserTokenData(request, "id"));
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "notice/query/delete", method = RequestMethod.POST)
	@ApiOperation(value = "处级通知书删除(查询页使用)")
	public RestfulResponse<Boolean> deleteFalse(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		servcie.deleteFalse(id, request.getRemoteHost(), TokenUtil.getUserTokenData(request, "id"));
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "noticeSection/delete", method = RequestMethod.POST)
	@ApiOperation(value = "段级通知书删除")
	public RestfulResponse<Boolean> deleteNoticeSection(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		noticeSectionService.deleteFalse(id, request.getRemoteHost(), TokenUtil.getUserTokenData(request, "id"));
		return new RestfulResponse<Boolean>(true);
	}

	@RequestMapping(value = "notice/pages", method = RequestMethod.GET)
	@ApiOperation(value = "处级通知书的分页数据")
	public RestfulResponse<Page<NoticeDTO>> pages(HttpServletRequest request, @ModelAttribute NoticeQueryFilter n1,
			@ModelAttribute Page page) {
		Page<NoticeDTO> pg = servcie.findNotice(page, n1);
		return new RestfulResponse<Page<NoticeDTO>>(pg);

	}
	
	@DataControlSectionPut(authCode="LOOK_QUERY_3C")
	@RequestMapping(value = "noticeSection/pages", method = RequestMethod.GET)
	@ApiOperation(value = "段级通知书的分页数据")
	public RestfulResponse<Page<NoticeSectionDTO>> sectionPages(HttpServletRequest request,
			@ModelAttribute NoticeQueryFilter n1, @ModelAttribute Page page) {
	/*	String sectionId = TokenUtil.getUser(request).getSectionId();
		if (StringUtil.isNotBlank(sectionId)) {
			n1.setSectionId(sectionId); 
		}*/
		Page<NoticeSectionDTO> pg = noticeSectionService.findNoticeSection(page, n1);
		return new RestfulResponse<Page<NoticeSectionDTO>>(pg);
	}

	@RequestMapping(value = "notice/defect/pages", method = RequestMethod.GET)
	@ApiOperation(value = "段级通知书的分页数据")
	public RestfulResponse<Page<DefectC1Form>> findDefectByNotice(Page page, String noticeId, String sectionId) {
		PageUtils.setPage(page);
		Page<DefectC1Form> pg = PageUtils.buildPage(servcie.findDefectByNotice(noticeId, sectionId));

		return new RestfulResponse<Page<DefectC1Form>>(pg);
	}
	@RequestMapping(value = "notice/defect/lists", method = RequestMethod.GET)
	@ApiOperation(value = "段级通知书的数据")
	public RestfulResponse<List<DefectC1Form>> findDefectByNoticeList(String noticeId, String sectionId) {
		List<DefectC1Form> list = servcie.findDefectByNotice(noticeId, sectionId);
		return new RestfulResponse<List<DefectC1Form>>(list);
	}
	
	@RequestMapping(value = "notice/3C/defect/pages", method = RequestMethod.GET)
	@ApiOperation(value = "段级通知书的分页数据")
	public RestfulResponse<List<DefectC3Form>> findDefectByNotice(String noticeId, String sectionId) {
		List<DefectC3Form> pg = servcie.findDefectByNoticeId(noticeId, sectionId);
		return new RestfulResponse<List<DefectC3Form>>(pg);
	}

	@RequestMapping(value = "noticeSection/noticeId", method = RequestMethod.GET)
	@ApiOperation(value = "通过处级id获取段级通知书集合")
	public RestfulResponse<List<NoticeSectionDTO>> sectionPagesByNoticeId(HttpServletRequest request, String noticeId) {
		List<NoticeSectionDTO> list = noticeSectionService.getByNoticeId(noticeId);
		return new RestfulResponse<List<NoticeSectionDTO>>(list);
	}

	@RequestMapping(value = "notice/details", method = RequestMethod.GET)
	@ApiOperation(value = "处级通知书详情")
	public RestfulResponse<NoticeDTO> detail(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		NoticeDTO noticeDTO = servcie.getNoticeDetails(id);
		return new RestfulResponse<NoticeDTO>(noticeDTO);
	}

	@RequestMapping(value = "noticeSection/details", method = RequestMethod.GET)
	@ApiOperation(value = "段级通知书详情")
	public RestfulResponse<NoticeSectionDTO> details(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		NoticeSectionDTO entity = noticeSectionService.getDetails(id);
		return new RestfulResponse<NoticeSectionDTO>(entity);
	}

	@RequestMapping(value = "notice/word", method = RequestMethod.GET)
	@ApiOperation(value = "通知书导出(word下载)")
	public ResponseEntity getAnalysisReport(HttpServletRequest request, String id, String sysTemplateId,
			String noticeType) {
		byte[] data = noticeExportService.noticeWordExport(id, sysTemplateId, noticeType);
		if (data == null) {
			Shift.fatal(StatusCode.FAIL);
		}
		ResponseEntity re = null;
		try {
			re = FileDownload.fileDownload(data, "缺陷通知书.doc");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}

	@RequestMapping(value = "notice/pdf", method = RequestMethod.GET)
	@ApiOperation(value = "通知书导出(pdf下载)")
	public ResponseEntity noticePDF(HttpServletRequest request, String id, String sysTemplateId, String noticeType) {
		byte[] data = noticeExportService.noticePDFExport(id, sysTemplateId, noticeType);
		if (data == null) {
			Shift.fatal(StatusCode.FAIL);
		}
		ResponseEntity re = null;
		try {
			re = FileDownload.fileDownload(data, "缺陷通知书.pdf");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}

	// @RequestMapping(value = "notice/preview", method = RequestMethod.GET)
	// @ApiOperation(value = "通知书在线预览(转换之后的pdf)")
	// public void noticePreview(HttpServletRequest request, HttpServletResponse
	// response, String id, String sysTemplateId,
	// String noticeType) {
	//
	// FileBlobDTO data = noticeExportService.noticePDFPreview(id,
	// sysTemplateId, noticeType);
	// try {
	// FileDownload.showFile(request, response, data.getRealName(), new
	// ByteArrayInputStream(data.getData()));
	// } catch (Exception e) {
	// Shift.fatal(StatusCode.FAIL);
	// }
	// }
	@RequestMapping(value = "notice/preview", method = RequestMethod.GET)
	@ApiOperation(value = "通知书在线预览(转换之后的pdf)")
	public ResponseEntity noticePreview(HttpServletRequest request, HttpServletResponse response, String id,
			String sysTemplateId, String noticeType) {

		byte[] data = noticeExportService.noticeWordExport(id, sysTemplateId, noticeType);
		if (data == null) {
			Shift.fatal(StatusCode.FAIL);
		}
		ResponseEntity re = null;
		try {
			re = FileDownload.fileDownload(data, "缺陷通知书.doc");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}

	@RequestMapping(value = "notice/defect/preview", method = RequestMethod.GET)
	@ApiOperation(value = "通知书缺陷导出")
	public ResponseEntity noticeDefectPreview(HttpServletRequest request, HttpServletResponse response, String id,
			String templetId, String sectionId) {
		List<DefectC1Response> list = servcie.findDefectResponseByNotice(id, sectionId);
		if (StringUtil.isEmpty(list)) {
			list = new ArrayList<>();
			DefectC1Response entity = new DefectC1Response();
			list.add(entity);
		}
		ResponseEntity re = null;
		try {
			byte[] bt = defect1CService.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "1C缺陷整改通知书.xls");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	@RequestMapping(value = "notice/3C/defect/preview", method = RequestMethod.GET)
	@ApiOperation(value = "通知书缺陷导出")
	public ResponseEntity notice3CDefectPreview(HttpServletRequest request, HttpServletResponse response, String id,
			String templetId, String sectionId) {
		List<DefectC3Response> list = servcie.find3CDefectResponseByNotice(id, sectionId);
		if (StringUtil.isEmpty(list)) {
			list = new ArrayList<>();
			DefectC3Response entity = new DefectC3Response();
			list.add(entity);
		}
		ResponseEntity re = null;
		try {
			byte[] bt = defect3CService.exportData(list, templetId);
			re = FileDownload.fileDownload(bt, "3C缺陷整改通知书.xls");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	@DataControlSectionPut(authCode="LOOK_REPORT_DEFECT_MANAGE_6C_HOME")
	@RequestMapping(value = "notice/findNoticeFlowCount", method = RequestMethod.GET)
	@ApiOperation(value = "通知书统计按状态")
	public RestfulResponse<NoticeFlowCountData> findNoticeFlowCount(HttpServletRequest request,@ModelAttribute NoticeQueryFilter n1) {
	/*	if(StringUtil.isNotBlank(n1.getAuthCode()))
			n1.setAuthCode("LOOK_REPORT_DEFECT_MANAGE_6C_HOME");
		String token = TokenUtil.getDataScopeTokenData(request,n1.getAuthCode());
		UserInfo info = TokenUtil.getUser(request);
		if(!"0".equals(token)){
			n1.setSectionId(info.getSectionId());
		}*/
		NoticeFlowCountData nfc = noticeSectionService.findNoticeFlowCount(n1);
		return new RestfulResponse<NoticeFlowCountData>(nfc);
	}
}
