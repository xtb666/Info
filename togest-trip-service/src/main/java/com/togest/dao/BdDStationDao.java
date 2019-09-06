package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdDStationDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdDStationDao extends CrudCommonDao<BdDStationDTO> {

	List<BdDStationDTO> findDStationList(BdInfoQueryFilter entity);


}
