package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Naming;
import com.togest.domain.NoticeSectionDTO;
import com.togest.request.NoticeQueryFilter;
import com.togest.response.statistics.DefectFromStatistics;
import com.togest.response.statistics.NoticeFlowCountData;

public interface NoticeSectionDao extends CrudDao<NoticeSectionDTO> {

	public List<Naming> getNoticeSectionDefect(String id);
	
	public NoticeSectionDTO getNoticeSectionDetail(String id);
	
	public List<NoticeSectionDTO> getByNoticeId(String noticeId);
	
	public int updateStatus(@Param("ids") List<String> ids, @Param("status") String status);
	
	public int updateFeedbackInfo(@Param("ids") List<String> ids, 
			@Param("feedbackPerson") String feedbackPerson,
			@Param("feedbackContent") String feedbackContent, 
			@Param("feedbackDate") Date feedbackDate);
	
	public List<NoticeSectionDTO> findNoticeSection(NoticeQueryFilter entity);
	
	public int deleteByNoticeId(@Param("ids") List<String> ids);
	
	public List<String> getIdByNoticeId(@Param("ids") List<String> ids);
	
	public List<String> findStatusList(String id);
	
	public int deleteFalse(@Param("ids") List<String> ids, 
			@Param("deleteIp") String deleteIp,
			@Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);
	
	public List<String> getProcessInstanceIds(@Param("ids") List<String> ids);
	
	public String getNoticeIdByNoticeSectionId(String id);
	
	public int deleteFalseByNoticeId(@Param("ids") List<String> ids, @Param("deleteIp") String deleteIp, 
			@Param("deleteBy") String deleteBy, @Param("deleteDate") Date deleteDate);
	
	public int updateOvertime(@Param("ids") List<String> ids);
	
	public NoticeFlowCountData findNoticeFlowCount(NoticeQueryFilter n1);
}
