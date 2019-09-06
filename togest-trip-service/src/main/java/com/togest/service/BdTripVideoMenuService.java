package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdTripVideoMenuDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdTripVideoMenuService extends ICrudCommonService<BdTripVideoMenuDTO> {
	int save(BdTripVideoMenuDTO entity, StringBuilder sb, int i);
	
	List<BdTripVideoMenuDTO> findTripVideoMenuList(BdInfoQueryFilter entity);

	Page<BdTripVideoMenuDTO> findTripVideoMenuPage(Page<BdTripVideoMenuDTO> page, BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);

	List<BdTripVideoMenuDTO> findListByGlb(String lineId, String glb, String distance);

	List<BdTripVideoMenuDTO> findListByDistanceAndGlb(String id, String lineId, String glb, String distance);
}
