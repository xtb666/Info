package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.TechnicalDataDTO;
import com.togest.request.TechnicalDataRequest;

public interface TechnicalDataDao extends CrudDao<TechnicalDataDTO> {
	public List<TechnicalDataDTO> getListByTypeOrAssortmentIdsOrName(
			@Param("type") String type,
			@Param("assortmentIds") String assortmentIds,
			@Param("name") String name);

	public List<TechnicalDataDTO> getListByTechnicalDataRequest(
			TechnicalDataRequest entity);
}
