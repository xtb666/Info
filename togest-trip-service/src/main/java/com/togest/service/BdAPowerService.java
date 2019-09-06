package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdAPowerDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdAPowerService extends ICrudCommonService<BdAPowerDTO> {

	BdAPowerDTO get(String id, int status);
	
	List<BdAPowerDTO> findAPowerList(BdInfoQueryFilter entity);

	Page<BdAPowerDTO> findAPowerPage(Page<BdAPowerDTO> page, BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);
}
