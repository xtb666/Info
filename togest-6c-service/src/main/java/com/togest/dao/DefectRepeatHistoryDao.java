package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectRepeatHistory;

public interface DefectRepeatHistoryDao {

	public int insert(DefectRepeatHistory entity);
	public int insertBatch(@Param("list") List<DefectRepeatHistory> list);

	public int delete(@Param("ids") List<String> ids);
}
