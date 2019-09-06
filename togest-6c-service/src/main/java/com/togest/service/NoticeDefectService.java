package com.togest.service;

import java.util.List;
import java.util.Map;

import com.togest.domain.NoticeDefect;

public interface NoticeDefectService extends ICrudService<NoticeDefect> {

	public List<String> getDefectIds(String noticeSectionId);
	
	public List<String> getDefectIdsByNoticeSectionIds(List<String> noticeSectionIds);
	
	public int deleteByNoticeSectionId(List<String> ids);
	
	public List<String> hasDefect(List<String> defectIds);
	
	public Map<String, Object> checkDefectStart(String ids);
}
