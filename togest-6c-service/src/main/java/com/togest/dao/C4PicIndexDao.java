package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.C4PicIndexDTO;
import com.togest.domain.Naming;

public interface C4PicIndexDao extends CrudDao<C4PicIndexDTO> {
	
	public void deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp);
	
	public List<Naming> getCanmerNoByInfoId(@Param("infoId") String infoId);
	
	public List<Naming> getStationByInfoId(@Param("infoId") String infoId);
	
	public void updateBatch(@Param("ids") List<String> ids,@Param("entity") C4PicIndexDTO entity);
}
