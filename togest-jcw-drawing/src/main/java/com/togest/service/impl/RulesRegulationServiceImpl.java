package com.togest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.RulesRegulationDao;
import com.togest.domain.dto.RulesRegulationDTO;
import com.togest.request.RulesRegulationRequest;
import com.togest.service.CrudService;
import com.togest.service.RulesRegulationService;
import com.togest.service.SystemPropertyResourcesService;
import com.togest.web.StatusCode;

@Service
public class RulesRegulationServiceImpl extends CrudService<RulesRegulationDao, RulesRegulationDTO> implements RulesRegulationService {

	@Autowired
	private SystemPropertyResourcesService systemPropertyResourcesService;
	
	//获取规章制度集合
	@Override
	public List<RulesRegulationDTO> getListByRulesRegulationRequest(RulesRegulationRequest entity) {
		return dao.getListByRulesRegulationRequest(entity);
	}

	@Override
	public Map<String, List<Map<String, Object>>> editRulesRegulationDataConfig(RulesRegulationDTO basicData,
			String resourcesCode, String dictName, int i) {
		return systemPropertyResourcesService.editDataConfig((Map<String, Object>) ObjectUtils.objectToMap(basicData),
				resourcesCode, dictName, i);
	}

	//保存规章制度信息
	@Override
	public int save(RulesRegulationDTO entity) {
		if (entity != null) {
			if (!checkCodeUniqe(entity.getCode(), entity.getId())) {
				Shift.fatal(StatusCode.CODE_REPEAT);
			} else if (StringUtil.isNotBlank(entity.getName()) && !checkNameUniqe(entity.getName(), entity.getId())) {
				Shift.fatal(StatusCode.NAME_REPEAT);
			}
		}
		return super.save(entity);
	}
	
	//校验名称是否重复
	public boolean checkCodeUniqe(String code, String id) {
		RulesRegulationDTO entity = new RulesRegulationDTO();
		entity.setCode(code);
		RulesRegulationDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	//校验code是否重复
	public boolean checkNameUniqe(String name, String id) {
		RulesRegulationDTO entity = new RulesRegulationDTO();
		entity.setName(name);
		RulesRegulationDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}
}
