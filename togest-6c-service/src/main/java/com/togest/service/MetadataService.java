package com.togest.service;

import com.togest.domain.Metadata;
import com.togest.domain.Page;

public interface MetadataService extends ICrudService<Metadata>{


	public Page<Metadata> findPage(Page<Metadata> page, Metadata entity);
	
	public int deleteFalses(String id, String deleteBy, String deleteIp);
	
	public int save(Metadata entity);

}
