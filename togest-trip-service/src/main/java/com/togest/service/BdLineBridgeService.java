package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdLineBridgeDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdLineBridgeService extends ICrudCommonService<BdLineBridgeDTO> {

	BdLineBridgeDTO get(String id, int status);
	
	List<BdLineBridgeDTO> findLineBridgeList(BdInfoQueryFilter entity);

	Page<BdLineBridgeDTO> findLineBridgePage(Page<BdLineBridgeDTO> page, BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);
}
