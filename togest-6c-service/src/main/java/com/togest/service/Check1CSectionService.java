package com.togest.service;

import java.util.Date;
import java.util.List;

import com.togest.domain.Check1CSectionDTO;
import com.togest.domain.Page;
import com.togest.request.CheckQueryFilter;

public interface Check1CSectionService {
	
	public Check1CSectionDTO get(String id);
	
	public int update(Check1CSectionDTO entity);
	
	public Page<Check1CSectionDTO> findCheckSectionList(Page page, CheckQueryFilter entity);
	
	public int deleteFalses(String ids, String deleteBy, String deleteIp);
	
	public int deleteFalsesByCheckIds(String ids, String deleteBy, String deleteIp, Date deleteDate);
	
	public List<String> getByCheckIds(List<String> checkIds);
}
