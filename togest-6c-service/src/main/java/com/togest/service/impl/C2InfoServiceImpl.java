package com.togest.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.util.string.StringUtil;
import com.togest.config.DataStatus;
import com.togest.dao.C2InfoDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.C2Info;
import com.togest.domain.C2InfoDTO;
import com.togest.domain.Page;
import com.togest.request.InfoQueryFilter;
import com.togest.service.C2InfoService;
import com.togest.utils.PageUtils;

@Service
public class C2InfoServiceImpl extends BaseInfoServiceImpl<C2InfoDao, C2InfoDTO> implements C2InfoService {
	
	@Autowired
	private C2InfoDao dao;
	

	@Transactional
	@Override
	public int update(C2Info entity) {
		Date date = new Date();
		entity.setUpdateDate(date);
		return dao.update(entity);
	}

	
	@Override
	@DictAggregation
	public C2InfoDTO get(String id) {
		return dao.getByKey(id);
	}
	
	
	
	@Override
	@DictAggregation
	public C2InfoDTO findC2InfoDTO(Date checkDate, String lineId, String direction, String systemId, String sectionId) {
		return dao.findC2InfoDTO(checkDate, lineId, direction, systemId, sectionId);
	}

	@Override
	@DictAggregation
	public Page<C2InfoDTO> findC2InfoPages(Page page, InfoQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findC2InfoDTOList(entity));
	}

}
