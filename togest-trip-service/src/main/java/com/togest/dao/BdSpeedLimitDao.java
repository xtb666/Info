package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdSpeedLimitDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdSpeedLimitDao extends CrudCommonDao<BdSpeedLimitDTO> {

	List<BdSpeedLimitDTO> findSpeedLimitList(BdInfoQueryFilter entity);


}
