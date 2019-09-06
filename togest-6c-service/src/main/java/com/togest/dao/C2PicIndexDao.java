package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.C2PicIndexDTO;

public interface C2PicIndexDao extends CrudDao<C2PicIndexDTO> {

	public void deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp);
	
	public void updateBatch(@Param("ids") List<String> ids,@Param("entity") C2PicIndexDTO entity);
}
