package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdSubstationRangeDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdSubstationRangeDao extends CrudCommonDao<BdSubstationRangeDTO> {

	List<BdSubstationRangeDTO> findSubstationRangeList(BdInfoQueryFilter entity);


}
