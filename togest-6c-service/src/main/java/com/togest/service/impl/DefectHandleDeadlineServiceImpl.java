package com.togest.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.togest.common.annotation.Shift;
import com.togest.dao.DefectHandleDeadlineDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.DefectHandleDeadline;
import com.togest.domain.Page;
import com.togest.service.DefectHandleDeadlineService;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class DefectHandleDeadlineServiceImpl extends CrudCommonService<DefectHandleDeadlineDao, DefectHandleDeadline>
		implements DefectHandleDeadlineService {

	@Override
	@DictAggregation
	public DefectHandleDeadline get(String id) {
		return super.get(id);
	}
	public DefectHandleDeadline getByEntity(String defectDataLevel, String defectDataCategory) {
		DefectHandleDeadline dto = new DefectHandleDeadline();
		dto.setDefectDataLevel(defectDataLevel);
		dto.setDefectDataCategory(defectDataCategory);
		return dao.getByEntity(dto);
	}

	@Override
	public int save(DefectHandleDeadline entity) {
		if (!checkDataUniqe(entity.getDefectDataLevel(), entity.getDefectDataCategory(), entity.getId())) {
			Shift.fatal(StatusCode.DATA_REPEAT);
		}
		return super.save(entity);
	}

	public boolean checkDataUniqe(String defectDataLevel, String defectDataCategory, String id) {
		DefectHandleDeadline dto = new DefectHandleDeadline();
		dto.setDefectDataLevel(defectDataLevel);
		dto.setDefectDataCategory(defectDataCategory);
		DefectHandleDeadline temp = dao.getByEntity(dto);
		if (temp == null || temp.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@DictAggregation
	public List<DefectHandleDeadline> findList(DefectHandleDeadline entity) {
		return super.findList(entity);
	}

	@Override
	@DictAggregation
	public Page<DefectHandleDeadline> findPage(Page<DefectHandleDeadline> page, DefectHandleDeadline entity) {
		return super.findPage(page, entity);
	}

}
