package com.togest.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.togest.dao.WireHeightControlHistoryDao;
import com.togest.domain.WireHeightControlHistoryDTO;
import com.togest.service.WireHeightControlHistoryService;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class WireHeightControlHistoryServiceImpl extends CrudCommonService<WireHeightControlHistoryDao, WireHeightControlHistoryDTO>
		implements WireHeightControlHistoryService {
	@Override
	public int save(WireHeightControlHistoryDTO entity, int status) {
		return super.save(entity);
	}

	@Override
	public List<WireHeightControlHistoryDTO> findLastNewWireHeightByPillars(List<String> pillarIdList) {
		return dao.findLastNewWireHeightByPillars(pillarIdList);
	}

	@Override
	public WireHeightControlHistoryDTO findHistoryByMaxVersion(String controlId) {
		return dao.findHistoryByMaxVersion(controlId);
	}
}
