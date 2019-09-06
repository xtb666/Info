package com.togest.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.C1InfoDao;
import com.togest.dao.CInfoDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.Page;
import com.togest.request.InfoQueryFilter;
import com.togest.service.C1InfoService;
import com.togest.utils.PageUtils;

@Service
public class C1InfoServiceImpl extends BaseInfoServiceImpl<C1InfoDao, C1InfoDTO> implements C1InfoService{

	
	@Override
	@DictAggregation
	public List<C1InfoDTO> findLists(InfoQueryFilter entity) {
		
		return dao.findLists(entity);
	}

	@Override
	@DictAggregation
	public Page<C1InfoDTO> findPages(Page page, InfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<C1InfoDTO> pg = PageUtils.buildPage(dao.findLists(entity));
		return pg;
	}

	
}
