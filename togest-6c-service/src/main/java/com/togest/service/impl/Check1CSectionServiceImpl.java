package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.Check1CDao;
import com.togest.dao.Check1CSectionDao;
import com.togest.dao.DefectDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Check1CDTO;
import com.togest.domain.Check1CSectionDTO;
import com.togest.domain.Defect;
import com.togest.domain.Page;
import com.togest.request.CheckQueryFilter;
import com.togest.service.Check1CSectionService;
import com.togest.service.DefectService;
import com.togest.utils.PageUtils;

@Service
public class Check1CSectionServiceImpl implements Check1CSectionService  {

	@Autowired
	private Check1CSectionDao dao;
	@Autowired
	private Check1CDao checkDao;
	@Autowired
	private DefectDao defectDao;
	@Autowired
	private DefectService defectService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Check1CSectionServiceImpl.class);
	
	@Transactional
	@Override
	public int update(Check1CSectionDTO after) {

		if(StringUtil.isBlank(after.getCheckId())) {
			Shift.fatal(StatusCode.FAIL);
		}
		Check1CDTO check = checkDao.getByKey(after.getCheckId());
		if(StringUtil.isNotEmpty(check)) {
			Check1CSectionDTO before = dao.getByKey(after.getId());
			Double detectMileage = after.getDetectMileage();
			Double goodMileage = after.getGoodMileage();
			Double qualifiedMileage = after.getQualifiedMileage();
			Double unqualifiedMileage = after.getUnqualifiedMileage();
			if (detectMileage != qualifiedMileage + unqualifiedMileage) 
				Shift.fatal(StatusCode.MILEAGE_ERROR);
			if (goodMileage > qualifiedMileage)
				Shift.fatal(StatusCode.MILEAGE_ERROR2);
			check.change(before, after);
			after.setUpdateDate(new Date());
			checkDao.update(check);
			dao.update(after);
			return 1;
		} else {
			LOGGER.error("1C更新段检测作业时未找到其处级作业信息    段级检测作业id => "+after.getId());
			Shift.fatal(StatusCode.FAIL);
		}
		return 0;
	}

	@DictAggregation
	@Override
	public Page<Check1CSectionDTO> findCheckSectionList(Page page, CheckQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findCheckSectionList(entity));
	}

	@Transactional
	@Override
	public int deleteFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			Date date = new Date();
			StringBuilder deleteCheckIds = new StringBuilder();
			List<String> list = Arrays.asList(ids.split(","));
			List<Defect> defects = defectDao.getByInfoIds(list);
			if(StringUtil.isNotEmpty(defects)){
				defectService.deleteFalse(CollectionUtils.distinctExtractToList(defects, "id", String.class), deleteBy, deleteIp);
			}
			List<Check1CSectionDTO> csList = new ArrayList<>();
			for (String id : list) {
				Check1CSectionDTO entity = dao.getByKey(id);
				if(StringUtil.isEmpty(entity)) {
					Shift.fatal(StatusCode.FAIL);
				}
				csList.add(entity);
			}
			int i = dao.deleteFalses(list, deleteBy, deleteIp, date);
			for (Check1CSectionDTO entity : csList) {
				String checkId = entity.getCheckId();
				if(dao.unDeleteCounts(checkId) == 0) {
					deleteCheckIds.append(checkId+",");
				} else {
					Check1CDTO check = checkDao.getByKey(checkId);
					if(StringUtil.isEmpty(check)) {
						Shift.fatal(StatusCode.FAIL);
					}
					check.setPoints(check.getPoints()-entity.getPoints());
					check.setDetectMileage(check.getDetectMileage() - entity.getDetectMileage());
					check.setGoodMileage(check.getGoodMileage() - entity.getGoodMileage());
					check.setQualifiedMileage(check.getQualifiedMileage() - entity.getQualifiedMileage());
					check.setUnqualifiedMileage(check.getUnqualifiedMileage() - entity.getUnqualifiedMileage());
					checkDao.update(check);
				}
			}
			checkDao.deleteFalses(Arrays.asList(deleteCheckIds.toString().split(",")), "维护数据时自动删除", deleteIp, date);
			return i;
		}
		return 0;
	}

	@Transactional
	@Override
	public int deleteFalsesByCheckIds(String ids, String deleteBy, String deleteIp, Date deleteDate) {
		if (StringUtil.isNotBlank(ids)) {//主表删除时调用
			if (StringUtil.isNotBlank(ids)) {
				List<String> list = Arrays.asList(ids.split(","));
				int i = dao.deleteFalsesByCheckIds(list, "维护数据时自动删除", deleteIp, new Date());
				return i;
			}
		}
		return 0;
	}

	@DictAggregation
	@Override
	public Check1CSectionDTO get(String id) {
		return dao.getByKey(id);
	}

	@Override
	public List<String> getByCheckIds(List<String> checkIds) {
		return dao.getByCheckIds(checkIds);
	}

	
}
