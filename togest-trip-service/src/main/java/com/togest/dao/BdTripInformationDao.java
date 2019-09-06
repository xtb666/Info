package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdTripInformationDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdTripInformationDao extends CrudCommonDao<BdTripInformationDTO> {

	List<BdTripInformationDTO> findTripInformationList(BdInfoQueryFilter entity);


}
