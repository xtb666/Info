package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdDStationDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdDStationService extends ICrudCommonService<BdDStationDTO> {

	BdDStationDTO get(String id, int status);
	
	List<BdDStationDTO> findDStationList(BdInfoQueryFilter entity);

	Page<BdDStationDTO> findDStationPage(Page<BdDStationDTO> page, BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);

}
