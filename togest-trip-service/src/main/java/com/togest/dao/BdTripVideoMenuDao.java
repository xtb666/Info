package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdTripVideoMenuDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdTripVideoMenuDao extends CrudCommonDao<BdTripVideoMenuDTO> {

	List<BdTripVideoMenuDTO> findTripVideoMenuList(BdInfoQueryFilter entity);

	List<BdTripVideoMenuDTO> findListByGlb(BdTripVideoMenuDTO bdTripVideoMenuDTO);

	List<BdTripVideoMenuDTO> findListByDistanceAndGlb(BdTripVideoMenuDTO bdTripVideoMenuDTO);


}
