package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.CheckSystemDTO;

public interface CheckSystemDao extends CrudDao<CheckSystemDTO> {
	public int deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp);

	public void insertSystemDefectType(@Param("systemId") String systemId,
			@Param("defectTypeIds") List<String> defectTypeIds);

	public void insertSystemParam(@Param("systemId") String systemId,
			@Param("paramIds") List<String> paramIds);

	public void deleteSystemParam(String systemId);

	public void deleteSystemDefectType(String systemId);
}
