package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdFastRepairedDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdFastRepairedService extends ICrudCommonService<BdFastRepairedDTO> {

	Page<BdFastRepairedDTO> findFastReairePage(Page<BdFastRepairedDTO> page, BdInfoQueryFilter entity);

	List<BdFastRepairedDTO> findFastReaireList(BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);

	BdFastRepairedDTO get(String id, int i);
}
