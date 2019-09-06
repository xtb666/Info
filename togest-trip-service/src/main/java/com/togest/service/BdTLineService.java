package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdTLineDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdTLineService extends ICrudCommonService<BdTLineDTO> {

	Page<BdTLineDTO> findTLinePage(Page<BdTLineDTO> page, BdInfoQueryFilter entity);

	List<BdTLineDTO> findTLineList(BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);
}
