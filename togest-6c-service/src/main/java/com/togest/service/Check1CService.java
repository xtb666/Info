package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Check1C;
import com.togest.domain.Check1CDTO;
import com.togest.domain.Page;
import com.togest.request.CheckQueryFilter;

public interface Check1CService {

	public Check1CDTO get(String id);

	public int save(List<Check1CDTO> check1CList);

	public Map<String, Object> analyzeExcelData(String originalFilename, InputStream inputStream, String createBy,
			String createIp, String planId, String trainId, String infoId);

	public Map<String, Object> analyzeSectionData(String originalFilename, InputStream inputStream, String createBy,
			String createIp, String sectionId, String planId, String trainId, String infoId);

	public  Map<String, Object>  handleCheckSectionData(String originalFilename, InputStream inputStream, String templateId,
			String trainId, String sectionId, String createBy);

	public int update(Check1C entity);

	public int deleteFalses(String ids, String deleteBy, String deleteIp);

	public Page<Check1CDTO> findCheckListPages(Page page, CheckQueryFilter entity);

	public Page<Check1CDTO> check1CNoticePages(Page page, CheckQueryFilter entity);

	public List<Check1CDTO> getByKeys(List<String> ids);
}
