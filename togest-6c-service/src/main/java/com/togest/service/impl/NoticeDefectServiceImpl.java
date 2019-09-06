package com.togest.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.DefectDao;
import com.togest.dao.NoticeDefectDao;
import com.togest.domain.NoticeDefect;
import com.togest.service.CrudService;
import com.togest.service.NoticeDefectService;

@Service
public class NoticeDefectServiceImpl extends CrudService<NoticeDefectDao, NoticeDefect>
	implements NoticeDefectService {
	
	@Autowired
	private DefectDao defectDao;

	@Override
	public List<String> getDefectIds(String noticeSectionId) {
		return dao.getDefectIds(noticeSectionId);
	}

	@Override
	public int deleteByNoticeSectionId(List<String> ids) {
		if(StringUtil.isEmpty(ids)) {
			return 0;
		}
		return dao.deleteByNoticeSectionId(ids);
	}
	
	@Override
	public List<String> hasDefect(List<String> defectIds){
		return dao.getNoticeSectionIds(defectIds);
	}

	@Override
	public Map<String, Object> checkDefectStart(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", false);
		if(StringUtil.isNotBlank(ids)) {
			List<String> list = Arrays.asList(ids.split(","));
			List<String> defectIds = hasDefect(list);
			if(StringUtil.isNotEmpty(defectIds)){
				map.put("data", defectDao.getByKeys(defectIds));
			} else {
				map.put("flag", true);
			}
		}
		return map;
	}

	@Override
	public List<String> getDefectIdsByNoticeSectionIds(List<String> noticeSectionIds) {
		
		return dao.getDefectIdsByNoticeSectionIds(noticeSectionIds);
	}
	
	
}
