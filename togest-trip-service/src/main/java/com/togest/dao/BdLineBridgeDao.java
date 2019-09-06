package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdFastRepairedDTO;
import com.togest.domain.dto.BdLineBridgeDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdLineBridgeDao extends CrudCommonDao<BdLineBridgeDTO> {

	List<BdLineBridgeDTO> findLineBridgeList(BdInfoQueryFilter entity);
}
