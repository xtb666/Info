package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.PinyinUtil;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.DefectTypeDao;
import com.togest.domain.DefectTypeDTO;
import com.togest.service.DefectTypeService;
import com.togest.service.TreeService;

@Service
public class DefectTypeServiceImpl extends TreeService<DefectTypeDao, DefectTypeDTO> implements DefectTypeService {
	@Transactional
	public int save(DefectTypeDTO entity) {
		if (entity != null && entity.getIsNewRecord()) {
			if (StringUtil.isNotBlank(entity.getCode())) {
				entity.setId(entity.getCode());
				entity.setIsNewRecord(true);
			} else {
				if (StringUtil.isNotBlank(entity.getName())) {
					entity.setId(StringUtil.toUpperCase(PinyinUtil.getPingYin(entity.getName())));
					entity.setCode(StringUtil.toUpperCase(PinyinUtil.getPingYin(entity.getName())));
					entity.setIsNewRecord(true);
				}
			}
			if (checkCodeRepeat(entity.getCode(), null)) {
				Shift.fatal(StatusCode.CODE_REPEAT);
			} else if (checkNameRepeat(entity.getName(), null)) {
				Shift.fatal(StatusCode.NAME_REPEAT);
			}
			String id = dao.getFalseId(entity.getId());
			if (StringUtil.isNotBlank(id)) {
				dao.deleteByKey(id);
			}
			if (StringUtil.isBlank(entity.getParentId())) {
				entity.setParentId("0");
				entity.setParentIds("0,");
			}
			entity.setParentIds((super.get(entity.getParentId()) == null ? ""
					: super.get(entity.getParentId()).getParentIds() == null ? ""
							: super.get(entity.getParentId()).getParentIds())
					+ entity.getParentId() + ",");
			return dao.insert(entity);
		} else {
			if (entity != null && checkCodeRepeat(entity.getCode(), entity.getId())) {
				Shift.fatal(StatusCode.CODE_REPEAT);
			} else if (entity != null && checkNameRepeat(entity.getName(), entity.getId())) {
				Shift.fatal(StatusCode.NAME_REPEAT);
			}
			return super.save(entity);
		}

	}

	public boolean checkCodeRepeat(String code, String id) {
		DefectTypeDTO dto = new DefectTypeDTO();
		dto.setCode(code);
		DefectTypeDTO entity = dao.getByEntity(dto);
		if (entity == null || entity.getId().equals(id)) {
			return false;
		}
		return true;
	}

	public boolean checkNameRepeat(String name, String id) {
		DefectTypeDTO dto = new DefectTypeDTO();
		dto.setName(name);
		DefectTypeDTO entity = dao.getByEntity(dto);
		if (entity == null || entity.getId().equals(id)) {
			return false;
		}
		return true;
	}

	public List<DefectTypeDTO> getListBySystemCode(String systemCode) {
		return dao.getListBySystemCode(systemCode);
	}

	public DefectTypeDTO getTreeBySystemCode(String systemCode) {
		List<DefectTypeDTO> list = dao.getListBySystemCode(systemCode);
		DefectTypeDTO entity = dao.getByKey("0");
		if (StringUtil.isNotEmpty(list)) {
			Set<String> ids = new HashSet<String>();
			for (DefectTypeDTO defectTypeDTO : list) {
				String parentIds = defectTypeDTO.getParentIds();
				if (StringUtil.isNotBlank(parentIds)) {
					ids.addAll(Arrays.asList(parentIds.split(",")));
				}
			}
			ids.addAll(CollectionUtils.distinctExtractToList(list, "id", String.class));
			List<DefectTypeDTO> defectTypes = dao
					.getListByKeys(Arrays.asList(StringUtil.getStringByArray(ids).split(",")));
			setTree(entity, defectTypes);
		}
		return entity;
	}

	@Override
	public List<String> getDefectTypeByRanHu() {
		return dao.getDefectTypeByRanHu();
	}

	@Override
	public List<String> getDefectTypeByJiHe() {
		List<String> lists = new ArrayList<String>();
		List<String> list = dao.getDefectTypeByJiHe();
		if (StringUtil.isNotEmpty(list)) {
			for (String str : list) {
				List<String> listTemp = dao.getDefectTypeChildById(str);
				if (StringUtil.isNotEmpty(listTemp)) {
					lists.addAll(listTemp);
				}
			}
		}
		return lists;
	}

}
