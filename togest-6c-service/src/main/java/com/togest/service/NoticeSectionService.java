package com.togest.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.togest.domain.NoticeSectionDTO;
import com.togest.domain.Page;
import com.togest.request.NoticeQueryFilter;
import com.togest.response.statistics.NoticeFlowCountData;

public interface NoticeSectionService extends ICrudService<NoticeSectionDTO> {

	public NoticeSectionDTO getDetails(String id);
	
	public List<NoticeSectionDTO> getByNoticeId(String noticeId);
	
	public int updateStatus(String ids, String status);
	
	public int updateFeedbackInfo(String ids, String feedbackPerson, String feedbackContent, Date feedbackDate);
	
	public Page<NoticeSectionDTO> findNoticeSection(Page page, NoticeQueryFilter entity);
	
	public boolean needUpdateStatus(String id, boolean flag);
	
	public Map<String, String> getName(String ids, String flag);
	
	public int deleteByNoticeId(String ids);
	
	public int deleteFalse(String ids, String deleteIp, String deleteBy);
	
	public String getNoticeIdByNoticeSectionId(String id);
	
	public int deleteFalseByNoticeId(List<String> noticeIds, String deleteIp, String deleteBy, Date deleteDate);
	
	public NoticeFlowCountData findNoticeFlowCount(NoticeQueryFilter n1);
}
