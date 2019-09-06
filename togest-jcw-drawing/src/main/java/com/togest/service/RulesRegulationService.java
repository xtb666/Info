package com.togest.service;

import java.util.List;
import java.util.Map;

import com.togest.domain.dto.RulesRegulationDTO;
import com.togest.request.RulesRegulationRequest;

public interface RulesRegulationService extends ICrudService<RulesRegulationDTO> {

	List<RulesRegulationDTO> getListByRulesRegulationRequest(RulesRegulationRequest entity);

	Map<String, List<Map<String, Object>>> editRulesRegulationDataConfig(RulesRegulationDTO basicData,
			String resourcesCode, String dictName, int i);

}
