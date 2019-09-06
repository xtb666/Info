package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdSubstationRangeDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdSubstationRangeService extends ICrudCommonService<BdSubstationRangeDTO> {

	BdSubstationRangeDTO get(String id, int status);
	
	List<BdSubstationRangeDTO> findSubstationRangeList(BdInfoQueryFilter entity);

	Page<BdSubstationRangeDTO> findSubstationRangePage(Page<BdSubstationRangeDTO> page, BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);
}
