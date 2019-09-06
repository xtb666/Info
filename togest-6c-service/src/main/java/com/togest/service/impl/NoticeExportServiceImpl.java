package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.client.ExportClient;
import com.togest.common.annotation.Shift;
import com.togest.config.DefectSystem;
import com.togest.domain.FileBlobDTO;
import com.togest.domain.NoticeDTO;
import com.togest.domain.NoticeSectionDTO;
import com.togest.file.client.FileClient;
import com.togest.model.reponse.FileDataDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectResponse;
import com.togest.service.Defect1CService;
import com.togest.service.Defect3CService;
import com.togest.service.NoticeExportService;
import com.togest.service.NoticeSectionService;
import com.togest.service.NoticeService;


@Service
public class NoticeExportServiceImpl implements NoticeExportService {
	
	@Autowired
	private ExportClient wordExportService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeSectionService noticeSectionService;
	@Autowired
	private Defect1CService defect1CService;
	@Autowired
	private Defect3CService defect3CService;
	@Autowired
	private FileClient fileSystemFeignService;
	
	private static final String NOTICE = "notice";
	private static final String NOTICESECTION = "noticeSection";
	private static final String PDFNAME = "缺陷复核整改通知书.pdf";

	@Override
	public byte[] noticeWordExport(String id, String sysTemplateId, String noticeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysTemplateId", sysTemplateId);
		List<String> defectIds = new ArrayList<String>();
		
		Calendar calendar = Calendar.getInstance();
		NoticeDTO notice = null;
		String numberYear = null;
		String numberStr = null;
		Date issueDate = null;
		String issueDateStr = null;
		String issuePersonName = null;
		String responsiblePersonName = null;
		String rectifyContent = null;
		String systemId = null;
		if(NOTICE.equals(noticeType)) {
			notice = noticeService.getNoticeDetails(id);
			List<DefectResponse> defects = notice.getDefects();
			for (DefectResponse defectResponse : defects) {
				defectIds.add(defectResponse.getId());
			}
		} else if(NOTICESECTION.equals(noticeType)) {
			NoticeSectionDTO entity = noticeSectionService.getDetails(id);
			notice = entity.getNotice();
			List<DefectResponse> defects = entity.getDefects();
			for (DefectResponse defectResponse : defects) {
				defectIds.add(defectResponse.getId());
			}
		} else {
			Shift.fatal(StatusCode.NON_EXISTENT_TYPE);
		}
		
		numberYear = notice.getNoticeNumberYear();
		numberStr = notice.getNoticeNumberStr();
		issueDate = notice.getIssueDate();
		issuePersonName = notice.getIssuePerson();
		responsiblePersonName = notice.getResponsiblePerson();
		rectifyContent = notice.getRectifyContent();
		systemId = notice.getSystemId();
		calendar.setTime(issueDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		issueDateStr = year+"年"+month+"月"+day+"日";
		
		if(defectIds.size() == 0) {//通知书关联缺陷全部被删除
			map.put("defectList", null);
		} else {
			if(DefectSystem.defectC1.getStatus().equals(systemId)) {
				List<DefectC1Form> defectC1 = defect1CService.findC1DefectByIds(defectIds);
				map.put("defectList", defectC1);
			} else if(DefectSystem.defectC3.getStatus().equals(systemId)) {
				List<DefectC3Form> defectC3 = defect3CService.findC3DefectByIds(defectIds);
				map.put("defectList", defectC3);
			} else {
				Shift.fatal(StatusCode.NON_EXISTENT_TYPE);
			}
		}
		
		map.put("numberYear", numberYear);
		map.put("numberStr", numberStr);
		map.put("issueDateStr", issueDateStr);
		map.put("issuePersonName", issuePersonName);
		map.put("responsiblePersonName", responsiblePersonName);
		map.put("rectifyContent", rectifyContent);
		map.put("systemId", systemId);
		byte[] data = wordExportService.wordExport(map);

		return data;
	}

	@Override
	public byte[] noticePDFExport(String id, String sysTemplateId, String noticeType) {

		byte[] wordData = noticeWordExport(id, sysTemplateId, noticeType);
		FileDataDTO entity = new FileDataDTO();
		entity.setData(wordData);
		RestfulResponse<byte[]> pdf = fileSystemFeignService.conventToPdf(entity);
		/*File target = new File(PathUtil.getDirectoryPath() + File.separator + "temp");
		if (!target.exists()) {//临时文件目录
			target.mkdirs();
		}
		File file = new File(target, IdGen.uuid()+".doc");
		RestfulResponse<String> pdf = null;
		try {
			FileCopyUtils.copy(wordData, file);
			pdf = fileConvertClient.pdfConvert(file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}
		byte[] decodeBase64 = Encodes.decodeBase64(pdf.getData());*/
		if(pdf == null) {
			return null;
		}
		return pdf.getData();
	}

	@Override
	public FileBlobDTO noticePDFPreview(String id, String sysTemplateId, String noticeType) {
		
		FileBlobDTO blob = new FileBlobDTO();
		byte[] data = noticePDFExport(id, sysTemplateId, noticeType);
		if(data == null) {
			Shift.fatal(StatusCode.FAIL);
		}
		blob.setRealName(PDFNAME);
		blob.setData(data);
		return blob;
	}
	
	
}
