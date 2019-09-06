package com.togest.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.util.string.StringUtil;
import com.togest.config.DataStatus;
import com.togest.dao.C4InfoDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.C4Info;
import com.togest.domain.C4InfoDTO;
import com.togest.domain.Page;
import com.togest.request.InfoQueryFilter;
import com.togest.service.C4InfoService;
import com.togest.utils.PageUtils;

@Service
public class C4InfoServiceImpl extends BaseInfoServiceImpl<C4InfoDao, C4InfoDTO> implements C4InfoService {
	
	@Autowired
	private C4InfoDao dao;
	

	@Transactional
	@Override
	public int update(C4Info entity) {
		Date date = new Date();
		entity.setUpdateDate(date);
		return dao.update(entity);
	}

	
	@Override
	@DictAggregation
	public C4InfoDTO get(String id) {
		return dao.getByKey(id);
	}
	
	@Override
	public C4InfoDTO findC4InfoDTO(Date checkDate, String lineId, String direction, String systemId, String sectionId) {
		return dao.findC4InfoDTO(checkDate, lineId, direction, systemId, sectionId);
	}

	@Override
	@DictAggregation
	public Page<C4InfoDTO> findC4InfoPages(Page page, InfoQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findC4InfoDTOList(entity));
	}
}
