package com.togest.service;

import com.togest.service.upgrade.ICrudCommonService;

public interface BaseInfoService<T> extends ICrudCommonService<T> {
	public int save(T entity);
	
	public int deleteFalses(String ids, String deleteBy, String deleteIp);
	
}
