package com.togest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Check1CSection;
import com.togest.domain.Check1CSectionDTO;
import com.togest.request.CheckQueryFilter;

public interface Check1CSectionDao {

	public int insert(Check1CSection entity);
	
	public int update(Check1CSection entity);
	
	public Check1CSectionDTO getByKey(String id);
	
	public Integer unDeleteCounts(String checkId);
	
	public List<Check1CSectionDTO> findCheckSectionList(CheckQueryFilter entity);
	
	public List<Map<String, String>> findCheckSectionIds(@Param("entityList") List<Check1CSectionDTO> entityList);
	
	public String findCheckSectionId(@Param("checkDate") Date checkDate, @Param("lineId") String lineId,
			@Param("direction") String direction, @Param("sectionId") String sectionId,@Param("deptId") String deptId);
	
	public int deleteFalsesByCheckIds(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);
	
	public int deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);
	
	public List<String> getByCheckIds(@Param("checkIds") List<String> checkIds);
} 
