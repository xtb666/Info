package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.NoticeDefect;

public interface NoticeDefectDao extends CrudDao<NoticeDefect> {

	public List<String> getDefectIds(String noticeSectionId);
	
	public int deleteByNoticeSectionId(@Param("ids") List<String> ids);
	
	public List<String> getNoticeSectionIds(@Param("defectIds") List<String> defectIds);
	
	public List<String> getDefectIdsByNoticeSectionIds(@Param("noticeSectionIds") List<String> noticeSectionIds);
}
