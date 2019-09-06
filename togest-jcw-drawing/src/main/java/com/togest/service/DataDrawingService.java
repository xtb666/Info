package com.togest.service;

import java.util.List;
import java.util.Map;

import com.togest.domain.BatchDrawingData;
import com.togest.domain.DataDrawingDTO;
import com.togest.request.DataDrawingRequest;

public interface DataDrawingService extends ICrudService<DataDrawingDTO> {
	public List<DataDrawingDTO> getDataDrawingList(String type);

	public List<DataDrawingDTO> getDataDrawingList(String type, String deptIds);

	public List<DataDrawingDTO> getDataDrawingList(String type, String deptIds,
			String assortmentIds);

	public List<DataDrawingDTO> getDataDrawingList(String type, String deptIds,
			String assortmentIds, String name);
	
	public Map<String, List<Map<String, Object>>> editDataDrawingDataConfig(
			DataDrawingDTO entity, String systemResourcesCode, String dictName, int editTag);
	
	public void batchSave(BatchDrawingData data);
	
	public List<DataDrawingDTO> getListByDataDrawingRequest(
			DataDrawingRequest entity);

}
