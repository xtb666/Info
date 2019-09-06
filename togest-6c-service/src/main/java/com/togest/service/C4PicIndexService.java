package com.togest.service;

import java.util.List;

import com.togest.domain.C4PicIndexDTO;
import com.togest.domain.Naming;

public interface C4PicIndexService extends ICrudService<C4PicIndexDTO>{

	public void analyzeData(String xmlLocation, String mdbLocation,
			String packageName, String packagePath, String table,
			String sectionId,String source,String infoId);
	
	public List<Naming> getCanmerNoByInfoId(String infoId);
	
	public List<Naming> getStationByInfoId(String infoId);
	
	public void updateBatch(C4PicIndexDTO entity);
	
	public void deleteFalses(String ids, String deleteBy, String deleteIp);
}
