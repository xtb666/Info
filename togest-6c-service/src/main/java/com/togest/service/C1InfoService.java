package com.togest.service;

import java.util.List;

import com.togest.domain.C1InfoDTO;
import com.togest.domain.Page;
import com.togest.request.InfoQueryFilter;
import com.togest.request.PlanQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface C1InfoService extends BaseInfoService<C1InfoDTO>{
	
	
	public List<C1InfoDTO> findLists(InfoQueryFilter entity);
	
	public Page<C1InfoDTO> findPages(Page page,InfoQueryFilter entity);


}
