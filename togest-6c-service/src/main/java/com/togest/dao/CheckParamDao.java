package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.CheckParamDTO;

public interface CheckParamDao extends CrudDao<CheckParamDTO> {
	public int deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp);
}
