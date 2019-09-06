package com.togest.service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import com.togest.domain.C3Info;
import com.togest.domain.C3InfoDTO;
import com.togest.domain.Page;
import com.togest.request.CheckQueryFilter;
import com.togest.request.FileForm;
import com.togest.request.InfoQueryFilter;

public interface C3InfoService extends BaseInfoService<C3InfoDTO> {
	
	public C3InfoDTO get(String id);
	
	public int update(C3Info entity);

	public Map<String, String> saveXML(File src, Map<String, FileForm> imgMap,
			String createBy);

	public Map<String, String> saveZip(InputStream input, String fileName,
			String createBy);
	
	public C3InfoDTO findC3InfoDTO(Date checkDate, String lineId, String direction, String systemId, String sectionId);
	
	public Page<C3InfoDTO> findC3InfoPages(Page page, InfoQueryFilter entity);
}
