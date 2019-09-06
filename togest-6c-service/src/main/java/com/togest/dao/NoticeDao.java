package com.togest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.NoticeDTO;
import com.togest.request.NoticeQueryFilter;

public interface NoticeDao extends CrudDao<NoticeDTO> {
	
	public int defectCount(NoticeQueryFilter entity);
	
	public int deleteFalse(@Param("ids") List<String> ids, 
			@Param("deleteIp") String deleteIp,
			@Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);
	
	public String getNoticeNumberStr();
	
	public Integer getNoticeNumber(String noticeNumberYear);
	
	public String checkNumberRepeat(@Param("noticeNumberYear") String noticeNumberYear, 
			@Param("noticeNumber") Integer noticeNumber);
	
	public int updateNoticeStatus(@Param("ids") List<String> ids, @Param("noticeStatus") String noticeStatus);
	
	public NoticeDTO getNoticeDetails(String id);
	
	public List<NoticeDTO> findNotice(NoticeQueryFilter entity);
	
	public NoticeDTO getById(String id);
	
	public void deleteNoticeCheck(@Param("noticeId") String noticeId,@Param("checkId") String checkId);
	public void deleteNoticeChecks(@Param("noticeIds") List<String> noticeIds);
	public void insertNoticeCheck(@Param("noticeId") String noticeId,@Param("checkId") String checkId);
	public List<Map<String,String>> findNoticeCheck(@Param("noticeId") String noticeId, @Param("checkId") String checkId);
}
