package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdConstructPositionDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdConstructPositionDao extends CrudCommonDao<BdConstructPositionDTO> {

	List<BdConstructPositionDTO> findConstructPositionList(BdInfoQueryFilter entity);


}
