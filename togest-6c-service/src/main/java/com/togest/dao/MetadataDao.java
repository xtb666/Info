package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Metadata;

public interface MetadataDao extends CrudDao<Metadata> {

	public int deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp);

}
