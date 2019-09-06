package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdCorresStandDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdCorresStandService extends ICrudCommonService<BdCorresStandDTO> {

	List<BdCorresStandDTO> findCorresStandList(BdInfoQueryFilter entity);

	Page<BdCorresStandDTO> findCorresStandPage(Page<BdCorresStandDTO> page, BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);
}
