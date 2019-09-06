package com.togest.service;

import java.util.Date;

import com.togest.domain.C4Info;
import com.togest.domain.C4InfoDTO;
import com.togest.domain.Page;
import com.togest.request.CheckQueryFilter;
import com.togest.request.InfoQueryFilter;

public interface C4InfoService extends BaseInfoService<C4InfoDTO> {

	public C4InfoDTO get(String id);
	
	public int update(C4Info entity);
	
	public C4InfoDTO findC4InfoDTO(Date checkDate, String lineId, String direction, String systemId, String sectionId);
	
	public Page<C4InfoDTO> findC4InfoPages(Page page, InfoQueryFilter entity);
}
