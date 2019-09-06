package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Page;
import com.togest.domain.dto.BdTripInformationDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdTripInformationService extends ICrudCommonService<BdTripInformationDTO> {

	public void checkGlb(BdInfoQueryFilter entity);
	
	BdTripInformationDTO get(String id);
	
	List<BdTripInformationDTO> findTripInformationList(BdInfoQueryFilter entity);

	Page<BdTripInformationDTO> findTripInformationPage(Page<BdTripInformationDTO> page, BdInfoQueryFilter entity);

	Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId, String sectionId, Map<String, Object> propMap);

	String svgImage(String id);
}
