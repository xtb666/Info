package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectAssortmentDTO;

public interface DefectAssortmentDao extends CrudDao<DefectAssortmentDTO> {

	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);

}
