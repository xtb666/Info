package com.togest.service;

import com.togest.domain.C2PicIndexDTO;

public interface C2PicIndexService extends ICrudService<C2PicIndexDTO> {

	public void deleteFalses(String ids, String deleteBy, String deleteIp);

	public void analyzeData(String xmlLocation, String mdbLocation,
			String packageName, String packagePath, String table,
			String sectionId,String source,String infoId);
	
	public void updateBatch(C2PicIndexDTO entity);
}
