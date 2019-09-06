package com.togest.service;

import java.util.List;

import com.togest.domain.Page;
import com.togest.domain.dto.BdSpeedLimitDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdSpeedLimitService extends ICrudCommonService<BdSpeedLimitDTO> {

	BdSpeedLimitDTO get(String id, int status);
	
	List<BdSpeedLimitDTO> findSpeedLimitList(BdInfoQueryFilter entity);

	Page<BdSpeedLimitDTO> findSpeedLimitPage(Page<BdSpeedLimitDTO> page, BdInfoQueryFilter entity);
}
