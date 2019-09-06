package com.togest.dao;

import java.util.List;

import com.togest.domain.dto.RulesRegulationDTO;
import com.togest.request.RulesRegulationRequest;

public interface RulesRegulationDao extends CrudDao<RulesRegulationDTO> {

	List<RulesRegulationDTO> getListByRulesRegulationRequest(RulesRegulationRequest entity);

}
