package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdTLineDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdTLineDao extends CrudCommonDao<BdTLineDTO> {

	List<BdTLineDTO> findTLineList(BdInfoQueryFilter entity);


}
