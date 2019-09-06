package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdConstructPositionDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdConstructPositionService extends ICrudCommonService<BdConstructPositionDTO> {

	public BdConstructPositionDTO get(String id, int status);
	
	Page<BdConstructPositionDTO> findConstructPositionPage(Page<BdConstructPositionDTO> page, BdInfoQueryFilter entity);

	List<BdConstructPositionDTO> findConstructPositionList(BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);

}
