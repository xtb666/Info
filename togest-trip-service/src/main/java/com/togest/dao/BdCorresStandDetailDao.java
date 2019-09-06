package com.togest.dao;

import com.togest.domain.BdCorresStandDetail;

import feign.Param;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;


public interface BdCorresStandDetailDao extends CrudCommonDao<BdCorresStandDetail> {

	List<BdCorresStandDetail> findListByDistance(BdCorresStandDetail bdCorresStandDetail);
	
	List<BdCorresStandDetail> findListByDistanceAndGlb(BdCorresStandDetail detailParam);

	void insertBatch(List<BdCorresStandDetail> list);
}
