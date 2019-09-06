package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.dao.DefectDetailDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.DefectDetail;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;
import com.togest.service.DefectDetailService;
import com.togest.utils.PageUtils;

@Service
public class DefectDetailServiceImpl implements DefectDetailService{

	@Autowired
	private DefectDetailDao dao;
	
	
	
	@Override
	@DictAggregation
	public DefectDetail getByKey(String id) {
		return dao.getByKey(id);
	}

	@Override
	@DictAggregation
	public List<DefectDetail> findAllList() {
		return dao.findAllList();
	}

	@Override
	@DictAggregation
	public List<DefectDetail> getListRegisterDefect(CQueryFilter entity) {
		return dao.getListRegisterDefect(entity);
	}

	@Override
	@DictAggregation
	public Page<DefectDetail> getPageRegisterDefect(Page page,
			CQueryFilter entity) {
		PageUtils.setPage(page);
		Page<DefectDetail> pg = PageUtils.buildPage(dao
				.getListRegisterDefect(entity));
		return pg;
	}

	
}
