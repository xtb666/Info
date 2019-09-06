package com.togest.service;

import java.util.List;

import com.togest.domain.WireHeightControlHistoryDTO;
import com.togest.service.upgrade.ICrudCommonService;

public interface WireHeightControlHistoryService extends ICrudCommonService<WireHeightControlHistoryDTO> {

	int save(WireHeightControlHistoryDTO WireHeightControlHistoryDTO, int status);
	
	List<WireHeightControlHistoryDTO> findLastNewWireHeightByPillars(List<String> pillarIdList);

	WireHeightControlHistoryDTO findHistoryByMaxVersion(String controlId);
}
