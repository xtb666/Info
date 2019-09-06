package com.togest.service;

import java.util.Date;

import com.togest.domain.C2Info;
import com.togest.domain.C2InfoDTO;
import com.togest.domain.Page;
import com.togest.request.InfoQueryFilter;

public interface C2InfoService extends BaseInfoService<C2InfoDTO>{

	public C2InfoDTO get(String id);
	
	
	public int update(C2Info entity);
	
	public C2InfoDTO findC2InfoDTO(Date checkDate, String lineId, String direction, String systemId, String sectionId);
	
	public Page<C2InfoDTO> findC2InfoPages(Page page, InfoQueryFilter entity);
}
