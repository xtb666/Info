package com.togest.service;

import com.togest.domain.FileBlobDTO;

public interface NoticeExportService {

	/**
	 * word下载
	 */
	public byte[] noticeWordExport(String id, String sysTemplateId, String noticeType);
	
	/**
	 * pdf下载
	 */
	public byte[] noticePDFExport(String id, String sysTemplateId, String noticeType);
	
	/**
	 * pdf在线预览
	 */
	public FileBlobDTO noticePDFPreview(String id, String sysTemplateId, String noticeType);
}
