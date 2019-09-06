package com.togest.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.CheckSystemDao;
import com.togest.domain.CheckSystemDTO;
import com.togest.service.CheckSystemService;
import com.togest.service.CrudService;

@Service
public class CheckSystemServiceImpl extends
		CrudService<CheckSystemDao, CheckSystemDTO> implements
		CheckSystemService {

	@Override
	@Transactional
	public int deleteFalses(String id, String deleteBy, String deleteIp) {
		if (StringUtil.isBlank(id)) {
			return 0;
		}
		List<String> list = Arrays.asList(id.split(","));
		for (String str : list) {
			dao.deleteSystemDefectType(str);
			dao.deleteSystemParam(str);
		}
		return dao.deleteFalses(list, deleteBy, deleteIp);
	}

	@Override
	@Transactional
	public int deleteFalse(CheckSystemDTO entity) {
		if (entity != null && StringUtil.isNotBlank(entity.getId())) {
			dao.deleteSystemDefectType(entity.getId());
			dao.deleteSystemParam(entity.getId());
		}
		return super.deleteFalse(entity);
	}

	public boolean checkCodeUniqe(String code, String id) {
		CheckSystemDTO entity = new CheckSystemDTO();
		entity.setCode(code);
		CheckSystemDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkNameUniqe(String name, String id) {
		CheckSystemDTO entity = new CheckSystemDTO();
		entity.setName(name);
		CheckSystemDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public int save(CheckSystemDTO entity) {
		if (entity != null) {
			if (!checkCodeUniqe(entity.getCode(), entity.getId())) {
				Shift.fatal(StatusCode.CODE_REPEAT);
			} else if (!checkNameUniqe(entity.getName(), entity.getId())) {
				Shift.fatal(StatusCode.NAME_REPEAT);
			}
		}

		int i = super.save(entity);
		dao.deleteSystemDefectType(entity.getId());
		dao.deleteSystemParam(entity.getId());
		if (StringUtil.isNotBlank(entity.getParamIds())) {
			dao.insertSystemParam(entity.getId(),
					Arrays.asList(entity.getParamIds().split(",")));
		}
		if (StringUtil.isNotBlank(entity.getDefectTypeIds())) {
			dao.insertSystemDefectType(entity.getId(),
					Arrays.asList(entity.getDefectTypeIds().split(",")));
		}

		return i;
	}
}
