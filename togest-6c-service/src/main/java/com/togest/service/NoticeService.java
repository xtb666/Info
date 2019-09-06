package com.togest.service;

import java.util.List;
import java.util.Map;

import com.togest.domain.NoticeDTO;
import com.togest.domain.Page;
import com.togest.request.NoticeQueryFilter;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC1Response;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectC3Response;

public interface NoticeService extends ICrudService<NoticeDTO> {
	
	public Map<String, Object> getLatestNumber();
	
	public boolean isNumberRepeat(String year, Integer number,String id);

	public int save(NoticeDTO entity);
	
	public int updateNoticeStatus(String ids, String noticeStatus);
	
	public int generateSectionNotice(NoticeDTO entity, Boolean newFlag);

	public NoticeDTO getNoticeDetails(String id);
	
	public Page<NoticeDTO> findNotice(Page page, NoticeQueryFilter entity);
	
	public int delete(String ids, String deleteIp, String deleteBy);
	
	public int deleteFalse(String ids, String deleteIp, String deleteBy);
	
	public List<DefectC1Form> findDefectByNotice(String noticeId, String sectionId);
	
	public List<DefectC1Response> findDefectResponseByNotice(String noticeId, String sectionId);
	
	public List<DefectC3Form> findDefectByNoticeId(String noticeId, String sectionId);

	public List<DefectC3Response> find3CDefectResponseByNotice(String id, String sectionId);

}
