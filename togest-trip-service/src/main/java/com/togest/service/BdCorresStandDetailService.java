package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.BdCorresStandDetail;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdCorresStandDetailService extends ICrudCommonService<BdCorresStandDetail> {
	void insertBatch(List<BdCorresStandDetail> list);

	List<BdCorresStandDetail> findListByDistance(String id, String distance, String glb);

	List<BdCorresStandDetail> findListByDistanceAndGlb(String id, String standId, String distance, String glb);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String standId,
			String sectionId, Map<String, Object> propMap);

}
