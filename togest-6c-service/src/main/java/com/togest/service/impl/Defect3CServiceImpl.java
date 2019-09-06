package com.togest.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectSystem;
import com.togest.dao.Defect3CDao;
import com.togest.dao.DefectCheckHandleDao;
import com.togest.dao.DefectReformHandleDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Defect3CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC3Request;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectC3Response;
import com.togest.service.Defect3CService;
import com.togest.service.DefectTypeService;
import com.togest.utils.PageUtils;

@Service
public class Defect3CServiceImpl extends SixCServiceImpl<Defect3CDao, Defect3CDTO, DefectC3Response, DefectC3Form>
		implements Defect3CService {

	@Autowired
	private DefectCheckHandleDao checkHandleDao;
	@Autowired
	private DefectReformHandleDao reformHandleDao;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(Defect3CServiceImpl.class);

	@Override
	@DictAggregation
	public Page<DefectC3Form> findC3FormForNotice(Page page, CQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findC3FormForNotice(entity));
	}

	@Override
	@DictAggregation
	public List<DefectC3Form> findC3DefectByIds(List<String> ids) {
		return dao.findC3DefectByIds(ids);
	}

	@Override
	public int updateDefectC3Request(DefectC3Request entity) {
		DefectCheckHandle checkHandle = entity.getDefectCheckHandle();
		if (StringUtil.isNotEmpty(checkHandle)) {
			checkHandle.setId(entity.getId());
			checkHandleDao.update(checkHandle);
		}
		DefectReformHandle reformHandle = entity.getDefectReformHandle();
		if (StringUtil.isNotEmpty(reformHandle)) {
			reformHandle.setId(entity.getId());
			reformHandleDao.update(reformHandle);
		}
		return save(entity);
	}

	//@PostConstruct
	public void updateDefectValue() {
		List<String> ranHu = defectTypeService.getDefectTypeByRanHu();
		List<String> jiHe = defectTypeService.getDefectTypeByJiHe();

		if (StringUtil.isNotEmpty(ranHu)) {
			List<DefectC3Form> list = dao.findC3DefectByDefectTypeIds(ranHu);
			if (StringUtil.isNotEmpty(list)) {
				for (DefectC3Form defectC3Form : list) {
					dao.updateDefectValue(defectC3Form.getId(), defectC3Form.getNetT());
				}
			}
		}
		if (StringUtil.isNotEmpty(jiHe)) {
			List<DefectC3Form> list = dao.findC3DefectByDefectTypeIds(jiHe);
			if (StringUtil.isNotEmpty(list)) {
				for (DefectC3Form defectC3Form : list) {
					dao.updateDefectValue(defectC3Form.getId(), defectC3Form.getStagger());
				}
			}
		}
	}
	
	@Override
	public int save(Defect3CDTO entity) {
		setDefectValue(entity);
		int i = super.save(entity);
		return i;
	}

	private void setDefectValue(Defect3CDTO entity){
		if(DefectSystem.defectC3.getStatus().equals(entity.getSystemId())){
			if(isJiHe(entity.getDefectType())){
				entity.setDefectValue(entity.getStagger());
			}else if(isRanHu(entity.getDefectType())){
				entity.setDefectValue(entity.getNetT());
			}
		}
	}
	
	public boolean isRanHu(String defectTypeId){
		List<String> ranHu = defectTypeService.getDefectTypeByRanHu();
		if(StringUtil.isNotEmpty(ranHu)){
			for (String str : ranHu) {
				if(str.equals(defectTypeId)){
					return true;
				}
			}
		}
		return false;
	}
	public boolean isJiHe(String defectTypeId){
		List<String> jiHe = defectTypeService.getDefectTypeByJiHe();
		if(StringUtil.isNotEmpty(jiHe)){
			for (String str : jiHe) {
				if(str.equals(defectTypeId)){
					return true;
				}
			}
		}
		return false;
	}
	public List<DefectC3Form> findC3FormBySectionNotice(List<String> noticeSectionIds, String sectionId) {

		return dao.findC3FormBySectionNotice(noticeSectionIds, sectionId);
	}
}
