package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DataDrawingDTO;
import com.togest.request.DataDrawingRequest;

public interface DataDrawingDao extends CrudDao<DataDrawingDTO> {

	public List<DataDrawingDTO> getListByTypeOrDeptIdsOrAssortmentIdsOrName(
			@Param("type") String type, @Param("deptIds") String deptIds,
			@Param("assortmentIds") String assortmentIds,
			@Param("name") String name);

	public List<DataDrawingDTO> getListByDataDrawingRequest(
			DataDrawingRequest entity);

}
